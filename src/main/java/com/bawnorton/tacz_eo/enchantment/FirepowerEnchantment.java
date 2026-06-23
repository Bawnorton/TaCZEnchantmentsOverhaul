package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.resource.index.CommonGunIndex;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;

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
