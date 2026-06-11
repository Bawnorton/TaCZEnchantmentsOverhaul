package com.bawnorton.tacz_eo;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Mod(TACZEO.MOD_ID)
public class TACZEO {
	public static final String MOD_ID = "tacz_eo";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public TACZEO(FMLJavaModLoadingContext context) {
		IEventBus bus = context.getModEventBus();
		TACZEOEnchantments.init(bus);
		LOGGER.debug("TaCZ Enchantments Overhaul Initialized");
	}

	public static boolean canApplyEnchantmentToGun(ResourceLocation enchantmentId, ResourceLocation itemId) {
		if (enchantmentId == null) return false;
		if (!TACZEOConfig.get().enchantmentsList.contains(enchantmentId)) return false;

		List<ResourceLocation> blacklistedGuns = TACZEOConfig.get().enchantmentsBlacklist.get(enchantmentId);
        return blacklistedGuns == null || !blacklistedGuns.contains(itemId);
    }

	public static ResourceLocation rl(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
