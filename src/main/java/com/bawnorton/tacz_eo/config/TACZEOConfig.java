package com.bawnorton.tacz_eo.config;

import com.bawnorton.tacz_eo.TACZEO;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import com.tacz.guns.api.TimelessAPI;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TACZEOConfig {
	public static ConfigClassHandler<TACZEOConfig> HANDLER = ConfigClassHandler.createBuilder(TACZEOConfig.class)
			.id(TACZEO.rl("config"))
			.serializer(config -> GsonConfigSerializerBuilder.create(config)
					.setPath(FMLPaths.CONFIGDIR.get().resolve("tacz_enchantments_overhaul.json5"))
					.setJson5(true)
					.appendGsonBuilder(gsonBuilder ->
							gsonBuilder.registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
					)
					.build())
			.build();

	private static boolean initialized = false;

	@SerialEntry(
			value = "bullseye_headshot_bonus",
			comment = "The headshot damage bonus per level for the Bullseye enchantment.\nDefault: 50%"
	)
	public float bullseyeHeadshotBonus = 0.5f;

	@SerialEntry(
			value = "bullseye_max_level",
			comment = "The maximum level for the Bullseye enchantment.\nDefault: 1"
	)
	public int bullseyeMaxLevel = 1;
	
	@SerialEntry(
			value = "bullseye_base_enchantability",
			comment = "The minimum enchantability required to obtain the first level of the enchantment.\nDefault: 35"
	)
	public int bullseyeBaseEnchantability = 35;
	
	@SerialEntry(
			value = "bullseye_level_enchantability",
			comment = "The additional enchantability required for each subsequent level of the enchantment.\nDefault: 15"
	)
	public int bullseyeLevelEnchantability = 15;
	
	@SerialEntry(
			value = "bullseye_enchantability_span",
			comment = "When added to the base and level enchantability, this value determines the maximum enchantability where the enchantment can be obtained.\nDefault: 50"
	)
	public int bullseyeEnchantabilitySpan = 50;

	@SerialEntry(
			value = "deep_impact_armor_piercing_bonus",
			comment = "The armour piercing bonus per level for the Deep Impact enchantment.\nDefault: 5%"
	)
	public float deepImpactArmourPiercingBonus = 0.05f;

	@SerialEntry(
			value = "deep_impact_max_level",
			comment = "The maximum level for the Deep Impact enchantment.\nDefault: 3"
	)
	public int deepImpactMaxLevel = 3;
	
	@SerialEntry(
			value = "deep_impact_base_enchantability",
			comment = "The minimum enchantability required to obtain the first level of the enchantment.\nDefault: 15"
	)
	public int deepImpactBaseEnchantability = 15;
	
	@SerialEntry(
			value = "deep_impact_level_enchantability",
			comment = "The additional enchantability required for each subsequent level of the enchantment.\nDefault: 12"
	)
	public int deepImpactLevelEnchantability = 12;
	
	@SerialEntry(
			value = "deep_impact_enchantability_span",
			comment = "When added to the base and level enchantability, this value determines the maximum enchantability where the enchantment can be obtained.\nDefault: 25"
	)
	public int deepImpactEnchantabilitySpan = 25;

	@SerialEntry(
			value = "firepower_damage_multiplier",
			comment = "The damage multiplier for the Firepower enchantment. This is multiplied by the enchantment level and added to the base damage of the bullets used.\nDefault: 10%"
	)
	public float firepowerDamageMultiplier = 0.1f;

	@SerialEntry(
			value = "firepower_max_level",
			comment = "The maximum level for the Firepower enchantment.\nDefault: 3"
	)
	public int firepowerMaxLevel = 3;
	
	@SerialEntry(
			value = "firepower_base_enchantability",
			comment = "The minimum enchantability required to obtain the first level of the enchantment.\nDefault: 1"
	)
	public int firepowerBaseEnchantability = 1;
	
	@SerialEntry(
			value = "firepower_level_enchantability",
			comment = "The additional enchantability required for each subsequent level of the enchantment.\nDefault: 11"
	)
	public int firepowerLevelEnchantability = 11;
	
	@SerialEntry(
			value = "firepower_enchantability_span",
			comment = "When added to the base and level enchantability, this value determines the maximum enchantability where the enchantment can be obtained.\nDefault: 25"
	)
	public int firepowerEnchantabilitySpan = 25;

	@SerialEntry(
			value = "punch_through_count",
			comment = "The number of additional entities that can be hit per level for the Punch Through enchantment.\nDefault: 1"
	)
	public int punchThroughCount = 1;

	@SerialEntry(
			value = "punch_through_max_level",
			comment = "The maximum level for the Punch Through enchantment.\nDefault: 2"
	)
	public int punchThroughMaxLevel = 2;
	
	@SerialEntry(
			value = "punch_through_base_enchantability",
			comment = "The minimum enchantability required to obtain the first level of the enchantment.\nDefault: 20"
	)
	public int punchThroughBaseEnchantability = 20;
	
	@SerialEntry(
			value = "punch_through_level_enchantability",
			comment = "The additional enchantability required for each subsequent level of the enchantment.\nDefault: 14"
	)
	public int punchThroughLevelEnchantability = 14;
	
	@SerialEntry(
			value = "punch_through_enchantability_span",
			comment = "When added to the base and level enchantability, this value determines the maximum enchantability where the enchantment can be obtained.\nDefault: 40"
	)
	public int punchThroughEnchantabilitySpan = 40;
	
	@SerialEntry(
			value = "precision_accuracy_bonus",
			comment = "The accuracy bonus per level for the Precision enchantment.\nDefault: 5%"
	)
	public float precisionAccuracyBonus = 0.05f;

	@SerialEntry(
			value = "precision_max_level",
			comment = "The maximum level for the Precision enchantment.\nDefault: 3"
	)
	public int precisionMaxLevel = 3;
	
	@SerialEntry(
			value = "precision_base_enchantability",
			comment = "The minimum enchantability required to obtain the first level of the enchantment.\nDefault: 3"
	)
	public int precisionBaseEnchantability = 3;
	
	@SerialEntry(
			value = "precision_level_enchantability",
			comment = "The additional enchantability required for each subsequent level of the enchantment.\nDefault: 12"
	)
	public int precisionLevelEnchantability = 12;
	
	@SerialEntry(
			value = "precision_enchantability_span",
			comment = "When added to the base and level enchantability, this value determines the maximum enchantability where the enchantment can be obtained.\nDefault: 25"
	)
	public int precisionEnchantabilitySpan = 25;

	@SerialEntry(
			value = "enchantments_map",
			comment = "A map of enchantment ids to a list of gun ids that the enchantment can be applied to."
	)
	public Map<ResourceLocation, List<ResourceLocation>> enchantmentsMap = Util.make(new LinkedHashMap<>(), map -> {
		List<ResourceLocation> guns = TimelessAPI.getAllCommonGunIndex().stream().map(Map.Entry::getKey).toList();
		map.put(ForgeRegistries.ENCHANTMENTS.getKey(Enchantments.MOB_LOOTING), guns);
		map.put(ForgeRegistries.ENCHANTMENTS.getKey(TACZEOEnchantments.BULLSEYE.get()), guns);
		map.put(ForgeRegistries.ENCHANTMENTS.getKey(TACZEOEnchantments.DEEP_IMPACT.get()), guns);
		map.put(ForgeRegistries.ENCHANTMENTS.getKey(TACZEOEnchantments.FIREPOWER.get()), guns);
		map.put(ForgeRegistries.ENCHANTMENTS.getKey(TACZEOEnchantments.PRECISION.get()), guns);
		map.put(ForgeRegistries.ENCHANTMENTS.getKey(TACZEOEnchantments.PUNCH_THROUGH.get()), guns);
	});

	public static void init() {
		if (initialized) return;

		HANDLER.load();
		initialized = true;
	}

	public static TACZEOConfig get() {
		init();
		return HANDLER.instance();
	}

	public static void save() {
		HANDLER.save();
	}
}
