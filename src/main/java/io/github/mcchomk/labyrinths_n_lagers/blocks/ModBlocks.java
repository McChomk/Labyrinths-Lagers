package io.github.mcchomk.labyrinths_n_lagers.blocks;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;

public class ModBlocks
{
	public static final Block KEG = new KegBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS).nonOpaque());

	public static void register(ModContainer mod)
	{
		Registry.register(Registries.BLOCK, Identifier.of(mod.metadata().id(), "keg"), KEG);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "keg"), new BlockItem(KEG, new Item.Settings()));

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL_BLOCKS).register(entries -> {
			entries.addAfter(Items.LOOM, KEG.asItem());
		});
	}
}
