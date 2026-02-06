package com.bawnorton.tacz_eo.mixin.tacz;

import com.bawnorton.tacz_eo.TACZEO;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractGunItem.class)
abstract class AbstractGunItemMixin extends ItemMixin implements IForgeItem, IGun {
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		ResourceLocation enchantmentId = ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
		ResourceLocation gunId = getGunId(stack);
		if (TACZEO.canApplyEnchantmentToGun(enchantmentId, gunId)) {
			return true;
		}
		return enchantment.category.canEnchant(stack.getItem());
	}

	@Override
	public int getEnchantmentValue(ItemStack stack) {
		return 15;
	}

	@Override
	public int getDefaultTooltipHideFlags(@NotNull ItemStack stack) {
		return ItemStack.TooltipPart.ENCHANTMENTS.getMask();
	}

	@Override
	protected boolean overrideIsEnchantable(ItemStack stack, Operation<Boolean> original) {
		return true;
	}
}
