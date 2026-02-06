package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.resource.index.CommonGunIndex;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Optional;

public class FirepowerEnchantment extends TACZEOEnchantment {
	public FirepowerEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot slot) {
		super(rarity, category, new EquipmentSlot[]{slot});
	}

	@Override
	public int getMinCost(int level) {
		return TACZEOConfig.get().firepowerBaseEnchantability + (level - 1) * TACZEOConfig.get().firepowerLevelEnchantability;
	}

	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + 25;
	}

	@Override
	public int getMaxLevel() {
		return TACZEOConfig.get().firepowerMaxLevel;
	}

	@Override
	public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
		if(enchantedItem.getItem() instanceof IGun gun) {
			Optional<CommonGunIndex> commonGunIndex = TimelessAPI.getCommonGunIndex(gun.getGunId(enchantedItem));
			if(commonGunIndex.isPresent()) {
				return commonGunIndex.orElseThrow()
						.getBulletData()
						.getDamageAmount() * (TACZEOConfig.get().firepowerDamageMultiplier * level);
			}
		}
		return super.getDamageBonus(level, mobType, enchantedItem);
	}
}
