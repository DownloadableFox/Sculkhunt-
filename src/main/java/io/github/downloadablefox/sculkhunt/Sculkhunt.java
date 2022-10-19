package io.github.downloadablefox.sculkhunt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sculkhunt implements ModInitializer {
	public static final String MOD_ID = "sculkhunt";
	public static final String MOD_NAME = "Sculkhunt";
	public static final String MOD_VERSION = "0.0.1";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", MOD_NAME);
	}
}
