package net.whateclipse.catalyze_mod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.whateclipse.catalyze_mod.Catalyze_mod;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Catalyze_mod.MODID, bus = EventBusSubscriber.Bus.MOD, value = net.neoforged.api.distmarker.Dist.CLIENT)
public class ModKeyBindings {
    public static final String KEY_CATEGORY_CATALYZE = "key.category.catalyze_mod.catalyze";
    public static final String KEY_HASTE_ABILITY = "key.catalyze_mod.haste_ability";

    public static final KeyMapping HASTE_ABILITY_KEY = new KeyMapping(
            KEY_HASTE_ABILITY,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            KEY_CATEGORY_CATALYZE);

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(HASTE_ABILITY_KEY);
    }
}
