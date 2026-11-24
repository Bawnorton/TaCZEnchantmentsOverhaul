package com.bawnorton.tacz_eo.enchantment.v1_21_1;

import com.bawnorton.tacz_eo.TACZEO;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class TACZEOEffectComponents {
    private static final DeferredRegister<DataComponentType<?>> REGISTER = DeferredRegister.create(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, TACZEO.MOD_ID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> MAGAZINE_CAPACITY = register("magazine_capacity");
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> ARMOR_IGNORE = register("armor_ignore");

    private TACZEOEffectComponents() {
    }

    private static DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> register(String name) {
        return REGISTER.register(name, () -> DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder()
                        .persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf())
                        .build());
    }

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}

