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

@Mixin(BulletInteractionManager.class)
abstract class BulletInteractionManagerMixin {
	@ModifyExpressionValue(
			method = "handleEntityInteraction",
			at = @At(
					value = "INVOKE",
					target = "Lme/muksc/tacztweaks/data/BulletInteraction$Entity;getPierce()Lme/muksc/tacztweaks/data/BulletInteraction$Pierce;"
			),
			remap = false
	)
	private BulletInteraction.Pierce applyEnchantmentPierceBonus(BulletInteraction.Pierce original, EntityKineticBullet bullet) {
		if(original instanceof BulletInteraction.Pierce.Count count) {
			int pierce = count.getCount();
			Entity owner = bullet.getOwner();
			if(owner instanceof LivingEntity living) {
				ItemStack held = living.getMainHandItem();
				TACZEOEnchantment punchThrough = TACZEOEnchantments.PUNCH_THROUGH.get();
				pierce += punchThrough.getPierceBonus(held.getEnchantmentLevel(punchThrough));
				return new BulletInteraction.Pierce.Count(
						pierce,
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
