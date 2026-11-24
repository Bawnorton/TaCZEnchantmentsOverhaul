package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.tacz;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ModernKineticGunScriptAPI.class, remap = false)
abstract class ShotEffectsMixin {
    @Shadow
    private ItemStack itemStack;
    @Shadow
    private LivingEntity shooter;

    @WrapMethod(method = "reduceAmmoOnce")
    private boolean applyAmmoUse(Operation<Boolean> original) {
        if (shooter.level() instanceof ServerLevel level) {
            if (EnchantmentHelper.processAmmoUse(level, itemStack, ItemStack.EMPTY, 1) == 0) {
                return true;
            }
        }
        return original.call();
    }
}

