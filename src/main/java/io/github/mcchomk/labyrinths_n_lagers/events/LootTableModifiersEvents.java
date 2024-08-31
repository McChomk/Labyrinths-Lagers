package io.github.mcchomk.labyrinths_n_lagers.events;

import io.github.mcchomk.labyrinths_n_lagers.items.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.gen.structure.BuiltInStructures.*;

public class LootTableModifiersEvents
{
	public static void registerEvents()
	{
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (key.equals(LootTables.WOODLAND_MANSION_CHEST) ||
					key.equals(LootTables.JUNGLE_TEMPLE_CHEST) ||
					key.equals(LootTables.END_CITY_TREASURE_CHEST) ||
					key.equals(LootTables.STRONGHOLD_CROSSING_CHEST) ||
					key.equals(LootTables.STRONGHOLD_CORRIDOR_CHEST) ||
					key.equals(LootTables.STRONGHOLD_LIBRARY_CHEST) ||
					key.equals(LootTables.ANCIENT_CITY_CHEST))
			{
				float isLibrary = key.equals(LootTables.STRONGHOLD_LIBRARY_CHEST) || key.equals(LootTables.ANCIENT_CITY_CHEST) ? 4 : 1;

				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.method_932(1f))
						.with(ItemEntry.builder(ModItems.OLDEN_BOOK))
						.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(isLibrary, isLibrary * 3f)).build());

				tableBuilder.pool(poolBuilder);
			}
		});
	}
}
