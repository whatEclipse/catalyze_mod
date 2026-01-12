package net.whateclipse.catalyze_mod.mixins;

import net.minecraft.world.effect.MobEffectInstance;
import net.whateclipse.catalyze_mod.effects.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectInstance.class)
public class MobEffectInstanceMixin {

    @Inject(method = "isVisible", at = @At("RETURN"), cancellable = true)
    private void catalyze_mod$hideBleedingParticles(CallbackInfoReturnable<Boolean> cir) {
        MobEffectInstance instance = (MobEffectInstance) (Object) this;
        if (instance.getEffect().value() == ModEffects.BLEEDING.get()) {
            cir.setReturnValue(false);
        }
    }
}
