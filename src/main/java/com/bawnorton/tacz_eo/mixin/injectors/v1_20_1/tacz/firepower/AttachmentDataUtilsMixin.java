/*!stonecutter-disabled
package com.bawnorton.tacz_eo.mixin.injectors.v1_20_1.tacz.firepower;

import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.bawnorton.tacz_eo.enchantment.v1_20_1.TACZEOEnchantment;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AttachmentDataUtils.class, remap = false)
abstract class AttachmentDataUtilsMixin {
    @ModifyReturnValue(
            method = "getDamageWithAttachment",
            at = @At("TAIL")
    )
    private static double applyEnchantmentDamageBonus(double original, ItemStack gunItem, GunData gunData) {
        TACZEOEnchantment firepower = TACZEOEnchantments.FIREPOWER.get();
        return firepower.apply((float) original, gunItem.getEnchantmentLevel(firepower), gunItem);
    }
}
*/