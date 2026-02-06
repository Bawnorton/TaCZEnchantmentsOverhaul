package com.bawnorton.tacz_eo.mixin.tacz.punch_through;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityKineticBullet.class)
abstract class EntityKineticBulletMixin {
	@Definition(id = "pierce", field = "Lcom/tacz/guns/entity/EntityKineticBullet;pierce:I")
	@Expression("?.pierce = @(?)")
	@ModifyExpressionValue(
			method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;ZLcom/tacz/guns/resource/pojo/data/gun/GunData;Lcom/tacz/guns/resource/pojo/data/gun/BulletData;)V",
			at = @At("MIXINEXTRAS:EXPRESSION")
	)
	private int applyEnchantmentPierceBonus(int original, @Local(argsOnly = true) ItemStack gunItem) {
		TACZEOEnchantment punchThrough = TACZEOEnchantments.PUNCH_THROUGH.get();
		int pierceBonus = punchThrough.getPierceBonus(gunItem.getEnchantmentLevel(punchThrough));
		return original + pierceBonus;
	}
}
