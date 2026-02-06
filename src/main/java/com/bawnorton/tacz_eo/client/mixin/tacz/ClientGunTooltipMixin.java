package com.bawnorton.tacz_eo.client.mixin.tacz;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.tacz.guns.client.tooltip.ClientGunTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClientGunTooltip.class)
abstract class ClientGunTooltipMixin {
	@Shadow
	@Final
	private ItemStack gun;
	@Unique
	private List<Component> tacz_eo$enchantmentLines;

	@Inject(
			method = "getText",
			at = @At("HEAD"),
			remap = false
	)
	private void loadEnchantmentLines(CallbackInfo ci) {
		this.tacz_eo$enchantmentLines = new ArrayList<>();
		ItemStack.appendEnchantmentNames(tacz_eo$enchantmentLines, gun.getEnchantmentTags());
	}

	@Inject(
			method = "renderText",
			at = @At(
					value = "FIELD",
					target = "Lcom/tacz/guns/item/GunTooltipPart;UPGRADES_TIP:Lcom/tacz/guns/item/GunTooltipPart;",
					opcode = Opcodes.GETSTATIC
			)
	)
	private void appendEnchantmentTooltip(Font font, int pX, int pY, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource, CallbackInfo ci, @Local(name = "yOffset") LocalIntRef yOffsetRef) {
		if (tacz_eo$enchantmentLines.isEmpty()) return;

		int yOffset = yOffsetRef.get();
		yOffset += 4;
		for (Component line : tacz_eo$enchantmentLines) {
			font.drawInBatch(line, pX, yOffset, 0xaaaaaa, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 0xF000F0);
			yOffset += 10;
		}
		yOffsetRef.set(yOffset);
	}

	@ModifyReturnValue(
			method = "getHeight",
			at = @At("RETURN")
	)
	private int addEnchantmentTooltipHeight(int originalHeight) {
		return originalHeight + tacz_eo$enchantmentLines.size() * 10 + (tacz_eo$enchantmentLines.isEmpty() ? 0 : 4);
	}
}
