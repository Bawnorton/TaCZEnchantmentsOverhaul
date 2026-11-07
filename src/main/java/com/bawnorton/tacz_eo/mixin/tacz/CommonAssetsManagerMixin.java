package com.bawnorton.tacz_eo.mixin.tacz;

import com.bawnorton.tacz_eo.config.TACZEOConfig;
import com.tacz.guns.resource.CommonAssetsManager;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(value = CommonAssetsManager.class, remap = false)
abstract class CommonAssetsManagerMixin {
	@Inject(
			method = "reloadAndRegister",
			at = @At("HEAD")
	)
	private void attachConfigLoader(Consumer<PreparableReloadListener> register, CallbackInfo ci) {
		TACZEOConfig.init();
	}
}

