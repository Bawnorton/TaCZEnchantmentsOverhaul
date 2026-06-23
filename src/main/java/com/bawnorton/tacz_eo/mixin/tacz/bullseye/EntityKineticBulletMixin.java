package com.bawnorton.tacz_eo.mixin.tacz.bullseye;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EntityKineticBullet.class, remap = false)
abstract class EntityKineticBulletMixin extends Projectile {
    protected EntityKineticBulletMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Definition(id = "headShot", field = "Lcom/tacz/guns/entity/EntityKineticBullet;headShot:F")
    @Expression("?(@(?.headShot), ?)")
    @ModifyExpressionValue(
            method = "onHitEntity",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private float applyEnchantmentHeadshotBonus(float original) {
        if(getOwner() instanceof LivingEntity attacker) {
            ItemStack gun = attacker.getMainHandItem();
            TACZEOEnchantment bullseye = TACZEOEnchantments.BULLSEYE.get();
            return bullseye.apply(original, gun.getEnchantmentLevel(bullseye), gun);
        }
        return original;
    }
}
