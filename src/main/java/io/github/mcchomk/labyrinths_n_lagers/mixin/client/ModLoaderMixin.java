package io.github.mcchomk.labyrinths_n_lagers.mixin.client;

import io.github.mcchomk.labyrinths_n_lagers.Constants;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Map;

@Mixin(ModelLoader.class)
abstract class ModelLoaderMixin
{
	@Shadow
	protected abstract void addUnbakedModel(ModelIdentifier id, UnbakedModel model);

	@Shadow
	protected abstract JsonUnbakedModel loadModelFromJson(Identifier id) throws IOException;

	@Inject(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V",
			ordinal = 0,
			shift = At.Shift.AFTER
		)
	)
	public void lnl$ModelLoader(BlockColors blockColors, Profiler profiler, Map modelResources, Map blockStateResources, CallbackInfo ci) throws IOException
	{
		this.addUnbakedModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "wooden_spear_hand")), loadModelFromJson(Identifier.of(Constants.MOD_ID, "item/wooden_spear_hand")));
		this.addUnbakedModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "stone_spear_hand")), loadModelFromJson(Identifier.of(Constants.MOD_ID, "item/stone_spear_hand")));
		this.addUnbakedModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "iron_spear_hand")), loadModelFromJson(Identifier.of(Constants.MOD_ID, "item/iron_spear_hand")));
		this.addUnbakedModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "golden_spear_hand")), loadModelFromJson(Identifier.of(Constants.MOD_ID, "item/golden_spear_hand")));
		this.addUnbakedModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "diamond_spear_hand")), loadModelFromJson(Identifier.of(Constants.MOD_ID, "item/diamond_spear_hand")));
		this.addUnbakedModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "netherite_spear_hand")), loadModelFromJson(Identifier.of(Constants.MOD_ID, "item/netherite_spear_hand")));

		this.addUnbakedModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "handheld_parry")), loadModelFromJson(Identifier.of(Constants.MOD_ID, "item/handheld_parry")));
		this.addUnbakedModel(ModelIdentifier.inventory(Identifier.of(Constants.MOD_ID, "iron_rapier_parrying")), loadModelFromJson(Identifier.of(Constants.MOD_ID, "item/iron_rapier_parrying")));
	}
}
