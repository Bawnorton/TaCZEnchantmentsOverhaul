package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.gundb;

import com.bawnorton.mixinsquared.TargetHandler;
import com.bawnorton.tacz_eo.TACZEO;
import com.bawnorton.tacz_eo.mixin.plugin.annotation.IfModLoaded;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("gundb")
@Mixin(value = ModernKineticGunItem.class, priority = 1500, remap = false)
abstract class ModernKineticGunItemMixinSquared extends AbstractGunItem {
    protected ModernKineticGunItemMixinSquared(Properties properties) {
        super(properties);
    }

    @TargetHandler(
            mixin = "mod.cdv.gdb.mixin.ModernKineticGunMixin",
            name = "supportsEnchantment"
    )
    @ModifyExpressionValue(
            method = "@MixinSquared:Handler",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/Enchantment;canEnchant(Lnet/minecraft/world/item/ItemStack;)Z"
            )
    )
    private boolean allowConfiguredEnchantments(boolean original, ItemStack stack, Holder<Enchantment> enchantment) {
        if (original) return true;

        ResourceLocation enchantmentId = enchantment.unwrapKey().map(ResourceKey::location).orElse(null);
        return TACZEO.canApplyEnchantmentToGun(enchantmentId, getGunId(stack));
    }
}
