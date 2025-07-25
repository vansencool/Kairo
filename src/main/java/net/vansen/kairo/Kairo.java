package net.vansen.kairo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.vansen.kairo.annotations.impl.AnnotationsProcessor;
import net.vansen.kairo.events.ClientChatEvent;
import net.vansen.kairo.events.ClientCommandEvent;
import net.vansen.kairo.events.CommandRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kairo implements ClientModInitializer {
    private static Kairo INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger("Kairo");

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        LOGGER.info("[Kairo] Registering modules...");

        long start = System.nanoTime();
        AnnotationsProcessor.process();
        long end = System.nanoTime();

        LOGGER.info("[Kairo] Modules registered in {}ms", (end - start) / 1_000_000);

        LOGGER.info("[Kairo] Initializing event listeners...");

        ClientCommandRegistrationCallback.EVENT.register(CommandRegisterEvent::dispatch);
        ClientSendMessageEvents.ALLOW_CHAT.register(ClientChatEvent::dispatch);
        ClientSendMessageEvents.ALLOW_COMMAND.register(ClientCommandEvent::dispatch);

        LOGGER.info("[Kairo] Event listeners initialized.");

        LOGGER.info("[Kairo] Initialization complete.");
    }

    public static Kairo instance() {
        return INSTANCE;
    }

    public static Logger logger() {
        return LOGGER;
    }
}