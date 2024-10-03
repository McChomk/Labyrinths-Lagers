package io.github.mcchomk.labyrinths_n_lagers.items.custom;

import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.EquipmentSlotGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static io.github.mcchomk.labyrinths_n_lagers.Constants.PLAYER_ENTITY_RANGE;

public class BasicWeaponItem extends ToolItem
{
	public BasicWeaponItem(ToolMaterial toolMaterial, Settings settings) { super(toolMaterial, settings); }

	public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float baseAttackDamage, float attackSpeed, double extraRange) {
		AttributeModifiersComponent.Builder attributes = AttributeModifiersComponent.builder()
			.add(
				EntityAttributes.GENERIC_ATTACK_DAMAGE,
				new EntityAttributeModifier(
					BASE_ATTACK_DAMAGE, baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE
				),
				EquipmentSlotGroup.get(EquipmentSlot.MAINHAND)
			)
			.add(
				EntityAttributes.GENERIC_ATTACK_SPEED,
				new EntityAttributeModifier(BASE_ATTACK_SPEED, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
				EquipmentSlotGroup.get(EquipmentSlot.MAINHAND)
			)
			.add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
				new EntityAttributeModifier(Identifier.parse(PLAYER_ENTITY_RANGE.toString()), extraRange, EntityAttributeModifier.Operation.ADD_VALUE),
				EquipmentSlotGroup.get(EquipmentSlot.MAINHAND));

			return attributes.build();
	}



	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return !miner.isCreative();
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		stack.damageEquipment(1, miner, EquipmentSlot.MAINHAND);
		return super.postMine(stack, world, state, pos, miner);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageEquipment(1, attacker, EquipmentSlot.MAINHAND);
		return true;
	}
}
