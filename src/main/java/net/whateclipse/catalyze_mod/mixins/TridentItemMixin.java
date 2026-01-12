package net.whateclipse.catalyze_mod.mixins;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
                }
                if (modTag.getBoolean("blinding")) {
                    target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20));
                }
            }
        }
    }
}
