package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.tacz;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EntityKineticBullet.class, remap = false)
abstract class PiercingMixin {
    @Definition(id = "pierce", field = "Lcom/tacz/guns/entity/EntityKineticBullet;pierce:I")
    @Expression("?.pierce = @(?)")
    @ModifyExpressionValue(
            method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;ZLcom/tacz/guns/resource/pojo/data/gun/GunData;Lcom/tacz/guns/resource/pojo/data/gun/BulletData;)V",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private int applyPiercing(int original, @Local(argsOnly = true) ItemStack gunItem) {
        EntityKineticBullet bullet = (EntityKineticBullet) (Object) this;
        if (bullet.level() instanceof ServerLevel level) {
            return original + EnchantmentHelper.getPiercingCount(level, gunItem, ItemStack.EMPTY);
        }
        return original;
    }
}

