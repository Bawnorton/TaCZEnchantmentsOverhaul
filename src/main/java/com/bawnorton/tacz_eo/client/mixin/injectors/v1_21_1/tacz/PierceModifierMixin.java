package com.bawnorton.tacz_eo.client.mixin.injectors.v1_21_1.tacz;

import com.bawnorton.tacz_eo.enchantment.v1_21_1.EnchantmentValueEffects;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.resource.modifier.custom.PierceModifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = PierceModifier.class, remap = false)
abstract class PierceModifierMixin {
    @Definition(id = "getCache", method = "Lcom/tacz/guns/resource/modifier/AttachmentCacheProperty;getCache(Ljava/lang/String;)Ljava/lang/Object;")
    @Definition(id = "Integer", type = Integer.class)
    @Expression("(Integer) ?.getCache(?)")
    @ModifyExpressionValue(
            method = "getPropertyDiagramsData",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private Integer applyPiercing(Integer original, ItemStack gunItem) {
        return Math.round(EnchantmentValueEffects.apply(
                gunItem,
                EnchantmentEffectComponents.PROJECTILE_PIERCING,
                original,
                RandomSource.create(0L),
                effect -> effect.requirements().isEmpty())
        );
    }
}
