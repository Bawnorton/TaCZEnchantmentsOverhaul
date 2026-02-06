package com.bawnorton.tacz_eo.mixin.tacz;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import com.bawnorton.tacz_eo.mixin.accessor.CommonDataManagerAccessor;
import com.bawnorton.tacz_eo.mixin.accessor.JsonDataManagerAccessor;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.manager.CommonDataManager;
import com.tacz.guns.resource.network.DataType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.Marker;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(CommonAssetsManager.class)
abstract class CommonAssetsManagerMixin {
	@Definition(id = "register", method = "Lcom/tacz/guns/resource/CommonAssetsManager;register(Lcom/tacz/guns/resource/manager/INetworkCacheReloadListener;)Lcom/tacz/guns/resource/manager/INetworkCacheReloadListener;")
	@Definition(id = "GUN_INDEX", field = "Lcom/tacz/guns/resource/network/DataType;GUN_INDEX:Lcom/tacz/guns/resource/network/DataType;")
	@Expression("?.register(@(new ?(GUN_INDEX, ?, ?, ?, ?)))")
	@ModifyExpressionValue(
			method = "reloadAndRegister",
			at = @At("MIXINEXTRAS:EXPRESSION"),
			remap = false
	)
	private <T> CommonDataManager<T> attachConfigLoader(CommonDataManager<T> original) {
		return new CommonDataManager<>(original.getType(), original.getDataClass(), original.getGson(), "index/guns", original.getMarker().getName()) {
			@Override
			protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
				((CommonDataManagerAccessor) original).tacz_eo$apply(pObject, pResourceManager, pProfiler);
				TACZEOConfig.init();
			}

			@Override
			public void clear() {
				original.clear();
			}

			@Override
			public Map<ResourceLocation, String> getNetworkCache() {
				return original.getNetworkCache();
			}

			@Override
			public DataType getType() {
				return original.getType();
			}

			@NotNull
			@Override
			protected Map<ResourceLocation, JsonElement> prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
				return ((JsonDataManagerAccessor<T>) original).tazc_eo$prepare(pResourceManager, pProfiler);
			}

			@Override
			protected T parseJson(JsonElement element) {
				return ((JsonDataManagerAccessor<T>) original).tacz_eo$parseJson(element);
			}

			@Override
			public Class<T> getDataClass() {
				return original.getDataClass();
			}

			@Override
			public Marker getMarker() {
				return original.getMarker();
			}

			@Override
			public Gson getGson() {
				return original.getGson();
			}

			@Override
			public T getData(ResourceLocation id) {
				return original.getData(id);
			}

			@Override
			public Map<ResourceLocation, T> getAllData() {
				return original.getAllData();
			}

			@Override
			public String getName() {
				return original.getName();
			}
		};
	}
}

