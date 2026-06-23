package com.bawnorton.tacz_eo.mixin.tacz_tweaks;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.entity.EntityKineticBullet;
import me.muksc.tacztweaks.data.BulletInteraction;
import me.muksc.tacztweaks.data.manager.BulletInteractionManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BulletInteractionManager.class, remap = false)
abstract class BulletInteractionManagerMixin {
	@ModifyExpressionValue(
			method = "handleEntityInteraction",
			at = @At(
					value = "INVOKE",
					target = "Lme/muksc/tacztweaks/data/BulletInteraction$Entity;getPierce()Lme/muksc/tacztweaks/data/BulletInteraction$Pierce;"
			)
	)
	private BulletInteraction.Pierce applyEnchantmentPierceBonus(BulletInteraction.Pierce original, EntityKineticBullet ammo) {
		if(original instanceof BulletInteraction.Pierce.Count count) {
			int pierce = count.getCount();
			Entity owner = ammo.getOwner();
			if(owner instanceof LivingEntity living) {
				ItemStack held = living.getMainHandItem();
				TACZEOEnchantment punchThrough = TACZEOEnchantments.PUNCH_THROUGH.get();
				return new BulletInteraction.Pierce.Count(
						(int) punchThrough.apply(pierce, held.getEnchantmentLevel(punchThrough), null),
						count.getConditional(),
						count.getDamageFalloff(),
						count.getDamageMultiplier(),
						count.getRenderBulletHole()
				);
			}
		}
		return original;
	}
}
