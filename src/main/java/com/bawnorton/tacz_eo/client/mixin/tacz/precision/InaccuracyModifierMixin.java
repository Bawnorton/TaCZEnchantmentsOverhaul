package com.bawnorton.tacz_eo.client.mixin.tacz.precision;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tacz.guns.api.modifier.IAttachmentModifier;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.custom.InaccuracyModifier;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(InaccuracyModifier.class)
abstract class InaccuracyModifierMixin {
	@Unique
	private static final ThreadLocal<ItemStack> tacz_eo$gunItemCapture = new ThreadLocal<>();

	@WrapMethod(
			method = "getPropertyDiagramsData",
			remap = false
	)
	private List<IAttachmentModifier.DiagramsData> captureGunItem(ItemStack gunItem, GunData gunData, AttachmentCacheProperty cacheProperty, Operation<List<IAttachmentModifier.DiagramsData>> original) {
		tacz_eo$gunItemCapture.set(gunItem);
		try {
			return original.call(gunItem, gunData, cacheProperty);
		} finally {
			tacz_eo$gunItemCapture.remove();
		}
	}

	@Definition(id = "get", method = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;")
	@Definition(id = "Float", type = Float.class)
	@Expression("(Float) ?.get(?)")
	@ModifyExpressionValue(
			method = "buildAim",
			at = @At("MIXINEXTRAS:EXPRESSION"),
			remap = false
	)
	private Float applyEnchantmentAccuracyBonus(Float original) {
		ItemStack gunItem = tacz_eo$gunItemCapture.get();
		TACZEOEnchantment precision = TACZEOEnchantments.PRECISION.get();
		float accuracyBonus = precision.getAccuracyBonus(gunItem.getEnchantmentLevel(precision));
		return original - accuracyBonus;
	}

	@Definition(id = "get", method = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;")
	@Definition(id = "Float", type = Float.class)
	@Expression("(Float) ?.get(?)")
	@ModifyExpressionValue(
			method = "buildNormal",
			at = @At("MIXINEXTRAS:EXPRESSION"),
			remap = false
	)
	private Float applyEnchantmentAccuracyBonusMultiplicative(Float original) {
		ItemStack gunItem = tacz_eo$gunItemCapture.get();
		TACZEOEnchantment precision = TACZEOEnchantments.PRECISION.get();
		float accuracyBonus = precision.getAccuracyBonus(gunItem.getEnchantmentLevel(precision));
		return original - (accuracyBonus * original);
	}
}
