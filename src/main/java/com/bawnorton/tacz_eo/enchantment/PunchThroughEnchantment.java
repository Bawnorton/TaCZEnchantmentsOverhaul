package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PunchThroughEnchantment extends TACZEOEnchantment {
	public PunchThroughEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot slot) {
		super(rarity, category, new EquipmentSlot[]{slot});
	}

	@Override
	public int getMinCost(int level) {
		return TACZEOConfig.get().punchThroughBaseEnchantability + (level - 1) * TACZEOConfig.get().punchThroughLevelEnchantability;
	}

	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + TACZEOConfig.get().punchThroughEnchantabilitySpan;
	}

	public int getMaxLevel() {
		return TACZEOConfig.get().punchThroughMaxLevel;
	}

	@Override
	public int getPierceBonus(int level) {
		return TACZEOConfig.get().punchThroughCount * level;
	}
}
