package com.bawnorton.tacz_eo.mixin.injectors.v1_21_1.tacz;

import com.tacz.guns.api.item.gun.AbstractGunItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
abstract class GunEnchantmentTooltipMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(
            method = "addToTooltip",
            at = @At("HEAD"),
            cancellable = true
    )
    private <T extends TooltipProvider> void hideVanillaEnchantmentLines(DataComponentType<T> component, Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, CallbackInfo ci) {
        if (component == DataComponents.ENCHANTMENTS && getItem() instanceof AbstractGunItem) {
            ci.cancel();
        }
    }
}
