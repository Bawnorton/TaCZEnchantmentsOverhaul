package com.bawnorton.tacz_eo.mixin;

import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class TACZEOMixinConfigPlugin implements IMixinConfigPlugin {
	@Override
	public void onLoad(String mixinPackage) {
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	private boolean isModLoaded(String modId) {
		LoadingModList modList = LoadingModList.get();
		for (ModInfo modInfo : modList.getMods()) {
			if (modInfo.getModId().equals(modId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return switch (mixinClassName) {
			case "com.bawnorton.tacz_eo.mixin.tacz_durability.ModernKineticGunItemMixinSquared" -> isModLoaded("gundb");
			case "com.bawnorton.tacz_eo.mixin.tacz_tweaks.BulletInteractionManagerMixin" -> isModLoaded("tacztweaks");
			default -> true;
		};
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return List.of();
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}
}
