package com.bawnorton.tacz_eo.client;

import com.bawnorton.tacz_eo.TACZEO;
import com.bawnorton.tacz_eo.client.config.TACZEOConfigScreenHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
		modid = TACZEO.MOD_ID,
		bus = Mod.EventBusSubscriber.Bus.MOD,
		value = Dist.CLIENT
)
public class TACZEOClient {
	@SuppressWarnings("removal") // Removed in 1.21.1, we are on 1.20.1
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> TACZEOConfigScreenHandler.generateConfigScreen(screen)));
	}
}