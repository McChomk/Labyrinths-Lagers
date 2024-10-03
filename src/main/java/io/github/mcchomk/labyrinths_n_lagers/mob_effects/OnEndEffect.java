package io.github.mcchomk.labyrinths_n_lagers.mob_effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public abstract class OnEndEffect extends StatusEffect
{
	protected OnEndEffect(StatusEffectType type, int color) { super(type, color); }

	public abstract void onExpire(Entity entity, int amplifier);
}
