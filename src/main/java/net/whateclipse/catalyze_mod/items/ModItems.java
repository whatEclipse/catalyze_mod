package net.whateclipse.catalyze_mod.items;

import io.netty.util.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.catalyze_mod.Catalyze_mod;
import net.whateclipse.catalyze_mod.items.weapons.NetheriteScytheItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Catalyze_mod.MODID);

    public static final DeferredItem<NetheriteScytheItem> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
            NetheriteScytheItem::new);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
