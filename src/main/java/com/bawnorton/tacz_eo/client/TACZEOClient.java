package com.bawnorton.tacz_eo.client;

import com.bawnorton.tacz_eo.client.config.TACZEOConfigScreenHandler;

//? if neoforge {
import net.neoforged.api.distmarker.Dist;
import com.bawnorton.tacz_eo.TACZEO;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = TACZEO.MOD_ID, dist = Dist.CLIENT)
public class TACZEOClient {
    public TACZEOClient(FMLModContainer container) {
        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (ignored, screen) -> TACZEOConfigScreenHandler.generateConfigScreen(screen)
        );
    }
}
//?} elif forge {
/*import net.neoforged.client.ConfigScreenHandler;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

public class TACZEOClient {
    public static void init(FMLJavaModLoadingContext context) {
        context.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, screen) -> TACZEOConfigScreenHandler.generateConfigScreen(screen)
                )
        );
    }
}
*///?}