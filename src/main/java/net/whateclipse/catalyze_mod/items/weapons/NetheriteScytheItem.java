package net.whateclipse.catalyze_mod.items.weapons;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.phys.Vec3;

public class NetheriteScytheItem extends SwordItem {
    public NetheriteScytheItem() {
        super(Tiers.NETHERITE, new Item.Properties()
                .attributes(createAttributes(Tiers.NETHERITE, 5.5F, -2.7F)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player && player.fallDistance > 0) {
            // Critical hit: pull enemy towards player
            Vec3 direction = player.position().subtract(target.position()).normalize();
            target.setDeltaMovement(direction.scale(0.5));
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}