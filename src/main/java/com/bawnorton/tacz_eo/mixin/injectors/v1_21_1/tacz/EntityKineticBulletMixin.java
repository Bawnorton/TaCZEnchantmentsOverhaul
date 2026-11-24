package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.tacz;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tacz.guns.api.entity.ITargetEntity;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EntityKineticBullet.class, remap = false)
abstract class EntityKineticBulletMixin extends Projectile {
    protected EntityKineticBulletMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("MixinExtrasOperationParameters")
    @WrapOperation(
            method = "onHitEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/tacz/guns/api/entity/ITargetEntity;onProjectileHit(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/EntityHitResult;Lnet/minecraft/world/damagesource/DamageSource;F)V"
            )
    )
    private void applyEnchantmentDamageBonus(ITargetEntity instance, Entity entity, EntityHitResult entityHitResult, DamageSource damageSource, float damage, Operation<Void> original) {
        if (level() instanceof ServerLevel level) {
            float modified = EnchantmentHelper.modifyDamage(
                    level,
                    getOwner().getWeaponItem(),
                    (Entity) instance,
                    damageSource,
                    damage
            );
            original.call(instance, entity, entityHitResult, damageSource, modified);
        }
    }
}