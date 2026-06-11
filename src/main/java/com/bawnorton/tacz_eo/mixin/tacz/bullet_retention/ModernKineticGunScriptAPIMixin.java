package com.bawnorton.tacz_eo.mixin.tacz.bullet_retention;

import com.bawnorton.tacz_eo.enchantment.BulletRetentionEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantment;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
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
		if(!consumeAmmo) {
			original.call(false);
			return;
		}

		TACZEOEnchantment enchantment = TACZEOEnchantments.BULLET_RETENTION.get();
        if (!(enchantment instanceof BulletRetentionEnchantment bulletRetention)) {
			original.call(true);
			return;
		}

        float chance = bulletRetention.apply(itemStack.getEnchantmentLevel(bulletRetention), itemStack);
        consumeAmmo = Math.random() <= chance;
        bulletRetention.setAmmoWillBeConsumed(consumeAmmo);
        original.call(consumeAmmo);
        bulletRetention.setAmmoWillBeConsumed(true);
    }
}
