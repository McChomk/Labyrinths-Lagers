package io.github.mcchomk.labyrinths_n_lagers.mixin.client;

import io.github.mcchomk.labyrinths_n_lagers.items.custom.BasicShieldItem;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
abstract
class ClientPlayerMixin extends LivingEntity
{
	protected ClientPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(null, null);
	}

	@Shadow
	public Input input;

	@Inject(
		method = "tickMovement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
		)
	)
	void lnl$tickMovement(CallbackInfo ci)
	{
		if (isUsingItem() && getActiveItem().getItem() instanceof BasicShieldItem && !hasVehicle())
		{
			input.sidewaysMovement /= ((BasicShieldItem) getActiveItem().getItem()).getSpeedModifier();
			input.forwardMovement /= ((BasicShieldItem) getActiveItem().getItem()).getSpeedModifier();
		}
	}
}
