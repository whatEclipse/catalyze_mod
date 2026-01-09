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

    // Weapon Catalysts
    public static final DeferredItem<Item> SERRATED_CATALYST = ITEMS.register("serrated_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLAZING_CATALYST = ITEMS.register("blazing_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FREEZING_CATALYST = ITEMS.register("freezing_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VENOMOUS_CATALYST = ITEMS.register("venomous_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BLINDING_CATALYST = ITEMS.register("blinding_catalyst",
            () -> new Item(new Item.Properties()));

    // Tool Catalysts
    public static final DeferredItem<Item> HASTE_CATALYST = ITEMS.register("haste_catalyst",
            () -> new Item(new Item.Properties()));

    // Special Catalysts
    public static final DeferredItem<Item> THROAT_SLIT_CATALYST = ITEMS.register("throat_slit_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TETHER_CATALYST = ITEMS.register("tether_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PIERCING_CATALYST = ITEMS.register("piercing_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SHATTERING_CATALYST = ITEMS.register("shattering_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GRASPING_CATALYST = ITEMS.register("grasping_catalyst",
            () -> new Item(new Item.Properties()));

    // Armor Catalysts
    public static final DeferredItem<Item> RESILIENCE_CATALYST = ITEMS.register("resilience_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERCEPTION_CATALYST = ITEMS.register("perception_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TITAN_SKIN_CATALYST = ITEMS.register("titan_skin_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AGILITY_CATALYST = ITEMS.register("agility_catalyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HEAVY_WEIGHT_CATALYST = ITEMS.register("heavy_weight_catalyst",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> EMPTY_CATALYST = ITEMS.register("empty_catalyst",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COMBAT_TEMPLATE = ITEMS.register("combat_template",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPECIAL_TEMPLATE = ITEMS.register("special_template",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ARMOR_TEMPLATE = ITEMS.register("armor_template",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TOOL_TEMPLATE = ITEMS.register("tool_template",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
