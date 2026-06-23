package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.Bolt;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;

public class CapacityEnchantment extends TACZEOEnchantment {
    public CapacityEnchantment(Rarity rarity) {
        super(rarity,
                () -> TACZEOConfig.get().capacityBaseEnchantability,
                () -> TACZEOConfig.get().capacityLevelEnchantability,
                () -> TACZEOConfig.get().capacityMaxLevel,
                () -> TACZEOConfig.get().capacityEnchantabilitySpan
        );
    }

    @Override
    public float apply(float original, int level, @Nullable ItemStack enchantedItem) {
        if(enchantedItem != null && enchantedItem.getItem() instanceof IGun gun) {
            Optional<CommonGunIndex> commonGunIndex = TimelessAPI.getCommonGunIndex(gun.getGunId(enchantedItem));
            if(commonGunIndex.isPresent()) {
                CommonGunIndex gunIndex = commonGunIndex.orElseThrow();
                GunData gunData = gunIndex.getGunData();
                int extraBullet = gunData.getBolt() != Bolt.OPEN_BOLT ? 1 : 0;
                int ammoAmount = gunData.getAmmoAmount() + extraBullet;
                return original + ammoAmount * (TACZEOConfig.get().capacityMultiplier * level);
            }
        }
        return super.apply(original, level, enchantedItem);
    }
}
