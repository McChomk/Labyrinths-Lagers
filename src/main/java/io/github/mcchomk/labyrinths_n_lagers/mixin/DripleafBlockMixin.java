package io.github.mcchomk.labyrinths_n_lagers.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BigDripleafBlock.class)
public abstract class DripleafBlockMixin extends HorizontalFacingBlock implements Fertilizable, Waterloggable
{
	public DripleafBlockMixin(Settings settings) { super(null); }

	@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance)
	{
		if (entity.bypassesLandingEffects()) super.onLandedUpon(world, state, pos, entity, fallDistance);
		else entity.handleFallDamage(fallDistance, 0.0F, world.getDamageSources().fall());
	}

	@Override
	public void onEntityLand(BlockView world, Entity entity)
	{
		if (entity.bypassesLandingEffects()) super.onEntityLand(world, entity);
		else this.bounce(entity);
	}

	@Unique
	private void bounce(Entity entity)
	{
		Vec3d vec3d = entity.getVelocity();
		if (vec3d.y < 0.0)
		{
			double d = entity instanceof LivingEntity ? 1.2 : 0.8;
			entity.setVelocity(vec3d.x, Math.min(-vec3d.y * d, 1.4f), vec3d.z);
		}

	}
}
