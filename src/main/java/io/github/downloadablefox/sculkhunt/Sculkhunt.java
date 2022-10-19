package io.github.downloadablefox.sculkhunt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.downloadablefox.commands.SculkhuntDebugCommand;

public class Sculkhunt implements ModInitializer {
	public static final Boolean DEBUG = true; // should be false in production
	public static final String MOD_ID = "sculkhunt";
	public static final String MOD_NAME = "Sculkhunt+";
	public static final String MOD_VERSION = "0.0.1";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	private void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, context, environment) -> {
			if (DEBUG) SculkhuntDebugCommand.register(dispatcher);
		});
	}

	@Override
	public void onInitialize(ModContainer mod) {
		registerCommands();
		
		LOGGER.info("{} v{} has been initialized!", MOD_NAME, MOD_VERSION);
		if (DEBUG) LOGGER.warn("Debug mode is enabled! This should be disabled in production!");
	}
}
