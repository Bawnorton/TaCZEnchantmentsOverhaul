package com.bawnorton.tacz_eo.data.v1_21_1;

import com.bawnorton.tacz_eo.TACZEO;
import com.bawnorton.tacz_eo.enchantment.TACZEOEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = TACZEO.MOD_ID)
public class TACZEODataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        DataProvider.Factory<DatapackBuiltinEntriesProvider> factory = output -> new DatapackBuiltinEntriesProvider(
                output,
                lookupProvider,
                new RegistrySetBuilder().add(Registries.ENCHANTMENT, TACZEOEnchantments::bootstrap),
                Set.of(TACZEO.MOD_ID)
        );

        generator.addProvider(event.includeServer(), factory);
    }
}
