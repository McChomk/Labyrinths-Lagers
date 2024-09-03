package io.github.mcchomk.labyrinths_n_lagers.mixin;

import io.github.mcchomk.labyrinths_n_lagers.LabyrinthsNLagers;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin
{
	@Inject(
		method = "canCraft",
		at = @At(
			value = "TAIL"
		),
		cancellable = true
	)
	private static void lnl$canCraft(BrewingRecipeRegistry registry, DefaultedList<ItemStack> slots, CallbackInfoReturnable<Boolean> cir)
	{
		if (slots.get(3).isOf(Items.LINGERING_POTION))
		{
			boolean canbrew = false;

			for(int i = 0; i < 3; ++i)
			{
				if (!slots.get(i).isEmpty()) canbrew = true;
			}

			cir.setReturnValue(canbrew);
		}
		else cir.setReturnValue(cir.getReturnValue());
	}
}
