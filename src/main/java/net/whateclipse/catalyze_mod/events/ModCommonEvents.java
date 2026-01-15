package net.whateclipse.catalyze_mod.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.whateclipse.catalyze_mod.Catalyze_mod;
import net.whateclipse.catalyze_mod.effects.ModEffects;
import net.whateclipse.catalyze_mod.particles.ModParticleTypes;

@EventBusSubscriber(modid = Catalyze_mod.MODID)
public class ModCommonEvents {

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            // Run on server to ensure all entities (mobs & players) are tracked
            if (!entity.level().isClientSide && entity.hasEffect(ModEffects.BLEEDING)) {
                MobEffectInstance effect = entity.getEffect(ModEffects.BLEEDING);
                if (effect != null && entity.level() instanceof ServerLevel serverLevel) {
                    int amplifier = effect.getAmplifier();
                    RandomSource random = entity.getRandom();

                    // Same density logic as before
                    int threshold = Math.max(1, 10 - amplifier * 2);
                    if (entity.tickCount % threshold == 0) {
                        int count = 1 + (amplifier / 2);
                        for (int i = 0; i < count; i++) {
                            double x = entity.getX() + (random.nextDouble() - 0.5D) * (entity.getBbWidth() * 0.2D);
                            double y = entity.getY() + random.nextDouble() * entity.getBbHeight();
                            double z = entity.getZ() + (random.nextDouble() - 0.5D) * (entity.getBbWidth() * 0.2D);

                            // Using count=0 trick to set exact velocity on client
                            serverLevel.sendParticles(ModParticleTypes.BLOOD_PARTICLE.get(),
                                    x, y, z,
                                    0, // count = 0 means dx,dy,dz are velocity
                                    0.0D, -0.05D, 0.0D, // Velocity: x, y, z
                                    1.0D // speed multiplier
                            );
                        }
                    }
                }
            }
        }
    }
}
