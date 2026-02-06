package com.bawnorton.tacz_eo;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.bawnorton.tacz_eo.event.TACZEOEventHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@Mod(TACZEO.MOD_ID)
public class TACZEO {
	public static final String MOD_ID = "tacz_eo";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public TACZEO() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		TACZEOEnchantments.DEFERRED_REGISTER.register(bus);
		LOGGER.debug("TaCZ Enchantments Overhaul Initialized");
	}

	public static boolean canApplyEnchantmentToGun(ResourceLocation enchantmentId, ResourceLocation itemId) {
		return TACZEOConfig.get().enchantmentsMap.getOrDefault(enchantmentId, Collections.emptyList()).contains(itemId);
	}

	public static ResourceLocation rl(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
