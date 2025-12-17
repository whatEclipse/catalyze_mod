package net.whateclipse.catalyze_mod.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.catalyze_mod.Catalyze_mod;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Catalyze_mod.MODID);

        public static final DeferredItem<SwordItem> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
                ()-> new SwordItem(Tiers.NETHERITE, new Item.Properties()
                        .attributes(SwordItem.createAttributes(Tiers.NETHERITE, 5.5F, -2.7f))));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
