package com.bawnorton.tacz_eo.client.mixin.tacz.firepower;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.tacz.guns.resource.modifier.custom.DamageModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DamageModifier.class)
abstract class DamageModifierMixin {
	@Definition(id = "getDamage", method = "Lcom/tacz/guns/resource/pojo/data/gun/ExtraDamage$DistanceDamagePair;getDamage()F")
	@Definition(id = "modifiedValue", local = @Local(type = float.class, name = "modifiedValue"))
	@Expression("modifiedValue = @(?.getDamage())")
	@ModifyExpressionValue(
			method = "getPropertyDiagramsData",
			at = @At("MIXINEXTRAS:EXPRESSION"),
			remap = false
	)
	private float applyEnchantmentDamageBonus(float original, ItemStack gunItem) {
		TACZEOEnchantment firepower = TACZEOEnchantments.FIREPOWER.get();
		float damageBonus = firepower.getGunDamageBonus(gunItem.getEnchantmentLevel(firepower), gunItem);
		return original + damageBonus;
	}
}
