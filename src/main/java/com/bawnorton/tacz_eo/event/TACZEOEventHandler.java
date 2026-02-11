package com.bawnorton.tacz_eo.event;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TACZEOEventHandler {
	public static void onEntityHurtByGunEvent(EntityHurtByGunEvent.Pre event) {
		LivingEntity attacker = event.getAttacker();
		if (attacker == null) return;

		ItemStack gun = attacker.getMainHandItem();
		applyFirepower(event, gun);
		applyBullseye(event, gun);
	}

	private static void applyFirepower(EntityHurtByGunEvent.Pre event, ItemStack gun) {
		TACZEOEnchantment firepower = TACZEOEnchantments.FIREPOWER.get();
		float damageBonus =  firepower.getGunDamageBonus(gun.getEnchantmentLevel(firepower), gun);
		event.setBaseAmount(event.getBaseAmount() + damageBonus);
	}

	private static void applyBullseye(EntityHurtByGunEvent.Pre event, ItemStack gun) {
		TACZEOEnchantment bullseye = TACZEOEnchantments.BULLSEYE.get();
		float headshotBonus = bullseye.getHeadshotBonus(gun.getEnchantmentLevel(bullseye));
		event.setHeadshotMultiplier(event.getHeadshotMultiplier() + headshotBonus);
	}
}
