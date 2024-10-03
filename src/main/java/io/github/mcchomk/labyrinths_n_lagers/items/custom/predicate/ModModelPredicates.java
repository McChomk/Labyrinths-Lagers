package io.github.mcchomk.labyrinths_n_lagers.items.custom.predicate;

import io.github.mcchomk.labyrinths_n_lagers.Constants;
import io.github.mcchomk.labyrinths_n_lagers.items.ModItems;
import io.github.mcchomk.labyrinths_n_lagers.items.custom.BasicShieldItem;
import io.github.mcchomk.labyrinths_n_lagers.items.custom.RapierItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModModelPredicates
{
	public static void registerModModelPredicates()
	{
		ModelPredicateProviderRegistry.register(ModItems.FAST_SHIELD, Identifier.of(Constants.MOD_ID, "blocking"), (itemStack, _, livingEntity, _) -> BasicShieldItem.getBlocking(itemStack, livingEntity));

		ModelPredicateProviderRegistry.register(ModItems.WOODEN_RAPIER, Identifier.of(Constants.MOD_ID, "parrying"), RapierItem::getParrying);
		ModelPredicateProviderRegistry.register(ModItems.STONE_RAPIER, Identifier.of(Constants.MOD_ID, "parrying"), RapierItem::getParrying);
		ModelPredicateProviderRegistry.register(ModItems.IRON_RAPIER, Identifier.of(Constants.MOD_ID, "parrying"), RapierItem::getParrying);
		ModelPredicateProviderRegistry.register(ModItems.GOLDEN_RAPIER, Identifier.of(Constants.MOD_ID, "parrying"), RapierItem::getParrying);
		ModelPredicateProviderRegistry.register(ModItems.DIAMOND_RAPIER, Identifier.of(Constants.MOD_ID, "parrying"), RapierItem::getParrying);
		ModelPredicateProviderRegistry.register(ModItems.NETHERITE_RAPIER, Identifier.of(Constants.MOD_ID, "parrying"), RapierItem::getParrying);
	}
}
