package net.whateclipse.catalyze_mod.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BleedingEffect extends MobEffect {
    protected BleedingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(@javax.annotation.Nonnull net.minecraft.world.entity.LivingEntity entity,
            int amplifier) {
        if (net.whateclipse.catalyze_mod.util.ModTags.EntityTypes.isImmuneToBleeding(entity)) {
            entity.removeEffect(ModEffects.BLEEDING);
            return false;
        }

        // Damage: exponentially gets worse
        // Level 1 (amp 0): 1.0 (0.5 hearts)
        // Level 2 (amp 1): 1.5
        // Level 3 (amp 2): 2.25
        // Level 5 (amp 4): 5.06
        float damage = (float) Math.pow(1.5, amplifier);
        entity.hurt(entity.damageSources().magic(), damage);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {

        int interval = (int) (60.0 / Math.pow(1.5, amplifier));
        return duration % Math.max(1, interval) == 0;
    }
}
