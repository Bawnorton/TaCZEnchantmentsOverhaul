package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.TACZEO;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class TACZEOEnchantments {
	/**
	 * @see TACZEO#canApplyEnchantmentToGun(ResourceLocation, ResourceLocation)
	 */
	public static final EnchantmentCategory GUN_CATEGORY = EnchantmentCategory.create("GUN", item -> false);

	private static final DeferredRegister<Enchantment> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, TACZEO.MOD_ID);

	public static final RegistryObject<TACZEOEnchantment> FIREPOWER = register("firepower", FirepowerEnchantment::new, Enchantment.Rarity.COMMON);
	public static final RegistryObject<TACZEOEnchantment> DEEP_IMPACT = register("deep_impact", DeepImpactEnchantment::new, Enchantment.Rarity.UNCOMMON);
	public static final RegistryObject<TACZEOEnchantment> BULLSEYE = register("bullseye", BullseyeEnchantment::new, Enchantment.Rarity.RARE);
	public static final RegistryObject<TACZEOEnchantment> PUNCH_THROUGH = register("punch_through", PunchThroughEnchantment::new, Enchantment.Rarity.RARE);
	public static final RegistryObject<TACZEOEnchantment> PRECISION = register("precision", PrecisionEnchantment::new, Enchantment.Rarity.COMMON);
	public static final RegistryObject<TACZEOEnchantment> CAPACITY = register("capacity", CapacityEnchantment::new, Enchantment.Rarity.COMMON);
	public static final RegistryObject<TACZEOEnchantment> BULLET_RETENTION = register("bullet_retention", BulletRetentionEnchantment::new, Enchantment.Rarity.RARE);

	private static <T extends TACZEOEnchantment> RegistryObject<T> register(String name, Function<Enchantment.Rarity, T> ctor, Enchantment.Rarity rarity) {
		return DEFERRED_REGISTER.register(name, () -> ctor.apply(rarity));
	}

	public static void init(IEventBus bus) {
		DEFERRED_REGISTER.register(bus);
	}
}
