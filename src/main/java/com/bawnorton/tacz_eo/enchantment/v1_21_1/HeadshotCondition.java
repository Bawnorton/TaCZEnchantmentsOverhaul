package com.bawnorton.tacz_eo.enchantment.v1_21_1;

import com.bawnorton.tacz_eo.TACZEO;
import com.mojang.serialization.MapCodec;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class HeadshotCondition implements LootItemCondition {
    private static final DeferredRegister<LootItemConditionType> REGISTER = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, TACZEO.MOD_ID);
    private static final DeferredHolder<LootItemConditionType, LootItemConditionType> TYPE = REGISTER.register("headshot", () -> new LootItemConditionType(MapCodec.unit(new HeadshotCondition())));
    private static final ThreadLocal<Boolean> CURRENT_SHOT = ThreadLocal.withInitial(() -> false);

    private HeadshotCondition() {
    }

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }

    public static void setCurrentShot(boolean headshot) {
        CURRENT_SHOT.set(headshot);
    }

    public static void clearCurrentShot() {
        CURRENT_SHOT.remove();
    }

    @Override
    public LootItemConditionType getType() {
        return TYPE.get();
    }

    @Override
    public boolean test(LootContext context) {
        return CURRENT_SHOT.get() && context.getParamOrNull(LootContextParams.DIRECT_ATTACKING_ENTITY) instanceof EntityKineticBullet;
    }

    public static LootItemCondition.Builder headshot() {
        return HeadshotCondition::new;
    }
}

