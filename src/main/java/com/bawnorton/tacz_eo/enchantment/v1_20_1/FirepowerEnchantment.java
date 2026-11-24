/*!stonecutter-disabled
package com.bawnorton.tacz_eo.enchantment.v1_20_1;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class FirepowerEnchantment extends TACZEOEnchantment {
    public FirepowerEnchantment(Rarity rarity) {
        super(rarity,
                () -> TACZEOConfig.get().firepowerBaseEnchantability,
                () -> TACZEOConfig.get().firepowerLevelEnchantability,
                () -> TACZEOConfig.get().firepowerMaxLevel,
                () -> TACZEOConfig.get().firepowerEnchantabilitySpan
        );
    }

    @Override
    public float apply(float original, int level, @Nullable ItemStack enchantedItem) {
        return original + original * (TACZEOConfig.get().firepowerDamageMultiplier * level);
    }
}
*/