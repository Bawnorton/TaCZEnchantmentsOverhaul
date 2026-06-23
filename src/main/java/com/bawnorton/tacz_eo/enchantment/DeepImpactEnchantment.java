package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class DeepImpactEnchantment extends TACZEOEnchantment {
	public DeepImpactEnchantment(Rarity rarity) {
		super(rarity,
				() -> TACZEOConfig.get().deepImpactBaseEnchantability,
				() -> TACZEOConfig.get().deepImpactLevelEnchantability,
				() -> TACZEOConfig.get().deepImpactMaxLevel,
				() -> TACZEOConfig.get().deepImpactEnchantabilitySpan
		);
	}

	@Override
	public float apply(float original, int level, @Nullable ItemStack enchantedItem) {
		return original + TACZEOConfig.get().deepImpactArmourPiercingBonus * level;
	}
}
