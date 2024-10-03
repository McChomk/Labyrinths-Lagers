package io.github.mcchomk.labyrinths_n_lagers.items;

import io.github.mcchomk.labyrinths_n_lagers.items.custom.BasicShieldItem;
import io.github.mcchomk.labyrinths_n_lagers.items.custom.RapierItem;
import io.github.mcchomk.labyrinths_n_lagers.items.custom.SpearItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;

public class ModItems
{
	public static final float spearDamage = 3f;
	public static final float spearSpeed = -3f;
	public static final double spearRange = 1;

	public static final float rapierDamage = 1f;
	public static final float rapierSpeed = -2.4f;
	public static final double rapierRange = 0;

	public static final Item CRUDE_IRON = new Item(new Item.Settings());
	public static final Item CRUDE_COPPER = new Item(new Item.Settings());
	public static final Item CRUDE_GOLD = new Item(new Item.Settings());

	public static final Item OLDEN_BOOK = new Item(new Item.Settings());

	public static final Item FAST_SHIELD = new BasicShieldItem(new Item.Settings().maxDamage(112), 40, 0.13f, Items.GOLD_INGOT.getDefaultStack());

	public static final Item WOODEN_SPEAR = new SpearItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiersComponent(SpearItem.createAttributeModifiers(ToolMaterials.WOOD, spearDamage, spearSpeed, spearRange)));
	public static final Item STONE_SPEAR = new SpearItem(ToolMaterials.STONE, new Item.Settings().attributeModifiersComponent(SpearItem.createAttributeModifiers(ToolMaterials.STONE, spearDamage, spearSpeed, spearRange)));
	public static final Item IRON_SPEAR = new SpearItem(ToolMaterials.IRON, new Item.Settings().attributeModifiersComponent(SpearItem.createAttributeModifiers(ToolMaterials.IRON, spearDamage, spearSpeed, spearRange)));
	public static final Item GOLDEN_SPEAR = new SpearItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiersComponent(SpearItem.createAttributeModifiers(ToolMaterials.GOLD, spearDamage, spearSpeed, spearRange)));
	public static final Item DIAMOND_SPEAR = new SpearItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiersComponent(SpearItem.createAttributeModifiers(ToolMaterials.DIAMOND, spearDamage, spearSpeed, spearRange)));
	public static final Item NETHERITE_SPEAR = new SpearItem(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiersComponent(SpearItem.createAttributeModifiers(ToolMaterials.NETHERITE, spearDamage, spearSpeed, spearRange)));

	public static final Item WOODEN_RAPIER = new RapierItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiersComponent(RapierItem.createAttributeModifiers(ToolMaterials.WOOD, rapierDamage, rapierSpeed, rapierRange)));
	public static final Item STONE_RAPIER = new RapierItem(ToolMaterials.STONE, new Item.Settings().attributeModifiersComponent(RapierItem.createAttributeModifiers(ToolMaterials.STONE, rapierDamage, rapierSpeed, rapierRange)));
	public static final Item IRON_RAPIER = new RapierItem(ToolMaterials.IRON, new Item.Settings().attributeModifiersComponent(RapierItem.createAttributeModifiers(ToolMaterials.IRON, rapierDamage, rapierSpeed, rapierRange)));
	public static final Item GOLDEN_RAPIER = new RapierItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiersComponent(RapierItem.createAttributeModifiers(ToolMaterials.GOLD, rapierDamage, rapierSpeed, rapierRange)));
	public static final Item DIAMOND_RAPIER = new RapierItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiersComponent(RapierItem.createAttributeModifiers(ToolMaterials.DIAMOND, rapierDamage, rapierSpeed, rapierRange)));
	public static final Item NETHERITE_RAPIER = new RapierItem(ToolMaterials.NETHERITE, new Item.Settings().attributeModifiersComponent(RapierItem.createAttributeModifiers(ToolMaterials.NETHERITE, rapierDamage, rapierSpeed, rapierRange)));

	public static void register(ModContainer mod)
	{
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "crude_iron_ingot"), CRUDE_IRON);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "crude_copper_ingot"), CRUDE_COPPER);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "crude_gold_ingot"), CRUDE_GOLD);

		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "olden_book"), OLDEN_BOOK);

		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "golden_buckler"), FAST_SHIELD);

		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "wooden_spear"), WOODEN_SPEAR);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "stone_spear"), STONE_SPEAR);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "iron_spear"), IRON_SPEAR);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "golden_spear"), GOLDEN_SPEAR);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "diamond_spear"), DIAMOND_SPEAR);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "netherite_spear"), NETHERITE_SPEAR);

		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "wooden_rapier"), WOODEN_RAPIER);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "stone_rapier"), STONE_RAPIER);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "iron_rapier"), IRON_RAPIER);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "golden_rapier"), GOLDEN_RAPIER);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "diamond_rapier"), DIAMOND_RAPIER);
		Registry.register(Registries.ITEM, Identifier.of(mod.metadata().id(), "netherite_rapier"), NETHERITE_RAPIER);


		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addAfter(Items.IRON_INGOT, CRUDE_IRON);
			entries.addAfter(Items.COPPER_INGOT, CRUDE_COPPER);
			entries.addAfter(Items.GOLD_INGOT, CRUDE_GOLD);
			// entries.addAfter(Items.NETHERITE_INGOT, ORICHALCUM_INGOT);

			entries.addAfter(Items.BOOK, OLDEN_BOOK);

		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.addAfter(Items.SHIELD, FAST_SHIELD);
			entries.addAfter(Items.NETHERITE_SWORD, WOODEN_SPEAR);
			entries.addAfter(WOODEN_SPEAR, STONE_SPEAR);
			entries.addAfter(STONE_SPEAR, IRON_SPEAR);
			entries.addAfter(IRON_SPEAR, GOLDEN_SPEAR);
			entries.addAfter(GOLDEN_SPEAR, DIAMOND_SPEAR);
			entries.addAfter(DIAMOND_SPEAR, NETHERITE_SPEAR);

			entries.addAfter(NETHERITE_SPEAR, WOODEN_RAPIER);
			entries.addAfter(WOODEN_RAPIER, STONE_RAPIER);
			entries.addAfter(STONE_RAPIER, IRON_RAPIER);
			entries.addAfter(IRON_RAPIER, GOLDEN_RAPIER);
			entries.addAfter(GOLDEN_RAPIER, DIAMOND_RAPIER);
			entries.addAfter(DIAMOND_RAPIER, NETHERITE_RAPIER);
		});
	}
}
