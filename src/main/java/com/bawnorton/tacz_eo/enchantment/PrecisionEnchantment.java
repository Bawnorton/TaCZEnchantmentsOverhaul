package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class PrecisionEnchantment extends TACZEOEnchantment {
	public PrecisionEnchantment(Rarity rarity) {
		super(rarity,
				() -> TACZEOConfig.get().precisionBaseEnchantability,
				() -> TACZEOConfig.get().precisionLevelEnchantability,
				() -> TACZEOConfig.get().precisionMaxLevel,
				() -> TACZEOConfig.get().precisionEnchantabilitySpan
		);
	}

	@Override
	public float apply(int level, @Nullable ItemStack enchantedItem) {
		return TACZEOConfig.get().precisionAccuracyBonus * level;
	}
}
