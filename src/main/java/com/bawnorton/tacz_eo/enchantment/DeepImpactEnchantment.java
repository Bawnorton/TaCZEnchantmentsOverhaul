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
	public float apply(int level, @Nullable ItemStack enchantedItem) {
		return TACZEOConfig.get().deepImpactArmourPiercingBonus * level;
	}
}
