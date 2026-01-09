package net.whateclipse.catalyze_mod.items.weapons;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class BlazingSwordItem extends SwordItem {
    public BlazingSwordItem() {
        super(Tiers.NETHERITE, new Item.Properties()
                .attributes(createAttributes(Tiers.NETHERITE, 3, -2.4F))
                .fireResistant()); // Netherite is fire resistant, so this should be too.
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.igniteForSeconds(5);
        return super.hurtEnemy(stack, target, attacker);
    }
}
