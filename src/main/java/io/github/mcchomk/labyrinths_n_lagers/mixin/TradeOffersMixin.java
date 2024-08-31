package io.github.mcchomk.labyrinths_n_lagers.mixin;

import io.github.mcchomk.labyrinths_n_lagers.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.village.TradeOffers.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;


@Mixin(EnchantBookFactory.class)
class TradeOffersMixin
{
	@Redirect(
			method = "create",
			slice = @Slice(
				from = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/village/TradeableItem;<init>(Lnet/minecraft/item/ItemConvertible;I)V")
			),
			at = @At(
				value = "FIELD",
				target = "Lnet/minecraft/item/Items;BOOK:Lnet/minecraft/item/Item;"
			)
	)
	private Item lnl$create() { return ModItems.OLDEN_BOOK; }
}
