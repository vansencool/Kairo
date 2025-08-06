package net.vansen.kairo.annotations.impl;

import net.vansen.kairo.Kairo;
import net.vansen.kairo.annotations.*;
import net.vansen.kairo.events.*;
import net.vansen.kairo.modules.Module;
import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class AnnotationsProcessor {
    private static final Unsafe unsafe;

    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            unsafe = (Unsafe) theUnsafeField.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to access Unsafe", e);
        }
    }

    /**
     * Processes the annotations in the mod's jar file.
     */
    public static void process() {
        try {
            File codeSourceFile = new File(Kairo.instance().getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            if (codeSourceFile.isDirectory()) {
                try (Stream<Path> paths = Files.walk(codeSourceFile.toPath())) {
                    paths.filter(path -> path.toString().endsWith(".class"))
                            .map(path -> codeSourceFile.toPath().relativize(path).toString()
                                    .replace(File.separator, ".").replace(".class", ""))
                            .map(AnnotationsProcessor::load)
                            .filter(Objects::nonNull)
                            .filter(clazz -> !clazz.isInterface() || !Modifier.isAbstract(clazz.getModifiers()))
                            .forEach(AnnotationsProcessor::process);
                }
            } else {
                try (JarFile jar = new JarFile(codeSourceFile)) {
                    jar.stream()
                            .filter(entry -> entry.getName().endsWith(".class"))
                            .map(entry -> entry.getName().replace('/', '.').replace(".class", ""))
                            .map(AnnotationsProcessor::load)
                            .filter(Objects::nonNull)
                            .filter(clazz -> !clazz.isInterface() || !Modifier.isAbstract(clazz.getModifiers()))
                            .forEach(AnnotationsProcessor::process);
                }
            }

            Kairo.logger().info("Processed all files.");
        } catch (Exception e) {
            Kairo.logger().error("[Kairo] Failed to process annotations", e);
        }
    }

    public static void process(@NotNull Class<?> clazz) {
        try {
            Optional.ofNullable(clazz.getAnnotation(Initialize.class))
                    .ifPresent(annotation -> constructor(clazz));

            Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> {
                        boolean init = method.isAnnotationPresent(Initialize.class);
                        if (init && method.getParameterCount() != 0) {
                            Kairo.logger().error("Method {} in class {} has parameters. It will not be invoked.", method.getName(), clazz.getName());
                            return false;
                        }
                        return init;
                    })
                    .forEach(method -> method(method, clazz));

            if (!Module.class.isAssignableFrom(clazz)) {
                return;
            }
            Module module = (Module) unsafe.allocateInstance(clazz);
            Optional.ofNullable(clazz.getAnnotation(Chat.class))
                    .filter(annotation -> ChatEvent.class.isAssignableFrom(clazz))
                    .ifPresent(annotation -> ChatEvent.register((ChatEvent) module));

            Optional.ofNullable(clazz.getAnnotation(CommandRegister.class))
                    .filter(annotation -> CommandRegisterEvent.class.isAssignableFrom(clazz))
                    .ifPresent(annotation -> CommandRegisterEvent.register((CommandRegisterEvent) module));

            Optional.ofNullable(clazz.getAnnotation(ClientChat.class))
                    .filter(annotation -> ClientChatEvent.class.isAssignableFrom(clazz))
                    .ifPresent(annotation -> ClientChatEvent.register((ClientChatEvent) module));

            Optional.ofNullable(clazz.getAnnotation(ClientCommand.class))
                    .filter(annotation -> ClientCommandEvent.class.isAssignableFrom(clazz))
                    .ifPresent(annotation -> ClientCommandEvent.register((ClientCommandEvent) module));

            Optional.ofNullable(clazz.getAnnotation(ClientBlockUse.class))
                    .filter(annotation -> ClientBlockUseEvent.class.isAssignableFrom(clazz))
                    .ifPresent(annotation -> ClientBlockUseEvent.register((ClientBlockUseEvent) module));
        } catch (Exception ignored) {
            Kairo.logger().error("Failed to process class: {}", clazz.getName());
        }
    }

    private static Class<?> load(@NotNull String className) {
        try {
            return Class.forName(className);
        } catch (@NotNull Exception e) {
            return null;
        }
    }

    public static void constructor(@NotNull Class<?> clazz) {
        try {
            clazz.getDeclaredConstructor().newInstance();
        } catch (@NotNull Exception ignored) {
        }
    }

    public static void method(@NotNull Method method, @NotNull Class<?> clazz) {
        try {
            method.setAccessible(true);
            method.invoke(Modifier.isStatic(method.getModifiers()) ? null : unsafe.allocateInstance(clazz));
        } catch (@NotNull Exception ignored) {
            Kairo.logger().error("Failed to invoke method: {} in class: {}", method.getName(), clazz.getName());
        }
    }
}
