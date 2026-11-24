package com.bawnorton.tacz_eo;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TACZEO {
    public static final String MOD_ID = "tacz_eo";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
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
