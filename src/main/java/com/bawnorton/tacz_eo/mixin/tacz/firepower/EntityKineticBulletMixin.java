package com.bawnorton.tacz_eo.mixin.tacz.firepower;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
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

    @ModifyReturnValue(
            method = "getDamage",
            at = @At("RETURN")
    )
    private float applyEnchantmentDamageBonus(float original) {
        if(getOwner() instanceof LivingEntity attacker) {
            ItemStack gun = attacker.getMainHandItem();
            TACZEOEnchantment firepower = TACZEOEnchantments.FIREPOWER.get();
            return firepower.apply(original, gun.getEnchantmentLevel(firepower), gun);
        }
        return original;
    }
}
