package com.bawnorton.tacz_eo.mixin.accessor;

import com.google.gson.JsonElement;
import com.tacz.guns.resource.manager.JsonDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(JsonDataManager.class)
public interface JsonDataManagerAccessor<T> {
	@Invoker("prepare")
	Map<ResourceLocation, JsonElement> tazc_eo$prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler);

	@Invoker("parseJson")
	T tacz_eo$parseJson(JsonElement element);
}
