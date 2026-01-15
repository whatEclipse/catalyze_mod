package net.whateclipse.catalyze_mod.items.weapons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class NetheriteScytheItem extends SwordItem {
    public NetheriteScytheItem() {
        super(Tiers.NETHERITE, new Item.Properties()
                .attributes(createAttributes(Tiers.NETHERITE, 5.5F, -2.7F)));

    }

}