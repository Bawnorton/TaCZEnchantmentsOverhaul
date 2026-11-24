/*!stonecutter-disabled
package com.bawnorton.tacz_eo.mixin.injectors.v1_20_1.tacz.bullet_retention;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.bawnorton.tacz_eo.enchantment.v1_20_1.BulletRetentionEnchantment;
import com.bawnorton.tacz_eo.enchantment.v1_20_1.TACZEOEnchantment;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ModernKineticGunScriptAPI.class, remap = false)
abstract class ModernKineticGunScriptAPIMixin {
    @Shadow
    private ItemStack itemStack;

    @WrapMethod(
            method = "shootOnce"
    )
    private void applyEnchantmentAmmoConsumeChance(boolean consumeAmmo, Operation<Void> original) {
        if (!consumeAmmo) {
            original.call(false);
            return;
        }

        TACZEOEnchantment enchantment = TACZEOEnchantments.BULLET_RETENTION.get();
        if (!(enchantment instanceof BulletRetentionEnchantment bulletRetention)) {
            original.call(true);
            return;
        }

        float chance = bulletRetention.apply(0, itemStack.getEnchantmentLevel(bulletRetention), itemStack);
        consumeAmmo = Math.random() <= chance;
        bulletRetention.setAmmoWillBeConsumed(consumeAmmo);
        original.call(consumeAmmo);
        bulletRetention.setAmmoWillBeConsumed(true);
    }
}*/