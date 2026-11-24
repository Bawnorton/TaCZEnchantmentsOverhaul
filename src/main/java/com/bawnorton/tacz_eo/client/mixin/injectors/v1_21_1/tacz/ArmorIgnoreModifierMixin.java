package com.bawnorton.tacz_eo.client.mixin.injectors.v1_21_1.tacz;

import com.bawnorton.tacz_eo.enchantment.v1_21_1.EnchantmentValueEffects;
import com.bawnorton.tacz_eo.enchantment.v1_21_1.TACZEOEffectComponents;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.resource.modifier.custom.ArmorIgnoreModifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ArmorIgnoreModifier.class, remap = false)
abstract class ArmorIgnoreModifierMixin {
    @Definition(id = "getCache", method = "Lcom/tacz/guns/resource/modifier/AttachmentCacheProperty;getCache(Ljava/lang/String;)Ljava/lang/Object;")
    @Definition(id = "Float", type = Float.class)
    @Expression("(Float) ?.getCache(?)")
    @ModifyExpressionValue(
            method = "getPropertyDiagramsData",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private Float applyArmorIgnore(Float original, ItemStack gunItem) {
        return EnchantmentValueEffects.apply(
                gunItem,
                TACZEOEffectComponents.ARMOR_IGNORE.get(),
                original,
                RandomSource.create(0L),
                effect -> effect.requirements().isEmpty()
        );
    }
}
