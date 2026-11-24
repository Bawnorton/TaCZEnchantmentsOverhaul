package com.bawnorton.tacz_eo.client.mixin.injectors.v1_21_1.tacz;

import com.bawnorton.tacz_eo.enchantment.v1_21_1.EnchantmentValueEffects;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.tacz.guns.resource.modifier.custom.DamageModifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DamageModifier.class)
abstract class DamageModifierMixin {
    @Definition(id = "getDamage", method = "Lcom/tacz/guns/resource/pojo/data/gun/ExtraDamage$DistanceDamagePair;getDamage()F")
    @Definition(id = "modifiedValue", local = @Local(type = float.class, name = "modifiedValue"))
    @Expression("modifiedValue = @(?.getDamage())")
    @ModifyExpressionValue(
            method = "getPropertyDiagramsData",
            at = @At("MIXINEXTRAS:EXPRESSION"),
            remap = false
    )
    private float applyEnchantmentDamageBonus(float original, ItemStack gunItem) {
        return EnchantmentValueEffects.apply(
                gunItem,
                EnchantmentEffectComponents.DAMAGE,
                original,
                RandomSource.create(0L),
                effect -> effect.requirements().isEmpty()
        );
    }
}
