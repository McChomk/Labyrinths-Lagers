package io.github.mcchomk.labyrinths_n_lagers.mixin;

import io.github.mcchomk.labyrinths_n_lagers.LabyrinthsNLagers;
import net.minecraft.component.DataComponentMap;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;

import net.minecraft.registry.Holder;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin
{
	@Inject(
		method = "isTopIngredient",
		at = @At(
			value = "RETURN"
		),
		cancellable = true
	)
	void lnl$isTopIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir)
	{
		if (stack.isOf(Items.LINGERING_POTION)) cir.setReturnValue(true);
	}

	@Inject(
		method = "craft",
		at = @At(
			value = "RETURN"
		),
		cancellable = true
	)
	void lnl$craft(ItemStack topInput, ItemStack bottomInput, CallbackInfoReturnable<ItemStack> cir)
	{
		if (topInput.isOf(Items.LINGERING_POTION) && !bottomInput.isEmpty())
		{
			ItemStack itemStack = bottomInput.getItem().getDefaultStack();
			PotionContentsComponent components = PotionContentsComponent.DEFAULT;
			ItemStack[] ingredients = new ItemStack[]{topInput, bottomInput};

			for (ItemStack ingredient : ingredients)
			{
				for (StatusEffectInstance k : Objects.requireNonNull(ingredient.get(DataComponentTypes.POTION_CONTENTS)).getEffects()) {
					boolean newEffect = true;

					for (StatusEffectInstance p : components.getEffects())
					{
						if (p.getEffectType().equals(k.getEffectType()))
						{
							newEffect = false;
							components = replace(components, p, k);

							break;
						}
					}

					if (newEffect) components = components.withEffect(k);
				}
			}

			itemStack.set(DataComponentTypes.POTION_CONTENTS, components);
			itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.labyrinths_n_lagers.mixed_potion"));
			itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);

			cir.setReturnValue(itemStack);
		}
		else if (bottomInput.hasGlint() && bottomInput.isOf(Items.POTION) && topInput.isOf(Items.GUNPOWDER))
		{
			ItemStack itemStack = Items.SPLASH_POTION.getDefaultStack();
			PotionContentsComponent components = PotionContentsComponent.DEFAULT;

			for (StatusEffectInstance k : Objects.requireNonNull(bottomInput.get(DataComponentTypes.POTION_CONTENTS)).getEffects())
			{
				boolean newEffect = true;

				for (StatusEffectInstance p : components.getEffects())
				{
					if (p.getEffectType().equals(k.getEffectType()))
					{
						newEffect = false;
						components = replace(components, p, k);

						break;
					}
				}

				if (newEffect) components = components.withEffect(k);
			}


			itemStack.set(DataComponentTypes.POTION_CONTENTS, components);
			itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.labyrinths_n_lagers.mixed_potion"));
			itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);

			cir.setReturnValue(itemStack);
		}
		else if (bottomInput.hasGlint() && bottomInput.isOf(Items.SPLASH_POTION) && topInput.isOf(Items.DRAGON_BREATH))
		{
			ItemStack itemStack = Items.LINGERING_POTION.getDefaultStack();
			PotionContentsComponent components = PotionContentsComponent.DEFAULT;

			for (StatusEffectInstance k : Objects.requireNonNull(bottomInput.get(DataComponentTypes.POTION_CONTENTS)).getEffects())
			{
				boolean newEffect = true;

				for (StatusEffectInstance p : components.getEffects())
				{
					if (p.getEffectType().equals(k.getEffectType()))
					{
						newEffect = false;
						components = replace(components, p, k);

						break;
					}
				}

				if (newEffect) components = components.withEffect(k);
			}


			itemStack.set(DataComponentTypes.POTION_CONTENTS, components);
			itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.labyrinths_n_lagers.mixed_potion"));
			itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);

			cir.setReturnValue(itemStack);
		}

		else cir.setReturnValue(cir.getReturnValue());
	}

	@Unique
	private static PotionContentsComponent replace(PotionContentsComponent component, StatusEffectInstance effect1, StatusEffectInstance effect2)
	{
		PotionContentsComponent component1 = PotionContentsComponent.DEFAULT;

		for (StatusEffectInstance l : component.customEffects())
		{
			if (l.equals(effect1))
			{
				var amplifier = (effect1.getAmplifier() == effect2.getAmplifier() && effect1.getAmplifier() < 9) ? effect1.getAmplifier() + 1: Math.max(effect1.getAmplifier(), effect2.getAmplifier());
				var duration = (int) Math.ceil((float) effect1.getDuration() * (1.0 - 0.5f) + ((float) effect2.getDuration() * 0.5f));

				component1 = component1.withEffect(new StatusEffectInstance(effect1.getEffectType(), duration, amplifier));
			}
			else component1 = component1.withEffect(l);
		}

		return component1;
	}
}
