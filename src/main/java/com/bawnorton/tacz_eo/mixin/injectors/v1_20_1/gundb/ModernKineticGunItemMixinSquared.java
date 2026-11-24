/*!stonecutter-disabled
package com.bawnorton.tacz_eo.mixin.injectors.v1_20_1.gundb;

import com.bawnorton.mixinsquared.TargetHandler;
import com.bawnorton.tacz_eo.TACZEO;
import com.bawnorton.tacz_eo.mixin.plugin.annotation.IfModLoaded;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.item.ModernKineticGunItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@IfModLoaded("gundb")
@Mixin(value = ModernKineticGunItem.class, priority = 1500, remap = false)
abstract class ModernKineticGunItemMixinSquared extends AbstractGunItem {
    protected ModernKineticGunItemMixinSquared(Properties pProperties) {
        super(pProperties);
    }

    @TargetHandler(
            mixin = "mod.cdv.gdb.mixin.ModernKineticGunMixin",
            name = "canApplyAtEnchantingTable"
    )
    @ModifyExpressionValue(
            method = "@MixinSquared:Handler",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentCategory;canEnchant(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean allowConfiguredEnchantments(boolean original, ItemStack stack, Enchantment enchantment) {
        if (original) return true;

        ResourceLocation enchantmentId = ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
        ResourceLocation gunId = getGunId(stack);
        return TACZEO.canApplyEnchantmentToGun(enchantmentId, gunId);
    }
}*/