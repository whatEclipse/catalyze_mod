package net.whateclipse.catalyze_mod.mixins;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.whateclipse.catalyze_mod.effects.ModEffects;
import net.whateclipse.catalyze_mod.util.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class) // Look in ModTags.java for more info on the isImmuneToBleeding tag
public abstract class LivingEntityMixin {
    @Inject(method = "canBeAffected", at = @At("HEAD"), cancellable = true)
    private void onCanBeAffected(MobEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (effect.getEffect().equals(ModEffects.BLEEDING.get())) {
            if (ModTags.EntityTypes.isImmuneToBleeding((LivingEntity) (Object) this)) {
                cir.setReturnValue(false);
            }
        }
    }
}
