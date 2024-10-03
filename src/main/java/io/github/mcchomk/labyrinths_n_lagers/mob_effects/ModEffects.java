package io.github.mcchomk.labyrinths_n_lagers.mob_effects;

import io.github.mcchomk.labyrinths_n_lagers.Constants;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Holder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;


public class ModEffects
{
	// public static final StatusEffect SPELUNKIN_EFFECT;
	public static final StatusEffect RECALL_EFFECT;

	static
	{
		// SPELUNKIN_EFFECT = Registry.register(Registries.STATUS_EFFECT, Identifier.of(Constants.MOD_ID, "spelunkin"), new SpelunkinEffect());
		RECALL_EFFECT = Registry.register(Registries.STATUS_EFFECT, Identifier.of(Constants.MOD_ID, "recall"), new RecallEffect());
	}

	// public static final Potion SPELUNKIN_POTION = new Potion(new StatusEffectInstance(Registries.STATUS_EFFECT.wrapAsHolder(SPELUNKIN_EFFECT),6000,0));
	// public static final Potion LONG_SPELUNKIN_POTION = new Potion(new StatusEffectInstance(Registries.STATUS_EFFECT.wrapAsHolder(SPELUNKIN_EFFECT),12000,0));
	// public static final Potion STRONG_SPELUNKIN_POTION = new Potion(new StatusEffectInstance(Registries.STATUS_EFFECT.wrapAsHolder(SPELUNKIN_EFFECT),6000,1));
	public static final Potion RECALL_POTION = new Potion(new StatusEffectInstance(Registries.STATUS_EFFECT.wrapAsHolder(RECALL_EFFECT),400,0));
	public static final Potion SHORT_RECALL_POTION = new Potion(new StatusEffectInstance(Registries.STATUS_EFFECT.wrapAsHolder(RECALL_EFFECT),200,0));

	public static void register(ModContainer mod)
	{
		// Registry.register(Registries.POTION, Identifier.of(Constants.MOD_ID, "spelunkin"), SPELUNKIN_POTION);
		// Registry.register(Registries.POTION, Identifier.of(Constants.MOD_ID, "long_spelunkin"), LONG_SPELUNKIN_POTION);
		// Registry.register(Registries.POTION, Identifier.of(Constants.MOD_ID, "strong_spelunkin"), STRONG_SPELUNKIN_POTION);
		Registry.register(Registries.POTION, Identifier.of(Constants.MOD_ID, "recall"), RECALL_POTION);
		Registry.register(Registries.POTION, Identifier.of(Constants.MOD_ID, "short_recall"), SHORT_RECALL_POTION);

		/*FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(
				Potions.AWKWARD,
				Ingredient.ofItems(Items.AMETHYST_SHARD),
				Registries.POTION.wrapAsHolder(SPELUNKIN_POTION)
			);
		});
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(
				Registries.POTION.wrapAsHolder(SPELUNKIN_POTION),
				Ingredient.ofItems(Items.REDSTONE),
				Registries.POTION.wrapAsHolder(LONG_SPELUNKIN_POTION)
			);
		});
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(
				Registries.POTION.wrapAsHolder(SPELUNKIN_POTION),
				Ingredient.ofItems(Items.GLOWSTONE_DUST),
				Registries.POTION.wrapAsHolder(STRONG_SPELUNKIN_POTION)
			);
		});*/

		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(
				Potions.AWKWARD,
				Ingredient.ofItems(Items.CRYING_OBSIDIAN),
				Registries.POTION.wrapAsHolder(RECALL_POTION)
			);
		});
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(
				Registries.POTION.wrapAsHolder(RECALL_POTION),
				Ingredient.ofItems(Items.REDSTONE),
				Registries.POTION.wrapAsHolder(SHORT_RECALL_POTION)
			);
		});
	}
}
