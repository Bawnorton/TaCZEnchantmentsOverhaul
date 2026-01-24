package com.bawnorton.tacz_eo;

import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TACZEO.MOD_ID)
public class TACZEO {
	public static final String MOD_ID = "tacz_eo";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public TACZEO() {
		LOGGER.info("TACZ Enchantments Overhaul Initialized");
	}
}
