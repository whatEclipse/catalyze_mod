package net.whateclipse.catalyze_mod.events;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.whateclipse.catalyze_mod.Catalyze_mod;

@EventBusSubscriber(modid = Catalyze_mod.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.has(DataComponents.CUSTOM_DATA)) {
            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            if (customData != null && customData.contains("catalyze_mod")) {
                net.minecraft.nbt.CompoundTag modTag = customData.copyTag().getCompound("catalyze_mod");
                for (String key : modTag.getAllKeys()) {
                    if (modTag.getBoolean(key)) {
                        ChatFormatting color = switch (key) {
                            case "freezing" -> ChatFormatting.AQUA;
                            case "venomous" -> ChatFormatting.GREEN;
                            case "blinding" -> ChatFormatting.DARK_PURPLE;
                            default -> ChatFormatting.RED;
                        };
                        event.getToolTip()
                                .add(Component.translatable("tooltip.catalyze_mod." + key)
                                        .withStyle(color));
                    }
                }
            }
        }
    }
}
