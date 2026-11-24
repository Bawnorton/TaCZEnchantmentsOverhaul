package com.bawnorton.tacz_eo.client.mixin.injectors.v1_21_1.tacz;

import com.bawnorton.tacz_eo.enchantment.v1_21_1.EnchantmentValueEffects;
import com.bawnorton.tacz_eo.enchantment.v1_21_1.HeadshotCondition;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.resource.modifier.custom.HeadShotModifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HeadShotModifier.class)
abstract class HeadShotModifierMixin {
    @Definition(id = "getCache", method = "Lcom/tacz/guns/resource/modifier/AttachmentCacheProperty;getCache(Ljava/lang/String;)Ljava/lang/Object;")
    @Definition(id = "Float", type = Float.class)
    @Expression("(Float) ?.getCache(?)")
    @ModifyExpressionValue(method = "getPropertyDiagramsData", at = @At("MIXINEXTRAS:EXPRESSION"), remap = false)
    private Float applyHeadshotDamage(Float original, ItemStack gunItem) {
        return EnchantmentValueEffects.apply(
                gunItem,
                EnchantmentEffectComponents.DAMAGE,
                original,
                RandomSource.create(0L),
                effect -> effect.requirements().filter(HeadshotCondition.class::isInstance).isPresent()
        );
    }
}
