package com.bawnorton.tacz_eo.mixin.tacz.bullseye;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AttachmentDataUtils.class)
abstract class AttachmentDataUtilsMixin {
	@ModifyReturnValue(
			method = "getHeadshotMultiplier",
			at = @At("TAIL"),
			remap = false
	)
	private static double applyEnchantmentHeadshotBonus(double original, ItemStack gunItem, GunData gunData) {
		TACZEOEnchantment bullseye = TACZEOEnchantments.BULLSEYE.get();
		float headshotBonus = bullseye.getHeadshotBonus(gunItem.getEnchantmentLevel(bullseye));
		return original + headshotBonus;
	}
}
