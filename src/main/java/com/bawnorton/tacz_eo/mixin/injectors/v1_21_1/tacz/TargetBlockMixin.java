package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.tacz;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tacz.guns.block.TargetBlock;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.init.ModDamageTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TargetBlock.class)
abstract class TargetBlockMixin {
    @WrapOperation(
            method = "onProjectileHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/tacz/guns/entity/EntityKineticBullet;getDamage(Lnet/minecraft/world/phys/Vec3;)F"
            )
    )
    private float applyEnchantmentDamageBonus(EntityKineticBullet instance, Vec3 hitVec, Operation<Float> original) {
        float damage = original.call(instance, hitVec);
        if (instance.level() instanceof ServerLevel level) {
            Entity owner = instance.getOwner();
            return EnchantmentHelper.modifyDamage(
                    level,
                    owner.getWeaponItem(),
                    instance,
                    ModDamageTypes.Sources.bullet(level.registryAccess(), instance, owner, false),
                    damage
            );
        }
        return damage;
    }
}
