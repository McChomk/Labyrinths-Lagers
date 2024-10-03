package io.github.mcchomk.labyrinths_n_lagers.items.custom;

import io.github.mcchomk.labyrinths_n_lagers.IParry;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Holder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.HashSet;

public class RapierItem extends BasicWeaponItem
{
	public final static HashSet<RapierItem> instances = new HashSet<>();

	public RapierItem(ToolMaterial tier, Settings settings)
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



	public static float getParrying(ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity, int i)
	{
		return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks)
	{
		if (user instanceof ServerPlayerEntity player && 0 < ((IParry) player).lnl$getParryTime())
		{
			for (RapierItem rapierItem : RapierItem.instances)
			{
				if (rapierItem.getMaterial() == ToolMaterials.GOLD) player.getItemCooldownManager().set(rapierItem, 40);
				else player.getItemCooldownManager().set(rapierItem, 60);
			}
		}
	}

	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.NONE;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		ItemStack stack = user.getStackInHand(hand);

		user.setCurrentHand(hand);
		return (user instanceof ServerPlayerEntity player && 0 < ((IParry) player).lnl$getParryTime()) ? TypedActionResult.consume(stack) : TypedActionResult.success(stack);
	}

	@Override
	public int getUseTicks(ItemStack stack, LivingEntity entity) {
		return 72000;
	}
}
