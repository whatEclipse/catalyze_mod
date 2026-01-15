package net.whateclipse.catalyze_mod.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.whateclipse.catalyze_mod.Catalyze_mod;

public record HasteAbilityPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<HasteAbilityPayload> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Catalyze_mod.MODID, "haste_ability"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HasteAbilityPayload> STREAM_CODEC = StreamCodec
            .unit(new HasteAbilityPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
