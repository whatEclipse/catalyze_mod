package net.whateclipse.catalyze_mod.mixins;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.whateclipse.catalyze_mod.effects.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class SwordItemMixin {

    @Inject(method = "hurtEnemy", at = @At("HEAD"))
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker,
            CallbackInfoReturnable<Boolean> cir) {
        if (!stack.isEmpty()) {
            // Check for the custom NBT/DataComponent
            // In newer NeoForge/MC versions, direct NBT access is discouraged in favor of
            // DataComponents,
            // but assuming we are looking for a simple persistent data tag for now.
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
                if (modTag.getBoolean("serrated")) {
                    if (!net.whateclipse.catalyze_mod.util.ModTags.EntityTypes.isImmuneToBleeding(target)) {
                        int armorPieces = 0;
                        for (ItemStack armor : target.getArmorSlots()) {
                            if (!armor.isEmpty())
                                armorPieces++;
                        }
                        float chance = 0.30f;
                        if (armorPieces == 1)
                            chance -= 0.05f;
                        else if (armorPieces == 2)
                            chance -= 0.12f;
                        else if (armorPieces == 3)
                            chance -= 0.20f;
                        else if (armorPieces == 4)
                            chance -= 0.28f;

                        // Apply Level 1 Bleeding
                        target.addEffect(new MobEffectInstance(ModEffects.BLEEDING, 100, 0, false, false, true));
                        // 30% base chance (minus armor reduction) to apply Level 2 instead
                        if (Math.random() < chance) {
                            target.addEffect(new MobEffectInstance(ModEffects.BLEEDING, 100, 1, false, false, true));
                        }
                    }
                }
            }
        }
    }
}
