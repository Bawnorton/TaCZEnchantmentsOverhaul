package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class BullseyeEnchantment extends TACZEOEnchantment {
	public BullseyeEnchantment(Rarity rarity) {
		super(rarity,
				() -> TACZEOConfig.get().bullseyeBaseEnchantability,
				() -> TACZEOConfig.get().bullseyeLevelEnchantability,
				() -> TACZEOConfig.get().bullseyeMaxLevel,
				() -> TACZEOConfig.get().bullseyeEnchantabilitySpan
		);
	}

	@Override
	public float apply(float original, int level, @Nullable ItemStack enchantedItem) {
		return original + TACZEOConfig.get().bullseyeHeadshotBonus * level;
	}
}
