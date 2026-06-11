package com.bawnorton.tacz_eo.mixin.shotsfired;

import com.bawnorton.tacz_eo.enchantment.BulletRetentionEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.tacz.guns.api.event.common.GunFireEvent;
import net.marblednull.shotsfired.ModEvents;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ModEvents.class, remap = false)
abstract class ModEventsMixin {
    @WrapWithCondition(
            method = "weaponFireEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/marblednull/shotsfired/ModEvents;spawnCasing(Lcom/tacz/guns/api/event/common/GunFireEvent;Lnet/minecraft/world/item/Item;DLjava/lang/String;)V"
            )
    )
    private static boolean dontSpawnCasingIfBulletWasRetained(GunFireEvent gunEvent, Item casingItem, double dropChance, String gunId) {
        TACZEOEnchantment enchantment = TACZEOEnchantments.BULLET_RETENTION.get();
        if (!(enchantment instanceof BulletRetentionEnchantment bulletRetention)) return true;

        return bulletRetention.wasAmmoConsumed();
    }
}
