package io.github.mcchomk.labyrinths_n_lagers.mixin.client;

import io.github.mcchomk.labyrinths_n_lagers.LabyrinthsNLagers;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
class TitleScreenMixin
{
	@Inject(
			method = "init",
			at = @At("TAIL")
	)
	public void lnl$init(CallbackInfo ci)
	{
		LabyrinthsNLagers.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
