package io.github.mcchomk.labyrinths_n_lagers.events;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.CampfireBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class CampfireEvents
{
	public static void registerEvents()
	{
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
		{
			if (world.isClient()) return ActionResult.PASS;
			if (!world.getDimension().bedWorks()) return ActionResult.PASS;

			BlockPos pos = hitResult.getBlockPos();

			if (player.isSneaking() && player.getMainHandStack().isEmpty() && player.getOffHandStack().isEmpty())
			{
				if (world.getBlockState(pos).getBlock() instanceof CampfireBlock && !world.isClient())
				{
					world.playSound(
							null,
							(double) pos.getX() + 0.5,
							(double) pos.getY() + 0.5,
							(double) pos.getZ() + 0.5,
							SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE,
							SoundCategory.BLOCKS,
							2F,
							1.0F
					);

					((ServerWorld) world).spawnParticles(
							((ServerPlayerEntity)player),
							ParticleTypes.END_ROD,
							true,
							(double) pos.getX() + 0.5,
							(double) pos.getY() + 1.1,
							(double) pos.getZ() + 0.5,
							6,
							0.2,
							0.6,
							0.2,
							0.05
					);

					((ServerPlayerEntity)player).setSpawnPoint(world.getRegistryKey(), pos, 0.0f, false, true);
					player.swingHand(hand);

					return ActionResult.SUCCESS_NO_ITEM_USED;
				}
			}

			return ActionResult.PASS;
		});
	}
}
