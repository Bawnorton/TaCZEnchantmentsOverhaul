package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.tacz;

import com.tacz.guns.item.ModernKineticGunItem;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ModernKineticGunItem.class, remap = false)
abstract class BulletSpreadMixin {
    @ModifyVariable(
            method = "doBulletSpread",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 1
    )
    private float applySpread(float original, ShooterDataHolder dataHolder, ItemStack gunItem, LivingEntity shooter) {
        if (shooter.level() instanceof ServerLevel level) {
            return EnchantmentHelper.processProjectileSpread(level, gunItem, shooter, original);
        }
        return original;
    }
}
