package io.github.mcchomk.labyrinths_n_lagers.items.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.ToolMaterial;

public class SpearItem extends BasicWeaponItem
{
	public SpearItem(ToolMaterial tier, Settings settings)
	{
		super(tier, settings.component(DataComponentTypes.TOOL, createToolComponent()));
	}

	private static ToolComponent createToolComponent()
	{
		return null;
	}
}
