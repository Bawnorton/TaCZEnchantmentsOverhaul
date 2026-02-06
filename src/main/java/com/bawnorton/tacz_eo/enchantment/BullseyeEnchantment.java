package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BullseyeEnchantment extends TACZEOEnchantment {
	public BullseyeEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot slot) {
		super(rarity, category, new EquipmentSlot[]{slot});
	}

	@Override
	public int getMinCost(int level) {
		return TACZEOConfig.get().bullseyeBaseEnchantability + (level - 1) * TACZEOConfig.get().bullseyeLevelEnchantability;
	}

	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + TACZEOConfig.get().bullseyeEnchantabilitySpan;
	}

	@Override
	public int getMaxLevel() {
		return TACZEOConfig.get().bullseyeMaxLevel;
	}

	@Override
	public float getHeadshotBonus(int level) {
		return TACZEOConfig.get().bullseyeHeadshotBonus * level;
	}
}
