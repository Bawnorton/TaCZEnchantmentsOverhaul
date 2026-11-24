package com.bawnorton.tacz_eo.platform;

import com.bawnorton.tacz_eo.TACZEO;
import net.neoforged.fml.common.Mod;

//? if neoforge {
import com.bawnorton.tacz_eo.event.v1_21_1.TACZEventHandler;
import com.bawnorton.tacz_eo.enchantment.v1_21_1.TACZEOEffectComponents;
import com.bawnorton.tacz_eo.enchantment.v1_21_1.HeadshotCondition;
import net.neoforged.bus.api.IEventBus;

@Mod(TACZEO.MOD_ID)
public class TACZEOWrapper {
    public TACZEOWrapper(IEventBus bus) {
        TACZEOEffectComponents.init(bus);
        HeadshotCondition.init(bus);
        TACZEO.init();
        TACZEventHandler.init();
    }
}
//?} elif forge {
/*import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.api.distmarker.Dist;
import com.bawnorton.tacz_eo.client.TACZEOClient;

@Mod(TACZEO.MOD_ID)
public class TACZEOWrapper {
    public TACZEOWrapper(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        TACZEOEnchantments.init(bus);
        TACZEO.init();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            TACZEOClient.init(context);
        }
    }
}
*///?}
