package com.bawnorton.tacz_eo.mixin.tacz.precision;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import com.tacz.guns.resource.pojo.data.gun.InaccuracyType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModernKineticGunScriptAPI.class)
abstract class ModernKineticGunScriptAPIMixin {
	@Shadow
	private ItemStack itemStack;

	@Definition(id = "inaccuracy", local = @Local(type = float.class, name = "inaccuracy"))
	@Expression("inaccuracy = @(?)")
	@ModifyExpressionValue(
			method = "shootOnce",
			at = @At("MIXINEXTRAS:EXPRESSION"),
			remap = false
	)
	private float applyEnchantmentAccuracyBonus(float original, @Local(name = "inaccuracyType") InaccuracyType type) {
		TACZEOEnchantment precision = TACZEOEnchantments.PRECISION.get();
		float accuracyBonus = precision.getAccuracyBonus(itemStack.getEnchantmentLevel(precision));
		if(type == InaccuracyType.AIM) {
			return original - accuracyBonus;
		} else {
			return original - (accuracyBonus * original);
		}
	}
}
