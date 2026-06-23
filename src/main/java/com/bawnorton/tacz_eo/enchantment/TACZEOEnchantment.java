package com.bawnorton.tacz_eo.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class TACZEOEnchantment extends Enchantment {
	private final Supplier<Integer> baseEnchantability;
	private final Supplier<Integer> perLevelEnchantability;
	private final Supplier<Integer> maxLevel;
	private final Supplier<Integer> enchantabilitySpan;

	protected TACZEOEnchantment(Rarity rarity, Supplier<Integer> baseEnchantability, Supplier<Integer> perLevelEnchantability, Supplier<Integer> maxLevel, Supplier<Integer> enchantabilitySpan) {
		super(rarity, TACZEOEnchantments.GUN_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
		this.baseEnchantability = baseEnchantability;
		this.perLevelEnchantability = perLevelEnchantability;
		this.maxLevel = maxLevel;
		this.enchantabilitySpan = enchantabilitySpan;
	}

	@Override
	public int getMinCost(int level) {
		return baseEnchantability.get() + (level - 1) * perLevelEnchantability.get();
	}

	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + enchantabilitySpan.get();
	}

	@Override
	public int getMaxLevel() {
		return maxLevel.get();
	}

	public float apply(float original, int level, @Nullable ItemStack enchantedItem) {
		return 0;
	}
}
