package io.github.mcchomk.labyrinths_n_lagers.mixin.client;

import io.github.mcchomk.labyrinths_n_lagers.items.custom.SpearItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.item.ShieldItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin
{
	@Shadow
	@Nullable
	public ClientPlayerEntity player;

	@Shadow
	@Final
	public GameOptions options;

	@Shadow
	protected abstract boolean doAttack();

	@Shadow
	public int attackCooldown;

	@Inject(
		method = "handleInputEvents",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z",
			ordinal = 0,
			shift = At.Shift.BEFORE
		)
	)
	void lnl$handleInputEvents(CallbackInfo ci)
	{
		assert player != null;
		if (player.isUsingItem() && 0 >= attackCooldown)
		{
			if (player.getMainHandStack().getItem() instanceof SpearItem && player.getOffHandStack().getItem() instanceof ShieldItem)
			{
				if (options.attackKey.wasPressed()) doAttack();
			}
		}
	}

	@Inject(
		method = "doAttack",
		at = @At("HEAD"),
		cancellable = true
	)
	void lnl$doAttack(CallbackInfoReturnable<Boolean> cir)
	{
		assert player != null;
		if ((player.getMainHandStack().getItem() instanceof SpearItem) && player.getItemCooldownManager() != null && player.getItemCooldownManager().getCooldownProgress(player.getMainHandStack().getItem(), 0.5f) > 0)
		{
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
