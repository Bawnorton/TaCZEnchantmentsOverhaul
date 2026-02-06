package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PrecisionEnchantment extends TACZEOEnchantment {
	public PrecisionEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot slot) {
		super(rarity, category, new EquipmentSlot[]{slot});
	}

	@Override
	public int getMinCost(int level) {
		return TACZEOConfig.get().precisionBaseEnchantability + (level - 1) * TACZEOConfig.get().precisionLevelEnchantability;
	}

	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + TACZEOConfig.get().precisionEnchantabilitySpan;
	}

	public int getMaxLevel() {
		return TACZEOConfig.get().precisionMaxLevel;
	}

	@Override
	public float getAccuracyBonus(int level) {
		return TACZEOConfig.get().precisionAccuracyBonus * level;
	}
}
