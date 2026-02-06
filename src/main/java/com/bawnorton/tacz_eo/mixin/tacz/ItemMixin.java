package com.bawnorton.tacz_eo.mixin.tacz;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
abstract class ItemMixin {
	@WrapMethod(
			method = "isEnchantable"
	)
	protected boolean overrideIsEnchantable(ItemStack stack, Operation<Boolean> original) {
		return original.call(stack);
	}
}
