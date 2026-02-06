package com.bawnorton.tacz_eo.mixin.tacz_durability;

import com.bawnorton.mixinsquared.TargetHandler;
import com.bawnorton.tacz_eo.TACZEO;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ModernKineticGunItem.class, priority = 1500)
abstract class ModernKineticGunItemMixinSquared extends AbstractGunItem {
	protected ModernKineticGunItemMixinSquared(Properties pProperties) {
		super(pProperties);
	}

	@TargetHandler(
			mixin = "mod.cdv.gdb.mixin.ModernKineticGunMixin",
			name = "canApplyAtEnchantingTable"
	)
	@ModifyExpressionValue(
			method = "@MixinSquared:Handler",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/item/enchantment/EnchantmentCategory;canEnchant(Lnet/minecraft/world/item/Item;)Z"
			)
	)
	private boolean forwardToTACZEO(boolean original, ItemStack stack, Enchantment enchantment) {
		if (original) return true;

		ResourceLocation enchantmentId = ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
		ResourceLocation gunId = getGunId(stack);
		return TACZEO.canApplyEnchantmentToGun(enchantmentId, gunId);
	}
}
