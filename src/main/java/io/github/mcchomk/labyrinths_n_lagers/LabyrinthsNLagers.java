package io.github.mcchomk.labyrinths_n_lagers;

import io.github.mcchomk.labyrinths_n_lagers.blocks.ModBlocks;
import io.github.mcchomk.labyrinths_n_lagers.events.CampfireEvents;
import io.github.mcchomk.labyrinths_n_lagers.events.LootTableModifiersEvents;
import io.github.mcchomk.labyrinths_n_lagers.items.ModItems;
import io.github.mcchomk.labyrinths_n_lagers.items.custom.predicate.ModModelPredicates;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabyrinthsNLagers implements ModInitializer
{
	public static final Logger LOGGER = LoggerFactory.getLogger("L&L");

	@Override
	public void onInitialize(ModContainer mod)
	{
		CampfireEvents.registerEvents();
		LootTableModifiersEvents.registerEvents();

		ModItems.register(mod);
		ModBlocks.register(mod);

		ModModelPredicates.registerModModelPredicates();
	}
}
