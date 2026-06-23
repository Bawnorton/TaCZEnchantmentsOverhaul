package com.bawnorton.tacz_eo.mixin.tacz.capacity;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AttachmentDataUtils.class, remap = false)
abstract class AttachmentDataUtilsMixin {
	@ModifyReturnValue(
			method = "getAmmoCountWithAttachment",
			at = @At("RETURN")
	)
	private static int applyEnchantmentCapacity(int original, ItemStack gunItem) {
		TACZEOEnchantment capacity = TACZEOEnchantments.CAPACITY.get();
		return Math.round(capacity.apply(original, gunItem.getEnchantmentLevel(capacity), gunItem));
	}
}
