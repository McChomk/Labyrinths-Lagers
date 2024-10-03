package io.github.mcchomk.labyrinths_n_lagers.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import io.github.mcchomk.labyrinths_n_lagers.IParry;
import io.github.mcchomk.labyrinths_n_lagers.items.custom.RapierItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerRapierMixin extends PlayerEntity implements IParry
{

	public ServerPlayerRapierMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
		super(world, pos, yaw, gameProfile);
	}

	@Shadow
	public abstract boolean damage(DamageSource source, float amount);

	@Shadow
	public abstract void sendMessage(Text message, boolean actionBar);


	@Unique
	private int parryTimeLeft = 0;

	@Override
	public boolean lnl$canParry() { return 0 < this.lnl$getParryTime(); }

	@Override
	public int lnl$getParryTime() { return this.parryTimeLeft; }

	@Override
	public void lnl$setParryTime(int i) { this.parryTimeLeft = i; this.parryTimeLeft = Math.max(this.parryTimeLeft, 0); }

	@Unique
	public boolean isRapierBlocking() { return this.getActiveItem().getItem() instanceof RapierItem; }

	@Inject(
		method = "tick",
		at = @At(
			value = "HEAD"
		)
	)
	public void lnl$tick(CallbackInfo ci)
	{
		if (!isRapierBlocking()) lnl$setParryTime(15);
		else lnl$setParryTime(this.lnl$getParryTime() - 1);
	}

	@WrapOperation(
		method = "damage",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
		)
	)
	private boolean applyRapierBlockProtection(ServerPlayerEntity instance, DamageSource source, float amount, Operation<Boolean> original)
	{
		if (!isRapierBlocking()) return original.call(instance, source, amount);

		if (0 < this.getItemCooldownManager().getCooldownProgress(activeItemStack.getItem(), 0))
		{
			stopUsingItem();
			return original.call(instance, source, amount);
		}

		if (this.blockedByRapier(source))
		{
			boolean isEntity = source.getSource() instanceof LivingEntity;
			LivingEntity entity = isEntity ? (LivingEntity) source.getSource(): null;

			if (isEntity && entity.canDisableShield())
			{
				for (RapierItem rapierItem : RapierItem.instances)
				{
					if (rapierItem.getMaterial() == ToolMaterials.GOLD) this.getItemCooldownManager().set(rapierItem, 40);
					else this.getItemCooldownManager().set(rapierItem, 60);
				}

				this.playSound(SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 6, 1);

				this.damageRapier(amount + 1);
				return original.call(instance, source, amount + 1);
			}

			if (this.lnl$canParry())
			{
				sendMessage(Text.literal("-PARRIED!-"), true);

				if (isEntity) entity.takeKnockback(0.5, this.getX() - entity.getX(), this.getZ() - entity.getZ());
				else if (source.getSource() instanceof ProjectileEntity projectile) projectile.setVelocity(3f * projectile.getVelocity().x, 3f * projectile.getVelocity().y, 3f * projectile.getVelocity().z);

				lnl$setParryTime(-1);
				this.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 4, 1);
				return false;
			}

			this.sendMessage(Text.literal("-BLOCKED-"), true);

			this.playSound(SoundEvents.BLOCK_ANVIL_FALL, SoundCategory.PLAYERS, 8, 1);
			this.damageRapier(amount);
			return original.call(instance, source, amount * 0.4f);
		}



		this.damageRapier(amount);
		return original.call(instance, source, amount);
	}

	@Shadow
	public abstract void playSound(SoundEvent event, SoundCategory category, float volume, float pitch);

	@Shadow
	public abstract void attack(Entity target);

	@Unique
	private void damageRapier(float amount)
	{
		if (amount >= 2.5F)
		{
			int i = 1 + MathHelper.floor(amount);
			Hand hand = this.getActiveHand();
			activeItemStack.damageEquipment(i, this, getHand(hand));

			if (this.activeItemStack.isEmpty())
			{
				if (hand == Hand.MAIN_HAND) this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
				else this.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);

				this.activeItemStack = ItemStack.EMPTY;
				this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + this.getWorld().random.nextFloat() * 0.4F);
			}
		}
	}

	@Unique
	public boolean blockedByRapier(DamageSource source)
	{
		Entity entity = source.getSource();
		boolean bool = (entity instanceof PersistentProjectileEntity persistentProjectileEntity && persistentProjectileEntity.getPierceLevel() > 0);

		if (!source.isTypeIn(DamageTypeTags.BYPASSES_SHIELD) && isRapierBlocking() && !bool)
		{
			Vec3d vec3d = source.getPosition();
			if (vec3d != null)
			{
				Vec3d vec3d2 = this.getRotationVector(0.0F, this.getHeadYaw());
				Vec3d vec3d3 = vec3d.relativize(this.getPos());
				vec3d3 = (new Vec3d(vec3d3.x, 0.0, vec3d3.z)).normalize();

				return vec3d3.dotProduct(vec3d2) < 0.0;
			}
		}

		return false;
	}
}
