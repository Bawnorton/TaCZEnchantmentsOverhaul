package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.tacz;

import com.bawnorton.tacz_eo.TACZEO;
import com.bawnorton.tacz_eo.mixin.injectors.tacz.ItemMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AbstractGunItem.class, remap = false)
abstract class AbstractGunItemMixin extends ItemMixin implements IItemExtension, IGun {
    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        ResourceLocation enchantmentId = enchantment.unwrapKey().map(ResourceKey::location).orElse(null);
        ResourceLocation gunId = getGunId(stack);
        if (TACZEO.canApplyEnchantmentToGun(enchantmentId, gunId)) {
            return true;
        }
        return IItemExtension.super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 15;
    }

    @Override
    protected boolean overrideIsEnchantable(ItemStack stack, Operation<Boolean> original) {
        return true;
    }
}