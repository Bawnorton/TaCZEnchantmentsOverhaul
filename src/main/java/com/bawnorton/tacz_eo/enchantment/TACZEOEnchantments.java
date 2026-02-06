package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.TACZEO;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TACZEOEnchantments {
	/**
	 * @see TACZEO#canApplyEnchantmentToGun(ResourceLocation, ResourceLocation)
	 */
	public static final EnchantmentCategory GUN_CATEGORY = EnchantmentCategory.create("GUN", item -> false);

	public static final DeferredRegister<Enchantment> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, TACZEO.MOD_ID);

	public static final RegistryObject<TACZEOEnchantment> FIREPOWER = register("firepower", () -> new FirepowerEnchantment(Enchantment.Rarity.COMMON, GUN_CATEGORY, EquipmentSlot.MAINHAND));
	public static final RegistryObject<TACZEOEnchantment> DEEP_IMPACT = register("deep_impact", () -> new DeepImpactEnchantment(Enchantment.Rarity.UNCOMMON, GUN_CATEGORY, EquipmentSlot.MAINHAND));
	public static final RegistryObject<TACZEOEnchantment> BULLSEYE = register("bullseye", () -> new BullseyeEnchantment(Enchantment.Rarity.RARE, GUN_CATEGORY, EquipmentSlot.MAINHAND));
	public static final RegistryObject<TACZEOEnchantment> PUNCH_THROUGH = register("punch_through", () -> new PunchThroughEnchantment(Enchantment.Rarity.RARE, GUN_CATEGORY, EquipmentSlot.MAINHAND));
	public static final RegistryObject<TACZEOEnchantment> PRECISION = register("precision", () -> new PrecisionEnchantment(Enchantment.Rarity.COMMON, GUN_CATEGORY, EquipmentSlot.MAINHAND));

	private static <T extends TACZEOEnchantment> RegistryObject<T> register(String name, Supplier<T> enchantment) {
		return DEFERRED_REGISTER.register(name, enchantment);
	}

	public static void init() {
		// no-op
	}
}
