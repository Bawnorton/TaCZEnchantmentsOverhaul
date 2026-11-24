package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.tacz;

import com.bawnorton.tacz_eo.enchantment.v1_21_1.EnchantmentValueEffects;
import com.bawnorton.tacz_eo.enchantment.v1_21_1.TACZEOEffectComponents;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EntityKineticBullet.class, remap = false)
abstract class ArmorIgnoreMixin {
    @Definition(id = "armorIgnore", field = "Lcom/tacz/guns/entity/EntityKineticBullet;armorIgnore:F")
    @Expression("?.armorIgnore = @(?)")
    @ModifyExpressionValue(
            method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;ZLcom/tacz/guns/resource/pojo/data/gun/GunData;Lcom/tacz/guns/resource/pojo/data/gun/BulletData;)V",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private float applyArmorIgnore(float original, @Local(argsOnly = true) ItemStack gunItem) {
        return EnchantmentValueEffects.apply(
                gunItem, TACZEOEffectComponents.ARMOR_IGNORE.get(), original,
                RandomSource.create(0L), effect -> effect.requirements().isEmpty()
        );
    }
}
