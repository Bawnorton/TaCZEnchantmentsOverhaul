package com.bawnorton.tacz_eo.client.config;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import com.tacz.guns.api.TimelessAPI;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.DropdownStringControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TACZEOConfigScreenHandler {

	public static Screen generateConfigScreen(Screen screen) {
		return YetAnotherConfigLib.createBuilder()
				.title(Component.translatable("config.tacz_eo.title"))
				.category(ConfigCategory.createBuilder()
						.name(Component.translatable("config.tacz_eo.category.enchantment_balancing"))
						.tooltip(Component.translatable("config.tacz_eo.category.enchantment_balancing.tooltip"))
						.group(OptionGroup.createBuilder()
								.name(Component.translatable("config.tacz_eo.category.enchantment_balancing.group.bullseye"))
								.description(OptionDescription.of(Component.translatable("config.tacz_eo.category.enchantment_balancing.group.bullseye.description")))
								.option(Option.<Float>createBuilder()
										.name(Component.translatable("config.tacz_eo.option.bullseye_headshot_bonus"))
										.binding(0.5f, () -> TACZEOConfig.get().bullseyeHeadshotBonus, val -> TACZEOConfig.get().bullseyeHeadshotBonus = val)
										.controller(option -> FloatSliderControllerBuilder.create(option)
												.range(0f, 2f)
												.step(0.01f))
										.build())
								.options(buildEnchantmentSettings(
										1, () -> TACZEOConfig.get().bullseyeMaxLevel, val -> TACZEOConfig.get().bullseyeMaxLevel = val,
										35, () -> TACZEOConfig.get().bullseyeBaseEnchantability, val -> TACZEOConfig.get().bullseyeBaseEnchantability = val,
										15, () -> TACZEOConfig.get().bullseyeLevelEnchantability, val -> TACZEOConfig.get().bullseyeLevelEnchantability = val,
										50, () -> TACZEOConfig.get().bullseyeEnchantabilitySpan, val -> TACZEOConfig.get().bullseyeEnchantabilitySpan = val
								))
								.collapsed(true)
								.build())
						.group(OptionGroup.createBuilder()
								.name(Component.translatable("config.tacz_eo.category.enchantment_balancing.group.deep_impact"))
								.description(OptionDescription.of(Component.translatable("config.tacz_eo.category.enchantment_balancing.group.deep_impact.description")))
								.option(Option.<Float>createBuilder()
										.name(Component.translatable("config.tacz_eo.option.deep_impact_armor_piercing_bonus"))
										.binding(0.05f, () -> TACZEOConfig.get().deepImpactArmourPiercingBonus, val -> TACZEOConfig.get().deepImpactArmourPiercingBonus = val)
										.controller(option -> FloatSliderControllerBuilder.create(option)
												.range(0f, 1f)
												.step(0.01f))
										.build())
								.options(buildEnchantmentSettings(
										3, () -> TACZEOConfig.get().deepImpactMaxLevel, val -> TACZEOConfig.get().deepImpactMaxLevel = val,
										15, () -> TACZEOConfig.get().deepImpactBaseEnchantability, val -> TACZEOConfig.get().deepImpactBaseEnchantability = val,
										12, () -> TACZEOConfig.get().deepImpactLevelEnchantability, val -> TACZEOConfig.get().deepImpactLevelEnchantability = val,
										25, () -> TACZEOConfig.get().deepImpactEnchantabilitySpan, val -> TACZEOConfig.get().deepImpactEnchantabilitySpan = val
								))
								.collapsed(true)
								.build())
						.group(OptionGroup.createBuilder()
								.name(Component.translatable("config.tacz_eo.category.enchantment_balancing.group.firepower"))
								.description(OptionDescription.of(Component.translatable("config.tacz_eo.category.enchantment_balancing.group.firepower.description")))
								.option(Option.<Float>createBuilder()
										.name(Component.translatable("config.tacz_eo.option.firepower_damage_multiplier"))
										.binding(0.1f, () -> TACZEOConfig.get().firepowerDamageMultiplier, val -> TACZEOConfig.get().firepowerDamageMultiplier = val)
										.controller(option -> FloatSliderControllerBuilder.create(option)
												.range(0f, 2f)
												.step(0.01f))
										.build())
								.options(buildEnchantmentSettings(
										3, () -> TACZEOConfig.get().firepowerMaxLevel, val -> TACZEOConfig.get().firepowerMaxLevel = val,
										1, () -> TACZEOConfig.get().firepowerBaseEnchantability, val -> TACZEOConfig.get().firepowerBaseEnchantability = val,
										11, () -> TACZEOConfig.get().firepowerLevelEnchantability, val -> TACZEOConfig.get().firepowerLevelEnchantability = val,
										25, () -> TACZEOConfig.get().firepowerEnchantabilitySpan, val -> TACZEOConfig.get().firepowerEnchantabilitySpan = val
								))
								.collapsed(true)
								.build())
						.group(OptionGroup.createBuilder()
								.name(Component.translatable("config.tacz_eo.category.enchantment_balancing.group.precision"))
								.description(OptionDescription.of(Component.translatable("config.tacz_eo.category.enchantment_balancing.group.precision.description")))
								.option(Option.<Float>createBuilder()
										.name(Component.translatable("config.tacz_eo.option.precision_accuracy_bonus"))
										.binding(0.05f, () -> TACZEOConfig.get().precisionAccuracyBonus, val -> TACZEOConfig.get().precisionAccuracyBonus = val)
										.controller(option -> FloatSliderControllerBuilder.create(option)
												.range(0f, 1f)
												.step(0.01f))
										.build())
								.options(buildEnchantmentSettings(
										3, () -> TACZEOConfig.get().precisionMaxLevel, val -> TACZEOConfig.get().precisionMaxLevel = val,
										3, () -> TACZEOConfig.get().precisionBaseEnchantability, val -> TACZEOConfig.get().precisionBaseEnchantability = val,
										12, () -> TACZEOConfig.get().precisionLevelEnchantability, val -> TACZEOConfig.get().precisionLevelEnchantability = val,
										25, () -> TACZEOConfig.get().precisionEnchantabilitySpan, val -> TACZEOConfig.get().precisionEnchantabilitySpan = val
								))
								.collapsed(true)
								.build())
						.group(OptionGroup.createBuilder()
								.name(Component.translatable("config.tacz_eo.category.enchantment_balancing.group.punch_through"))
								.description(OptionDescription.of(Component.translatable("config.tacz_eo.category.enchantment_balancing.group.punch_through.description")))
								.option(Option.<Integer>createBuilder()
										.name(Component.translatable("config.tacz_eo.option.punch_through_count"))
										.binding(1, () -> TACZEOConfig.get().punchThroughCount, val -> TACZEOConfig.get().punchThroughCount = val)
										.controller(option -> IntegerSliderControllerBuilder.create(option)
												.range(1, 10)
												.step(1))
										.build())
								.options(buildEnchantmentSettings(
										2, () -> TACZEOConfig.get().punchThroughMaxLevel, val -> TACZEOConfig.get().punchThroughMaxLevel = val,
										20, () -> TACZEOConfig.get().punchThroughBaseEnchantability, val -> TACZEOConfig.get().punchThroughBaseEnchantability = val,
										14, () -> TACZEOConfig.get().punchThroughLevelEnchantability, val -> TACZEOConfig.get().punchThroughLevelEnchantability = val,
										40, () -> TACZEOConfig.get().punchThroughEnchantabilitySpan, val -> TACZEOConfig.get().punchThroughEnchantabilitySpan = val
								))
								.collapsed(true)
								.build())
						.build())
				.category(buildEnchantmentWhitelistCategory())
				.save(TACZEOConfig::save)
				.build()
				.generateScreen(screen);
	}

	private static Collection<Option<Integer>> buildEnchantmentSettings(
			int defaultMaxLevel,
			Supplier<Integer> maxLevelGetter,
			Consumer<Integer> maxLevelSetter,
			int defaultBaseEnchantability,
			Supplier<Integer> baseEnchantabilityGetter,
			Consumer<Integer> baseEnchantabilitySetter,
			int defaultLevelEnchantability,
			Supplier<Integer> levelEnchantabilityGetter,
			Consumer<Integer> levelEnchantabilitySetter,
			int defaultEnchantabilitySpan,
			Supplier<Integer> enchantabilitySpanGetter,
			Consumer<Integer> enchantabilitySpanSetter
	) {
		return List.of(
				Option.<Integer>createBuilder()
						.name(Component.translatable("config.tacz_eo.option.max_level"))
						.binding(defaultMaxLevel, maxLevelGetter, maxLevelSetter)
						.controller(option -> IntegerFieldControllerBuilder.create(option)
								.range(1, 255)
								.formatValue(value -> Component.translatableWithFallback("enchantment.level.%s".formatted(value), "%s".formatted(value))))
						.build(),
				Option.<Integer>createBuilder()
						.name(Component.translatable("config.tacz_eo.option.base_enchantability"))
						.description(OptionDescription.of(Component.translatable("config.tacz_eo.option.base_enchantability.description")))
						.binding(defaultBaseEnchantability, baseEnchantabilityGetter, baseEnchantabilitySetter)
						.controller(option -> IntegerFieldControllerBuilder.create(option)
								.min(0))
						.build(),
				Option.<Integer>createBuilder()
						.name(Component.translatable("config.tacz_eo.option.level_enchantability"))
						.description(OptionDescription.of(Component.translatable("config.tacz_eo.option.level_enchantability.description")))
						.binding(defaultLevelEnchantability, levelEnchantabilityGetter, levelEnchantabilitySetter)
						.controller(option -> IntegerFieldControllerBuilder.create(option)
								.min(0))
						.build(),
				Option.<Integer>createBuilder()
						.name(Component.translatable("config.tacz_eo.option.enchantability_span"))
						.description(OptionDescription.of(Component.translatable("config.tacz_eo.option.enchantability_span.description")))
						.binding(defaultEnchantabilitySpan, enchantabilitySpanGetter, enchantabilitySpanSetter)
						.controller(option -> IntegerFieldControllerBuilder.create(option)
								.min(0))
						.build()
		);
	}

	private static @NotNull ConfigCategory buildEnchantmentWhitelistCategory() {
		ConfigCategory.Builder builder = ConfigCategory.createBuilder()
				.name(Component.translatable("config.tacz_eo.category.enchantment_whitelisting"))
				.tooltip(Component.translatable("config.tacz_eo.category.enchantment_whitelisting.tooltip"))
				.option(LabelOption.create(Component.translatable("config.tacz_eo.category.enchantment_whitelisting.info")));

		Set<ResourceLocation> enchantments = TACZEOConfig.get().enchantmentsMap.keySet();
		for (ResourceLocation enchantmentId : enchantments) {
			builder = builder.group(ListOption.<String>createBuilder()
					.name(Component.translatable("config.tacz_eo.option.enchantment_whitelist", enchantmentId.toString()))
					.description(OptionDescription.of(Component.translatable("config.tacz_eo.option.enchantment_whitelist.description", enchantmentId.toString())))
					.binding(TimelessAPI.getAllCommonGunIndex()
									.stream()
									.map(Map.Entry::getKey)
									.map(ResourceLocation::toString)
									.toList(),
							() -> TACZEOConfig.get().enchantmentsMap.get(enchantmentId)
									.stream()
									.map(ResourceLocation::toString)
									.toList(),
							val -> TACZEOConfig.get().enchantmentsMap.put(enchantmentId, val.stream()
									.map(ResourceLocation::parse)
									.toList()))
					.controller(option -> DropdownStringControllerBuilder.create(option)
							.values(TimelessAPI.getAllCommonGunIndex()
									.stream()
									.map(Map.Entry::getKey)
									.map(ResourceLocation::toString)
									.toList()))
					.initial("")
					.collapsed(true)
					.build());
		}
		return builder.build();
	}
}
