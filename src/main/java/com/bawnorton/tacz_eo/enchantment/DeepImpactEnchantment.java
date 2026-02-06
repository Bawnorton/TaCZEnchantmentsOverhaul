package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class DeepImpactEnchantment extends TACZEOEnchantment {
	public DeepImpactEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot slot) {
		super(rarity, category, new EquipmentSlot[]{slot});
	}

	@Override
	public int getMinCost(int level) {
		return TACZEOConfig.get().deepImpactBaseEnchantability + (level - 1) * TACZEOConfig.get().deepImpactLevelEnchantability;
	}

	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + TACZEOConfig.get().deepImpactEnchantabilitySpan;
	}

	@Override
	public int getMaxLevel() {
		return TACZEOConfig.get().deepImpactMaxLevel;
	}

	@Override
	public float getArmourPiercingBonus(int level) {
		return TACZEOConfig.get().deepImpactArmourPiercingBonus * level;
	}
}
