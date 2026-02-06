package com.bawnorton.tacz_eo.mixin.accessor;

import com.google.gson.JsonElement;
import com.tacz.guns.resource.manager.CommonDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(CommonDataManager.class)
public interface CommonDataManagerAccessor {
	@Invoker("apply")
	void tacz_eo$apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler);
}
