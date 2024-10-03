package io.github.mcchomk.labyrinths_n_lagers.mob_effects;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;
import java.util.Optional;

public class RecallEffect extends OnEndEffect
{
	protected RecallEffect()
	{
		super(StatusEffectType.NEUTRAL, 12999423);
	}

	@Override
	public void onExpire(Entity entity, int amplifier)
	{
		if (entity instanceof PlayerEntity)
		{
			assert entity instanceof PlayerEntity;
			ServerPlayerEntity player = (ServerPlayerEntity) entity;
			ServerWorld targetWorld = player.server.getWorld(player.getSpawnPointDimension());

			BlockPos spawnpoint = player.getSpawnPointPosition();

			if (spawnpoint == null) teleportToWorldSpawn(player);
			else
			{
				assert targetWorld != null;
				BlockState respawnBlockState = targetWorld.getBlockState(spawnpoint);
				Block respawnBlock = respawnBlockState.getBlock();
				Optional<Vec3d> respawnPosition = Optional.empty();

				if (respawnBlock instanceof RespawnAnchorBlock || respawnBlock instanceof CampfireBlock) respawnPosition = RespawnAnchorBlock.findRespawnPosition(EntityType.PLAYER, targetWorld, spawnpoint);
				else if (respawnBlock instanceof BedBlock) respawnPosition = BedBlock.findWakeUpPosition(EntityType.PLAYER, targetWorld, spawnpoint, respawnBlockState.get(BedBlock.FACING), player.getSpawnAngle());
				else if (player.isSpawnPointSet())
				{
					boolean footBlockClear = respawnBlock.canMobSpawnInside(respawnBlockState);
					boolean headBlockClear = targetWorld.getBlockState(spawnpoint.up()).getBlock().canMobSpawnInside(respawnBlockState);
					if (footBlockClear && headBlockClear) {
						respawnPosition = Optional.of(new Vec3d((double)spawnpoint.getX() + 0.5D, (double)spawnpoint.getY() + 0.1D, (double)spawnpoint.getZ() + 0.5D));
					}
				}

				if (respawnPosition.isPresent())
				{
					Vec3d spawnVec = respawnPosition.get();
					player.teleport(targetWorld, spawnVec.getX(), spawnVec.getY(), spawnVec.getZ(), player.getSpawnAngle(), 0.5F);
					targetWorld.playSound(null, spawnpoint, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 0.4f, 1f);

				}
				else
				{
					player.sendMessage(Text.translatable("block.minecraft.spawn.not_valid"), false);
					teleportToWorldSpawn(player);
				}
			}
		}
	}

	@Unique
	private void teleportToWorldSpawn(ServerPlayerEntity player)
	{
		ServerWorld overworld = Objects.requireNonNull(player.getServer()).getWorld(ServerWorld.OVERWORLD);

		assert overworld != null;
		BlockPos worldSpawn = overworld.getSpawnPos();

		player.teleport(overworld, worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ(), player.getSpawnAngle(), 0.5F);
		overworld.playSound(null, worldSpawn, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 0.4f, 1f);
	}
}
