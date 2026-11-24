package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.tacz_tweaks;

import com.bawnorton.tacz_eo.mixin.plugin.annotation.IfModLoaded;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.entity.EntityKineticBullet;
import me.muksc.tacztweaks.feature.datapack.legacy.BulletInteraction;
import me.muksc.tacztweaks.feature.datapack.legacy.manager.BulletInteractionManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("tacztweaks")
@Mixin(value = BulletInteractionManager.class, remap = false)
abstract class BulletInteractionManagerMixin {
    @ModifyExpressionValue(
            method = "handleEntityInteraction",
            at = @At(
                    value = "INVOKE",
                    target = "Lme/muksc/tacztweaks/feature/datapack/legacy/BulletInteraction$Entity;getPierce()Lme/muksc/tacztweaks/feature/datapack/legacy/BulletInteraction$Pierce;"
            )
    )
    private BulletInteraction.Pierce applyPiercingEffects(BulletInteraction.Pierce original, EntityKineticBullet bullet) {
        if (original instanceof BulletInteraction.Pierce.Count count
                && bullet.level() instanceof ServerLevel level
                && bullet.getOwner() instanceof LivingEntity shooter
        ) {
            ItemStack gun = shooter.getMainHandItem();
            int bonus = EnchantmentHelper.getPiercingCount(level, gun, ItemStack.EMPTY);
            if (bonus > 0) {
                return new BulletInteraction.Pierce.Count(
                        count.getCount() + bonus,
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
