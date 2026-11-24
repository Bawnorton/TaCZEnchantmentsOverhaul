package com.bawnorton.tacz_eo.enchantment.v1_21_1;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;
import java.util.function.Predicate;

public final class EnchantmentValueEffects {
    public static float apply(
            ItemStack stack,
            DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> componentType,
            float originalValue,
            RandomSource random,
            Predicate<ConditionalEffect<EnchantmentValueEffect>> expectedToApply
    ) {
        MutableFloat value = new MutableFloat(originalValue);

        EnchantmentHelper.runIterationOnItem(stack, (enchantment, level) -> enchantment.value()
                .getEffects(componentType)
                .stream()
                .filter(expectedToApply)
                .forEach(effect -> value.setValue(
                        effect.effect().process(level, random, value.floatValue())
                )));

        return value.floatValue();
    }
}
