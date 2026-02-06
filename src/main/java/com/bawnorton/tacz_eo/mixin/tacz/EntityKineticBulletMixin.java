package com.bawnorton.tacz_eo.mixin.tacz;

import com.bawnorton.tacz_eo.event.TACZEOEventHandler;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import com.tacz.guns.entity.EntityKineticBullet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityKineticBullet.class)
abstract class EntityKineticBulletMixin {
	@ModifyExpressionValue(
			method = "onHitEntity",
			at = @At(
					value = "NEW",
					target = "(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;FLorg/apache/commons/lang3/tuple/Pair;ZFLnet/minecraftforge/fml/LogicalSide;)Lcom/tacz/guns/api/event/common/EntityHurtByGunEvent$Pre;",
					remap = true
			),
			remap = false
	)
	private EntityHurtByGunEvent.Pre triggerJavaHandler(EntityHurtByGunEvent.Pre original) {
		TACZEOEventHandler.onEntityHurtByGunEvent(original);
		return original;
	}
}
