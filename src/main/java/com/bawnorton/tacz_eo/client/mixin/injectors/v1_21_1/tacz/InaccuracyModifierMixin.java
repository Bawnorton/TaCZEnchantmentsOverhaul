package com.bawnorton.tacz_eo.client.mixin.injectors.v1_21_1.tacz;

import com.bawnorton.tacz_eo.enchantment.v1_21_1.EnchantmentValueEffects;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tacz.guns.api.modifier.IAttachmentModifier;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.custom.InaccuracyModifier;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(value = InaccuracyModifier.class, remap = false)
abstract class InaccuracyModifierMixin {
    @Unique private static final ThreadLocal<ItemStack> tacz_eo$gunItem = new ThreadLocal<>();

    @WrapMethod(method = "getPropertyDiagramsData")
    private List<IAttachmentModifier.DiagramsData> captureGun(ItemStack gunItem, GunData gunData, AttachmentCacheProperty cacheProperty, Operation<List<IAttachmentModifier.DiagramsData>> original) {
        tacz_eo$gunItem.set(gunItem);
        try {
            return original.call(gunItem, gunData, cacheProperty);
        } finally {
            tacz_eo$gunItem.remove();
        }
    }

    @Definition(id = "get", method = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;")
    @Definition(id = "Float", type = Float.class)
    @Expression("(Float) ?.get(?)")
    @ModifyExpressionValue(
            method = "buildNormal",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private Float applyNormalSpread(Float original) {
        return tacz_eo$applySpread(original);
    }

    @Definition(id = "get", method = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;")
    @Definition(id = "Float", type = Float.class)
    @Expression("(Float) ?.get(?)")
    @ModifyExpressionValue(
            method = "buildAim",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private Float applyAimSpread(Float original) {
        return tacz_eo$applySpread(original);
    }

    @Unique
    private static float tacz_eo$applySpread(float original) {
        ItemStack gun = tacz_eo$gunItem.get();
        return gun == null ? original : EnchantmentValueEffects.apply(
                gun,
                EnchantmentEffectComponents.PROJECTILE_SPREAD,
                original,
                RandomSource.create(0L),
                effect -> effect.requirements().isEmpty()
        );
    }
}
