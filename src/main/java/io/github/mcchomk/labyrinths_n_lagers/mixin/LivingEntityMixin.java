package io.github.mcchomk.labyrinths_n_lagers.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import io.github.mcchomk.labyrinths_n_lagers.mob_effects.OnEndEffect;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable
{
	public LivingEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@Shadow
	@Nullable
	public abstract EntityAttributeInstance getAttributeInstance(Holder<EntityAttribute> attribute);

	@Shadow
	public abstract AttributeContainer getAttributes();

	@Shadow
	public abstract boolean isUsingItem();

	@Shadow
	protected ItemStack activeItemStack;

	@Shadow
	public abstract ItemStack getOffHandStack();

	@Inject(
		method = "isBlocking",
		at = @At(
			value = "HEAD"
		)
	)
	void lnl$isBlocking(CallbackInfoReturnable<Boolean> cir)
	{
		if (this.getOffHandStack().getItem() instanceof ShieldItem)
		{
			Multimap<Holder<EntityAttribute>, EntityAttributeModifier> map = ImmutableMap.of(
				EntityAttributes.GENERIC_ATTACK_SPEED,
				new EntityAttributeModifier(Identifier.parse("generic.attack_speed"), -0.45f, EntityAttributeModifier.Operation.ADD_VALUE)

			).asMultimap();

			if (this.isUsingItem() && this.activeItemStack.getItem() instanceof ShieldItem)
			{
				if (!Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED)).hasModifier(Identifier.parse("generic.attack_speed")))
				{
					this.getAttributes().addTemporaryModifiers(map);
				}
			}
			else
			{
				if (Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED)).hasModifier(Identifier.parse("generic.attack_speed")))
				{
					this.getAttributes().removeModifiers(map);
				}
			}
		}
	}

	@Inject(
		method = "onStatusEffectRemoved",
		at = @At(
			"HEAD"
		)
	)
	void lnl$onStatusEffectRemoved(StatusEffectInstance effect, CallbackInfo ci)
	{
		LivingEntity entity = (LivingEntity)((Object)this);
		if (!(entity.getWorld()).isClient)
		{
			if (effect.getEffectType().value() instanceof OnEndEffect) ((OnEndEffect) effect.getEffectType().value()).onExpire(this, effect.getAmplifier());
		}
	}
}
