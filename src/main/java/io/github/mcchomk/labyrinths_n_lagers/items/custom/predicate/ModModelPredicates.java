package io.github.mcchomk.labyrinths_n_lagers.items.custom.predicate;

import io.github.mcchomk.labyrinths_n_lagers.Constants;
import io.github.mcchomk.labyrinths_n_lagers.items.ModItems;
import io.github.mcchomk.labyrinths_n_lagers.items.custom.BasicShieldItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModModelPredicates
{
	public static void registerModModelPredicates()
	{
		ModelPredicateProviderRegistry.register(ModItems.FAST_SHIELD, Identifier.of(Constants.MOD_ID, "blocking"), BasicShieldItem::getBlocking);
	}
}
