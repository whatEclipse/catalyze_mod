package net.whateclipse.catalyze_mod.mixins;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.whateclipse.catalyze_mod.effects.ModEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentItem.class)
public class TridentItemMixin {

    @Inject(method = "hurtEnemy", at = @At("HEAD"))
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker,
            CallbackInfoReturnable<Boolean> cir) {
        if (!stack.isEmpty()) {
            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            if (customData != null && customData.contains("catalyze_mod")) {
                net.minecraft.nbt.CompoundTag modTag = customData.copyTag().getCompound("catalyze_mod");
                if (modTag.getBoolean("blazing")) {
                    target.igniteForSeconds(5);
                }
                if (modTag.getBoolean("freezing")) {
                    target.setTicksFrozen(300);
                }
                if (modTag.getBoolean("venomous")) {
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0, false, false, true));
                }
                if (modTag.getBoolean("blinding")) {
                    target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 0, false, false, true));
                }
                if (modTag.getBoolean("bleeding")) {
                    if (!net.whateclipse.catalyze_mod.util.ModTags.EntityTypes.isImmuneToBleeding(target)) {
                        target.addEffect(new MobEffectInstance(ModEffects.BLEEDING, 100, 0, false, false, true));
                    }
                }
            }
        }
    }
}
