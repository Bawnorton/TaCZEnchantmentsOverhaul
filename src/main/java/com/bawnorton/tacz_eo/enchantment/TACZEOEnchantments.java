package com.bawnorton.tacz_eo.enchantment;

import com.bawnorton.tacz_eo.TACZEO;
import net.minecraft.world.item.enchantment.Enchantment;

//? if >=1.21.1 {
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.MultiplyValue;
import net.minecraft.world.item.enchantment.effects.RemoveBinomial;
import com.bawnorton.tacz_eo.enchantment.v1_21_1.HeadshotCondition;
import com.bawnorton.tacz_eo.enchantment.v1_21_1.TACZEOEffectComponents;
import com.tacz.guns.init.ModItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;

public class TACZEOEnchantments {
    public static final ResourceKey<Enchantment> FIREPOWER = key("firepower");
    public static final ResourceKey<Enchantment> DEEP_IMPACT = key("deep_impact");
    public static final ResourceKey<Enchantment> BULLSEYE = key("bullseye");
    public static final ResourceKey<Enchantment> PUNCH_THROUGH = key("punch_through");
    public static final ResourceKey<Enchantment> PRECISION = key("precision");
    public static final ResourceKey<Enchantment> CAPACITY = key("capacity");
    public static final ResourceKey<Enchantment> BULLET_RETENTION = key("bullet_retention");

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderSet<Item> guns = HolderSet.direct(items.getOrThrow(ModItems.MODERN_KINETIC_GUN.getKey()));
        register(context, FIREPOWER, builder(guns, 10, 3, 1, 11, 25, 11).withEffect(
                EnchantmentEffectComponents.DAMAGE,
                new MultiplyValue(LevelBasedValue.perLevel(1.1f, 0.1f))
        ));
        register(context, DEEP_IMPACT, builder(guns, 5, 3, 15, 12, 25, 12).withEffect(
                TACZEOEffectComponents.ARMOR_IGNORE.get(),
                new AddValue(LevelBasedValue.perLevel(0.05f))
        ));
        register(context, BULLSEYE, builder(guns, 2, 1, 35, 15, 50, 15).withEffect(
                EnchantmentEffectComponents.DAMAGE,
                new MultiplyValue(LevelBasedValue.perLevel(1.5f, 0.5f)),
                HeadshotCondition.headshot()
        ));
        register(context, PUNCH_THROUGH, builder(guns, 2, 2, 20, 14, 40, 14).withEffect(
                EnchantmentEffectComponents.PROJECTILE_PIERCING,
                new AddValue(LevelBasedValue.perLevel(1.0f))
        ));
        register(context, PRECISION, builder(guns, 10, 3, 3, 12, 25, 12).withEffect(
                EnchantmentEffectComponents.PROJECTILE_SPREAD,
                new MultiplyValue(LevelBasedValue.perLevel(0.95f, -0.05f))
        ));
        register(context, CAPACITY, builder(guns, 10, 4, 2, 10, 15, 10).withEffect(
                TACZEOEffectComponents.MAGAZINE_CAPACITY.get(),
                new MultiplyValue(LevelBasedValue.perLevel(1.5f, 0.5f))
        ));
        register(context, BULLET_RETENTION, builder(guns, 2, 2, 21, 17, 45, 17).withEffect(
                EnchantmentEffectComponents.AMMO_USE,
                new RemoveBinomial(LevelBasedValue.perLevel(0.1f))
        ));
    }

    private static Enchantment.Builder builder(HolderSet<Item> guns, int weight, int maxLevel, int minBase, int minStep, int maxBase, int maxStep) {
        return Enchantment.enchantment(Enchantment.definition(
                guns, weight, maxLevel, Enchantment.dynamicCost(minBase, minStep),
                Enchantment.dynamicCost(maxBase, maxStep), 1, EquipmentSlotGroup.HAND
        ));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, TACZEO.rl(name));
    }
}
//?} else {
/*import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.neoforged.registries.DeferredRegister;
import net.neoforged.registries.ForgeRegistries;
import net.neoforged.registries.RegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import com.bawnorton.tacz_eo.enchantment.v1_20_1.*;

import java.util.function.Function;

public class TACZEOEnchantments {
    /^*
     * @see TACZEO#canApplyEnchantmentToGun(ResourceLocation, ResourceLocation)
     ^/
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
*///?}

