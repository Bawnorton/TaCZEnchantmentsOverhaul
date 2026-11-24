package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.tacz;

import com.bawnorton.tacz_eo.enchantment.v1_21_1.EnchantmentValueEffects;
import com.bawnorton.tacz_eo.enchantment.v1_21_1.TACZEOEffectComponents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AttachmentDataUtils.class, remap = false)
abstract class AttachmentDataUtilsMixin {
    @ModifyReturnValue(
            method = "getAmmoCountWithAttachment",
            at = @At("RETURN")
    )
    private static int applyCapacity(int original, ItemStack gunItem, GunData gunData) {
        return Math.max(1, Math.round(EnchantmentValueEffects.apply(
                gunItem, TACZEOEffectComponents.MAGAZINE_CAPACITY.get(), original,
                RandomSource.create(0L), effect -> effect.requirements().isEmpty()
        )));
    }

    @ModifyReturnValue(
            method = "getArmorIgnoreWithAttachment",
            at = @At("RETURN")
    )
    private static double applyArmorIgnore(double original, ItemStack gunItem, GunData gunData) {
        return EnchantmentValueEffects.apply(
                gunItem, TACZEOEffectComponents.ARMOR_IGNORE.get(), (float) original,
                RandomSource.create(0L), effect -> effect.requirements().isEmpty()
        );
    }
}

