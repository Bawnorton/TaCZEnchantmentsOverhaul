package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class PunchThroughEnchantment extends TACZEOEnchantment {
	public PunchThroughEnchantment(Rarity rarity) {
		super(rarity,
				() -> TACZEOConfig.get().punchThroughBaseEnchantability,
				() -> TACZEOConfig.get().punchThroughLevelEnchantability,
				() -> TACZEOConfig.get().punchThroughMaxLevel,
				() -> TACZEOConfig.get().punchThroughEnchantabilitySpan
		);
	}

	@Override
	public float apply(float original, int level, @Nullable ItemStack enchantedItem) {
		return original + TACZEOConfig.get().punchThroughCount * level;
	}
}
