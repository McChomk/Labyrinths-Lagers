package io.github.mcchomk.labyrinths_n_lagers.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
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
	void shieldLowerAttackSpeed(CallbackInfoReturnable<Boolean> cir)
	{
		if (this.getOffHandStack().getItem() instanceof ShieldItem)
		{
			Multimap<Holder<EntityAttribute>, EntityAttributeModifier> map = ImmutableMap.of(
				EntityAttributes.GENERIC_ATTACK_SPEED,
				new EntityAttributeModifier(Identifier.parse("generic.attack_speed"), -0.35f, EntityAttributeModifier.Operation.ADD_VALUE)

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

}
