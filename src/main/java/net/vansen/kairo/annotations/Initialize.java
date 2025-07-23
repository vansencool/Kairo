package net.vansen.kairo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method or class for automatic initialization.
 *
 * <p>
 * When applied to a method:
 * <ul>
 * <li>If the method is static, it will be called directly.
 * <li>
 * If the method is not static, a new instance of the class containing
 * the method will be created and the method will be called on that instance. The constructor of the class will NOT be called.
 * </ul>
 *
 * <p>
 * When applied to a class, the class's constructor and static initialization block will be executed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Initialize {
}