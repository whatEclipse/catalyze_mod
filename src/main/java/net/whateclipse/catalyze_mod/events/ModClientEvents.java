package net.whateclipse.catalyze_mod.events;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.whateclipse.catalyze_mod.util.TextUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.whateclipse.catalyze_mod.Catalyze_mod;
import net.whateclipse.catalyze_mod.effects.ModEffects;
import net.whateclipse.catalyze_mod.particles.ModParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import javax.annotation.Nonnull;

@EventBusSubscriber(modid = Catalyze_mod.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.has(DataComponents.CUSTOM_DATA)) {
            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            if (customData != null && customData.contains("catalyze_mod")) {
                if (Screen.hasShiftDown()) {
                    net.minecraft.nbt.CompoundTag modTag = customData.copyTag().getCompound("catalyze_mod");
                    for (String key : modTag.getAllKeys()) {
                        if (modTag.getBoolean(key)) {
                            int[] gradientColors = switch (key) {
                                case "blazing" -> new int[] { 0xF5B027, 0xF54927, 0xE6B553 };
                                case "freezing" -> new int[] { 0xDFF6FF, 0xC6E7FF, 0xA7DADC };
                                case "venomous" -> new int[] { 0x5B7F2A, 0x6FAE2E, 0x8BCF3F };
                                case "blinding" -> new int[] { 0x1B6F7A, 0x0B3442, 0x071B24 };
                                case "serrated" -> new int[] { 0x747474, 0xA30000, 0x470000 };
                                default -> null;
                            };

                            if (gradientColors != null) {
                                String translated = I18n.get("tooltip.catalyze_mod." + key);
                                for (String line : translated.split("\n")) {
                                    event.getToolTip()
                                            .add(TextUtil.createAnimatedGradientComponent(line, gradientColors));
                                }
                            } else {
                                event.getToolTip()
                                        .add(Component.translatable("tooltip.catalyze_mod." + key)
                                                .withStyle(ChatFormatting.RED));
                            }
                        }
                    }
                } else {
                    String translated = I18n.get("tooltip.catalyze_mod.hold_shift");
                    event.getToolTip().add(TextUtil.createAnimatedGradientComponent(translated,
                            0xD6B36A, 0xE4C98A, 0xC2A45D));
                }
            }
        }
    }

    @EventBusSubscriber(modid = Catalyze_mod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticleTypes.BLOOD_PARTICLE.get(), BloodParticle.Provider::new);
        }
    }

    public static class BloodParticle extends TextureSheetParticle {
        protected BloodParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites, double xSpeed,
                double ySpeed, double zSpeed) {
            super(level, x, y, z, xSpeed, ySpeed, zSpeed);
            this.xd = 0.0D;
            this.zd = 0.0D;
            this.yd = ySpeed;
            this.friction = 0.8F;
            this.gravity = 0.5F;
            this.quadSize *= 0.85F;
            this.lifetime = 10 + this.random.nextInt(10);
            this.pickSprite(sprites);
        }

        @Override
        public net.minecraft.client.particle.ParticleRenderType getRenderType() {
            return net.minecraft.client.particle.ParticleRenderType.PARTICLE_SHEET_OPAQUE;
        }

        public static class Provider implements ParticleProvider<net.minecraft.core.particles.SimpleParticleType> {
            private final SpriteSet sprites;

            public Provider(SpriteSet sprites) {
                this.sprites = sprites;
            }

            @Override
            @Nonnull
            public Particle createParticle(@Nonnull net.minecraft.core.particles.SimpleParticleType type,
                    @Nonnull ClientLevel level,
                    double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
                return new BloodParticle(level, x, y, z, this.sprites, xSpeed, ySpeed, zSpeed);
            }
        }
    }
}
