package net.whateclipse.catalyze_mod.creativetabs;

import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.catalyze_mod.Catalyze_mod;
import net.whateclipse.catalyze_mod.items.ModItems;
import net.whateclipse.catalyze_mod.items.weapons.NetheriteScythe;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, Catalyze_mod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CATALYZE_TAB = CREATIVE_MODE_TABS.register("catalyze_tab", () -> CreativeModeTab.builder()
        .title(net.minecraft.network.chat.Component.translatable("creativetab.catalyze_mod.catalyze_tab"))
        .withTabsBefore(net.minecraft.world.item.CreativeModeTabs.COMBAT)
        .icon(() -> ModItems.NETHERITE_SCYTHE.get().getDefaultInstance())
        .displayItems((parameters, output) -> {
            output.accept(ModItems.NETHERITE_SCYTHE.get());
        })
        .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}