package io.github.mcchomk.labyrinths_n_lagers.items.custom;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Holder;

import java.util.HashSet;

public class SpearItem extends BasicWeaponItem
{
	public final static HashSet<SpearItem> instances = new HashSet<>();

	public SpearItem(ToolMaterial tier, Settings settings)
	{
		super(tier, settings.component(DataComponentTypes.TOOL, createToolComponent()));
		instances.add(this);
	}

	private static ToolComponent createToolComponent()
	{
		return null;
	}

	@Override
	public boolean canBeEnchantedWith(ItemStack stack, Holder<Enchantment> enchantment, EnchantingContext context) {
		return super.canBeEnchantedWith(stack, enchantment, context);
	}
}
