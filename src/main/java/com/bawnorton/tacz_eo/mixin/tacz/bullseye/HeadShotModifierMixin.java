package com.bawnorton.tacz_eo.mixin.tacz.bullseye;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.resource.modifier.custom.HeadShotModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HeadShotModifier.class)
abstract class HeadShotModifierMixin {
	@Definition(id = "getCache", method = "Lcom/tacz/guns/resource/modifier/AttachmentCacheProperty;getCache(Ljava/lang/String;)Ljava/lang/Object;")
	@Definition(id = "Float", type = Float.class)
	@Expression("(Float) ?.getCache(?)")
	@ModifyExpressionValue(
			method = "getPropertyDiagramsData",
			at = @At("MIXINEXTRAS:EXPRESSION"),
			remap = false
	)
	private Float applyEnchantmentHeadshotBonus(Float original, ItemStack gunItem) {
		TACZEOEnchantment bullseye = TACZEOEnchantments.BULLSEYE.get();
		float headshotBonus = bullseye.getHeadshotBonus(gunItem.getEnchantmentLevel(bullseye));
		return original + headshotBonus;
	}
}
