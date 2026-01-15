package net.whateclipse.catalyze_mod.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.whateclipse.catalyze_mod.Catalyze_mod;
import net.whateclipse.catalyze_mod.network.packet.HasteAbilityPayload;
import net.whateclipse.catalyze_mod.util.ModKeyBindings;

@EventBusSubscriber(modid = Catalyze_mod.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ModKeyBindings.HASTE_ABILITY_KEY.consumeClick()) {
            PacketDistributor.sendToServer(new HasteAbilityPayload());
        }
    }
}
