package com.bawnorton.tacz_eo.mixin.tacz.punch_through;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.resource.modifier.custom.PierceModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PierceModifier.class)
abstract class PierceModifierMixin {
	@Definition(id = "getCache", method = "Lcom/tacz/guns/resource/modifier/AttachmentCacheProperty;getCache(Ljava/lang/String;)Ljava/lang/Object;")
	@Definition(id = "Integer", type = Integer.class)
	@Expression("(Integer) ?.getCache(?)")
	@ModifyExpressionValue(
			method = "getPropertyDiagramsData",
			at = @At("MIXINEXTRAS:EXPRESSION"),
			remap = false
	)
	private Integer applyEnchantmentPierceBonus(Integer original, ItemStack gunItem) {
		TACZEOEnchantment punchThrough = TACZEOEnchantments.PUNCH_THROUGH.get();
		int pierceBonus = punchThrough.getPierceBonus(gunItem.getEnchantmentLevel(punchThrough));
		return original + pierceBonus;
	}
}
