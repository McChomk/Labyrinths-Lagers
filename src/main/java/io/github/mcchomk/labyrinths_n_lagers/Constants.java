package io.github.mcchomk.labyrinths_n_lagers;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.UUID;

public class Constants
{
	public static final String MOD_ID = "labyrinths_n_lagers";
	public static final UUID PLAYER_ENTITY_RANGE = UUID.fromString("74a196e4-cd9e-4c93-8606-8e7f0afdc959");

	public static List<Text> MIXED_POTION_LORE = List.of(
		Text.literal(" "),
		Text.translatable("item.labyrinths_n_lagers.mixed_potion_lore0"),
		Text.translatable("item.labyrinths_n_lagers.mixed_potion_lore1"),
		Text.literal(" "));
}
