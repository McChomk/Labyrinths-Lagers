package io.github.mcchomk.labyrinths_n_lagers.mixin;

import io.github.mcchomk.labyrinths_n_lagers.items.ModItems;
import io.github.mcchomk.labyrinths_n_lagers.items.custom.BasicShieldItem;
import io.github.mcchomk.labyrinths_n_lagers.items.custom.SpearItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity
{

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(null, null);
	}

	@Shadow
	public abstract void incrementStat(Stat<?> stat);

	@Shadow
	public abstract ItemCooldownManager getItemCooldownManager();

	@Shadow
	@Final
	private PlayerInventory inventory;

	@Shadow
	@NotNull
	public abstract ItemStack getWeaponStack();

	@Inject(
		method = "disableShield",
		at = @At(
			value = "HEAD"
		)
	)
	void lnl$disableShield(CallbackInfo ci)
	{
		for (BasicShieldItem basicShieldItem : BasicShieldItem.instances)
		{
			this.getItemCooldownManager().set(basicShieldItem, basicShieldItem.getShieldCooldown());
		}
	}

	@Inject(
		method = "damageShield",
		at = @At(
			value = "HEAD"
		)
	)
	void lnl$damageShield(float amount, CallbackInfo ci)
	{
		if (this.activeItemStack.getItem() instanceof BasicShieldItem shieldItem)
		{
			if (!this.getWorld().isClient) this.incrementStat(Stats.USED.getOrCreateStat(shieldItem));

			if (amount >= 3.0F)
			{
				int i = 1 + MathHelper.floor(amount);
				Hand hand = this.getActiveHand();
				this.getActiveItem().damageEquipment(i, this, getHand(hand));

				if (this.activeItemStack.isEmpty())
				{
					if (hand == Hand.MAIN_HAND) this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
					else this.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);

					this.activeItemStack = ItemStack.EMPTY;
					this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.getWorld().random.nextFloat() * 0.4F);
				}
			}
		}
	}

	@Inject(
		method = "takeShieldHit",
		at = @At(
			value = "HEAD"
		)
	)
	void lnl$takeShieldHit(LivingEntity attacker, CallbackInfo ci)
	{
		if (attacker.canDisableShield() && this.getWeaponStack().getItem() instanceof SpearItem)
		{
			int cooldown = 100;
			for (BasicShieldItem basicShieldItem : BasicShieldItem.instances)
			{
				if (this.getOffHandStack().isOf(basicShieldItem)) cooldown = basicShieldItem.getShieldCooldown();
			}

			this.getItemCooldownManager().set(ModItems.WOODEN_SPEAR, cooldown + 20);
			this.getItemCooldownManager().set(ModItems.STONE_SPEAR, cooldown + 20);
			this.getItemCooldownManager().set(ModItems.IRON_SPEAR, cooldown + 20);
			this.getItemCooldownManager().set(ModItems.GOLDEN_SPEAR, cooldown - 10);
			this.getItemCooldownManager().set(ModItems.DIAMOND_SPEAR, cooldown + 20);
			this.getItemCooldownManager().set(ModItems.NETHERITE_SPEAR, cooldown + 20);
		}
	}
}
