package com.bawnorton.tacz_eo.client.mixin.injectors.v1_21_1.tacz;

import com.bawnorton.tacz_eo.enchantment.v1_21_1.EnchantmentValueEffects;
import com.bawnorton.tacz_eo.enchantment.v1_21_1.HeadshotCondition;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.tacz.guns.client.tooltip.ClientGunTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
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

@Mixin(value = ClientGunTooltip.class, remap = false)
abstract class ClientGunTooltipMixin {
    @Shadow
    @Final
    private ItemStack gun;
    @Shadow
    private int maxWidth;
    @Unique
    private List<Component> tacz_eo$enchantmentLines;

    @Inject(
            method = "getText",
            at = @At("HEAD")
    )
    private void loadEnchantmentLines(CallbackInfo ci) {
        tacz_eo$enchantmentLines = new ArrayList<>();
        gun.getEnchantments().addToTooltip(Item.TooltipContext.EMPTY, tacz_eo$enchantmentLines::add, TooltipFlag.NORMAL);
    }

    @Inject(
            method = "getText",
            at = @At("TAIL")
    )
    private void includeEnchantmentWidth(CallbackInfo ci) {
        Font font = Minecraft.getInstance().font;
        for (Component line : tacz_eo$enchantmentLines) {
            maxWidth = Math.max(maxWidth, font.width(line));
        }
    }

    @ModifyExpressionValue(
            method = "getText",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/tacz/guns/util/AttachmentDataUtils;getDamageWithAttachment(Lnet/minecraft/world/item/ItemStack;Lcom/tacz/guns/resource/pojo/data/gun/GunData;)D"
            )
    )
    private double showDamageEffects(double original) {
        return EnchantmentValueEffects.apply(
                gun,
                EnchantmentEffectComponents.DAMAGE,
                (float) original,
                RandomSource.create(0L),
                effect -> effect.requirements().isEmpty()
        );
    }

    @ModifyExpressionValue(
            method = "getText",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/tacz/guns/util/AttachmentDataUtils;getHeadshotMultiplier(Lnet/minecraft/world/item/ItemStack;Lcom/tacz/guns/resource/pojo/data/gun/GunData;)D"
            )
    )
    private double showHeadshotEffects(double original) {
        return EnchantmentValueEffects.apply(gun, EnchantmentEffectComponents.DAMAGE, (float) original,
                RandomSource.create(0L), effect -> effect.requirements().filter(HeadshotCondition.class::isInstance).isPresent());
    }

    @Inject(
            method = "renderText",
            at = @At(
                    value = "FIELD",
                    target = "Lcom/tacz/guns/item/GunTooltipPart;UPGRADES_TIP:Lcom/tacz/guns/item/GunTooltipPart;",
                    opcode = Opcodes.GETSTATIC,
                    remap = false
            ),
            remap = true
    )
    private void appendEnchantmentTooltip(Font font, int x, int y, Matrix4f matrix, MultiBufferSource.BufferSource buffers, CallbackInfo ci, @Local(name = "yOffset") LocalIntRef yOffsetRef) {
        if (tacz_eo$enchantmentLines.isEmpty()) return;

        int offset = yOffsetRef.get() + 4;
        for (Component line : tacz_eo$enchantmentLines) {
            font.drawInBatch(line, x, offset, 0xAAAAAA, false, matrix, buffers, Font.DisplayMode.NORMAL, 0, 0xF000F0);
            offset += 10;
        }
        yOffsetRef.set(offset);
    }

    @ModifyReturnValue(
            method = "getHeight",
            at = @At("RETURN"),
            remap = true
    )
    private int addEnchantmentHeight(int original) {
        return original + tacz_eo$enchantmentLines.size() * 10 + (tacz_eo$enchantmentLines.isEmpty() ? 0 : 4);
    }
}
