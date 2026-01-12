package net.whateclipse.catalyze_mod.creativetabs;

import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.catalyze_mod.Catalyze_mod;
import net.whateclipse.catalyze_mod.items.ModItems;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, Catalyze_mod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CATALYZE_TAB = CREATIVE_MODE_TABS
            .register("catalyze_tab", () -> CreativeModeTab.builder()
                    .title(net.minecraft.network.chat.Component.translatable("creativetab.catalyze_mod.catalyze_tab"))
                    .withTabsBefore(net.minecraft.world.item.CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.NETHERITE_SCYTHE.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.NETHERITE_SCYTHE.get());
                        output.accept(ModItems.SERRATED_CATALYST.get());
                        output.accept(ModItems.BLAZING_CATALYST.get());
                        output.accept(ModItems.FREEZING_CATALYST.get());
                        output.accept(ModItems.VENOMOUS_CATALYST.get());
                        output.accept(ModItems.BLINDING_CATALYST.get());
                        output.accept(ModItems.HASTE_CATALYST.get());
                        output.accept(ModItems.THROAT_SLIT_CATALYST.get());
                        output.accept(ModItems.TETHER_CATALYST.get());
                        output.accept(ModItems.PIERCING_CATALYST.get());
                        output.accept(ModItems.SHATTERING_CATALYST.get());
                        output.accept(ModItems.GRASPING_CATALYST.get());
                        output.accept(ModItems.RESILIENCE_CATALYST.get());
                        output.accept(ModItems.PERCEPTION_CATALYST.get());
                        output.accept(ModItems.TITAN_SKIN_CATALYST.get());
                        output.accept(ModItems.AGILITY_CATALYST.get());
                        output.accept(ModItems.HEAVY_WEIGHT_CATALYST.get());
                        output.accept(ModItems.EMPTY_CATALYST.get());
                        output.accept(ModItems.COMBAT_TEMPLATE.get());
                        output.accept(ModItems.SPECIAL_TEMPLATE.get());
                        output.accept(ModItems.ARMOR_TEMPLATE.get());
                        output.accept(ModItems.TOOL_TEMPLATE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}