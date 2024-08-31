package io.github.mcchomk.labyrinths_n_lagers.mixin;


import com.mojang.authlib.GameProfile;
import io.github.mcchomk.labyrinths_n_lagers.LabyrinthsNLagers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerMixin extends PlayerEntity
{
	public ServerPlayerMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) { super(world, pos, yaw, gameProfile); }

	@Inject(
			method = "method_60588",
			at = @At("HEAD"),
			cancellable = true
	)
	private static void lnl$method_60588(ServerWorld world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<ServerPlayerEntity.C_edsacgfr>> cir)
	{
		BlockState blockState = world.getBlockState(pos);
		Block block = blockState.getBlock();

		if (block instanceof CampfireBlock)
		{
			Optional<Vec3d> optional = RespawnAnchorBlock.findRespawnPosition(EntityType.PLAYER, world, pos);
			var returnValue = optional.map(vec3d -> ServerPlayerEntity.C_edsacgfr.method_60595(vec3d, pos));

			cir.setReturnValue(returnValue);
		}
	}
}
