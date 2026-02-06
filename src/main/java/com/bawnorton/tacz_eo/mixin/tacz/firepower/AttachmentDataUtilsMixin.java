package com.bawnorton.tacz_eo.mixin.tacz.firepower;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AttachmentDataUtils.class)
abstract class AttachmentDataUtilsMixin {
	@ModifyReturnValue(
			method = "getDamageWithAttachment",
			at = @At("TAIL"),
			remap = false
	)
	private static double applyEnchantmentDamageBonus(double original, ItemStack gunItem, GunData gunData) {
		TACZEOEnchantment firepower = TACZEOEnchantments.FIREPOWER.get();
		float damageBonus = firepower.getDamageBonus(gunItem.getEnchantmentLevel(firepower), MobType.UNDEFINED, gunItem);
		return original + damageBonus;
	}
}
