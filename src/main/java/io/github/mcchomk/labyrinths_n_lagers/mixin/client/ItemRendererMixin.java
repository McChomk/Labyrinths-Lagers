package io.github.mcchomk.labyrinths_n_lagers.mixin.client;

import io.github.mcchomk.labyrinths_n_lagers.Constants;
import io.github.mcchomk.labyrinths_n_lagers.items.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin
{
	@ModifyVariable(
		method = "renderItem",
		at = @At(value = "HEAD"),
		argsOnly = true)
	public BakedModel lnl$renderItem(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
	{
		if (!(renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED))
		{
			if (stack.isOf(ModItems.WOODEN_SPEAR)) return ((ItemRendererAccessor)this).lnl$getModels().getModelManager().getModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "wooden_spear_hand")));
			else if (stack.isOf(ModItems.STONE_SPEAR)) return ((ItemRendererAccessor)this).lnl$getModels().getModelManager().getModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "stone_spear_hand")));
			else if (stack.isOf(ModItems.IRON_SPEAR)) return ((ItemRendererAccessor)this).lnl$getModels().getModelManager().getModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "iron_spear_hand")));
			else if (stack.isOf(ModItems.GOLDEN_SPEAR)) return ((ItemRendererAccessor)this).lnl$getModels().getModelManager().getModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "golden_spear_hand")));
			else if (stack.isOf(ModItems.DIAMOND_SPEAR)) return ((ItemRendererAccessor)this).lnl$getModels().getModelManager().getModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "diamond_spear_hand")));
			else if (stack.isOf(ModItems.NETHERITE_SPEAR)) return ((ItemRendererAccessor)this).lnl$getModels().getModelManager().getModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "netherite_spear_hand")));
		}

		return value;
	}
}
