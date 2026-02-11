package com.bawnorton.tacz_eo.client.mixin.tacz.deep_impact;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.resource.modifier.custom.ArmorIgnoreModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorIgnoreModifier.class)
abstract class ArmorIgnoreModifierMixin {
	@Definition(id = "getCache", method = "Lcom/tacz/guns/resource/modifier/AttachmentCacheProperty;getCache(Ljava/lang/String;)Ljava/lang/Object;")
	@Definition(id = "Float", type = Float.class)
	@Expression("(Float) ?.getCache(?)")
	@ModifyExpressionValue(
			method = "getPropertyDiagramsData",
			at = @At("MIXINEXTRAS:EXPRESSION"),
			remap = false
	)
	private Float applyEnchantmentArmourPierceBonus(Float original, ItemStack gunItem) {
		TACZEOEnchantment deepImpact = TACZEOEnchantments.DEEP_IMPACT.get();
		float piercingBonus = deepImpact.getArmourPiercingBonus(gunItem.getEnchantmentLevel(deepImpact));
		return original + piercingBonus;
	}
}
