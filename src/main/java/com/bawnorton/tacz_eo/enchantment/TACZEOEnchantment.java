package com.bawnorton.tacz_eo.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class TACZEOEnchantment extends Enchantment {
	protected TACZEOEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] applicableSlots) {
		super(rarity, category, applicableSlots);
	}

	public float getGunDamageBonus(int level, ItemStack enchantedItem) {
		return 0;
	}

	public float getAccuracyBonus(int level) {
		return 0;
	}

	public float getArmourPiercingBonus(int level) {
		return 0;
	}

	public float getHeadshotBonus(int level) {
		return 0;
	}

	public int getPierceBonus(int level) {
		return 0;
	}
}
