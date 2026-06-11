package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class BulletRetentionEnchantment extends TACZEOEnchantment {
    private final ThreadLocal<Boolean> consumeFlag = ThreadLocal.withInitial(() -> true);

    public BulletRetentionEnchantment(Rarity rarity) {
        super(rarity,
                () -> TACZEOConfig.get().bulletRetentionBaseEnchantability,
                () -> TACZEOConfig.get().bulletRetentionLevelEnchantability,
                () -> TACZEOConfig.get().bulletRetentionMaxLevel,
                () -> TACZEOConfig.get().bulletRetentionEnchantabilitySpan
        );
    }

    @Override
    public float apply(int level, @Nullable ItemStack enchantedItem) {
        return Mth.clamp(1 - TACZEOConfig.get().bulletRetentionChance * level, 0, 1);
    }

    public void setAmmoWillBeConsumed(boolean flag){
        consumeFlag.set(flag);
    }

    public boolean wasAmmoConsumed() {
        return consumeFlag.get();
    }
}
