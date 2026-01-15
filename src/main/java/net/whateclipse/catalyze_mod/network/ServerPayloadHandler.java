package net.whateclipse.catalyze_mod.network;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.whateclipse.catalyze_mod.network.packet.HasteAbilityPayload;

public class ServerPayloadHandler {
    public static void handleHasteAbility(HasteAbilityPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                ItemStack mainHandItem = player.getMainHandItem();
                CustomData customData = mainHandItem.get(DataComponents.CUSTOM_DATA);

                if (customData != null && customData.contains("catalyze_mod")) {
                    net.minecraft.nbt.CompoundTag modTag = customData.copyTag().getCompound("catalyze_mod");
                    if (modTag.getBoolean("haste")) {
                        long currentTime = player.level().getGameTime();
                        long cooldownEnd = modTag.getLong("haste_cooldown_end");

                        if (currentTime >= cooldownEnd) {
                            // Apply Haste III (Amplifier 2) for 20 seconds (400 ticks)
                            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 2));

                            // Set Cooldown (1 minute = 1200 ticks)
                            modTag.putLong("haste_cooldown_end", currentTime + 1200);

                            // Update Item NBT
                            net.minecraft.nbt.CompoundTag rootTag = customData.copyTag();
                            rootTag.put("catalyze_mod", modTag);
                            CustomData newCustomData = CustomData.of(rootTag);
                            mainHandItem.set(DataComponents.CUSTOM_DATA, newCustomData);

                            player.displayClientMessage(Component.translatable("message.catalyze_mod.haste_activated"),
                                    true);
                        } else {
                            long remainingSeconds = (cooldownEnd - currentTime) / 20;
                            player.displayClientMessage(
                                    Component.translatable("message.catalyze_mod.haste_cooldown", remainingSeconds),
                                    true);
                        }
                    }
                }
            }
        });
    }
}
