package com.bawnorton.tacz_eo.event.v1_21_1;

import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import com.tacz.guns.api.event.common.GunDamageSourcePart;
import com.bawnorton.tacz_eo.enchantment.v1_21_1.HeadshotCondition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class TACZEventHandler {
    public static void init() {
        NeoForge.EVENT_BUS.register(TACZEventHandler.class);
    }

    @SubscribeEvent
    public static void preHurt(EntityHurtByGunEvent.Pre event) {
        Entity bullet = event.getBullet();
        if (bullet.level() instanceof ServerLevel level && event.getAttacker() != null && event.getHurtEntity() != null) {
            HeadshotCondition.setCurrentShot(event.isHeadShot());
            try {
                event.setBaseAmount(EnchantmentHelper.modifyDamage(
                        level,
                        event.getAttacker().getWeaponItem(),
                        event.getHurtEntity(),
                        event.getDamageSource(GunDamageSourcePart.NON_ARMOR_PIERCING),
                        event.getBaseAmount()
                ));
            } finally {
                HeadshotCondition.clearCurrentShot();
            }
        }
    }
}

