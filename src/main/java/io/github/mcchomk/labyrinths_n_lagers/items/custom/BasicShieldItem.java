package io.github.mcchomk.labyrinths_n_lagers.items.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

import java.util.HashSet;

public class BasicShieldItem extends ShieldItem
{
	private final int cooldown_ticks;
	private final float speed_modifier;
	private final ItemStack repair_ingredient;
	public final static HashSet<BasicShieldItem> instances = new HashSet<>();

	public BasicShieldItem(Settings settings, int cooldownTicks, float speedModifier, ItemStack repairIngredient)
	{
		super(settings);
		cooldown_ticks = cooldownTicks;
		speed_modifier = speedModifier;
		repair_ingredient = repairIngredient;
		instances.add(this);
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient)
	{
		return ingredient.isOf(repair_ingredient.getItem());
	}

	public int getShieldCooldown() { return cooldown_ticks; }
	public float getSpeedModifier() { return speed_modifier; }

	public static float getBlocking(ItemStack itemStack, LivingEntity livingEntity)
	{
		return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
	}
}
