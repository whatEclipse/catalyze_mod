package net.whateclipse.catalyze_mod.mixins;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ThrownTrident.class)
public abstract class ThrownTridentMixin extends AbstractArrow {

    protected ThrownTridentMixin(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    private boolean dealtDamage;

    @Inject(method = "tick", at = @At("HEAD"))
    private void checkPiercingTick(CallbackInfo ci) {
        if (!this.level().isClientSide && this.getPierceLevel() < 127) {
            ItemStack stack = ((AbstractArrowAccessor) this).invokeGetPickupItem();
            net.minecraft.world.item.component.CustomData customData = stack
                    .get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
            if (customData != null && customData.contains("catalyze_mod")) {
                net.minecraft.nbt.CompoundTag modTag = customData.copyTag().getCompound("catalyze_mod");
                if (modTag.getBoolean("piercing")) {
                    ((AbstractArrowAccessor) this).invokeSetPierceLevel((byte) 127);
                    // Initialize the piercing ignore set if null
                    if (((AbstractArrowAccessor) this).getPiercingIgnoreEntityIds() == null) {
                        ((AbstractArrowAccessor) this).setPiercingIgnoreEntityIds(new IntOpenHashSet(5));
                    }
                }
            }
        }
    }

    @Inject(method = "onHitEntity", at = @At("HEAD"), cancellable = true)
    private void checkPiercing(EntityHitResult result, CallbackInfo ci) {
        ItemStack stack = ((AbstractArrowAccessor) this).invokeGetPickupItem();
        net.minecraft.world.item.component.CustomData customData = stack
                .get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);

        if (customData != null && customData.contains("catalyze_mod")) {
            net.minecraft.nbt.CompoundTag modTag = customData.copyTag().getCompound("catalyze_mod");
            if (modTag.getBoolean("piercing")) {
                Entity target = result.getEntity();

                // Get vanilla's piercing ignore set
                IntOpenHashSet ignoreSet = ((AbstractArrowAccessor) this).getPiercingIgnoreEntityIds();
                if (ignoreSet == null) {
                    ignoreSet = new IntOpenHashSet(5);
                    ((AbstractArrowAccessor) this).setPiercingIgnoreEntityIds(ignoreSet);
                }

                // Skip entities we've already hit
                if (ignoreSet.contains(target.getId())) {
                    ci.cancel();
                    return;
                }

                // Add to vanilla's ignore list
                ignoreSet.add(target.getId());

                // Server-side logic for damage and effects
                if (!this.level().isClientSide) {
                    // Skip Endermen
                    if (target.getType() != EntityType.ENDERMAN) {
                        float damage = 8.0F;

                        if (this.level() instanceof ServerLevel serverLv) {
                            var registry = serverLv.registryAccess()
                                    .lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);
                            var impalingHolder = registry
                                    .getOrThrow(net.minecraft.world.item.enchantment.Enchantments.IMPALING);
                            net.minecraft.world.item.enchantment.ItemEnchantments enchants = stack.getOrDefault(
                                    net.minecraft.core.component.DataComponents.ENCHANTMENTS,
                                    net.minecraft.world.item.enchantment.ItemEnchantments.EMPTY);
                            int impalingLevel = enchants.getLevel(impalingHolder);
                            if (impalingLevel > 0) {
                                damage += (impalingLevel * 2.0F);
                            }
                        }

                        Entity owner = this.getOwner();
                        DamageSource source = this.damageSources().trident(this, owner == null ? this : owner);

                        if (target.hurt(source, damage)) {
                            if (target instanceof LivingEntity livingTarget) {
                                if (owner instanceof LivingEntity) {
                                    EnchantmentHelper.doPostAttackEffects(serverLevel(), livingTarget, source);
                                }
                                this.doKnockback(livingTarget, source);
                            }
                        }

                        this.playSound(net.minecraft.sounds.SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);

                        // Run Lightning Logic
                        manualChannelingLogic(target);
                    }
                }

                // ALWAYS cancel to prevent vanilla from stopping the trident
                ci.cancel();
            }
        }
    }

    @ModifyArg(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1)
    private float modifyVanillaTridentDamage(float originalDamage) {
        if (this.level() instanceof ServerLevel serverLevel) {
            ItemStack stack = ((AbstractArrowAccessor) this).invokeGetPickupItem();
            var registry = serverLevel.registryAccess()
                    .lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);
            var impalingHolder = registry.getOrThrow(net.minecraft.world.item.enchantment.Enchantments.IMPALING);

            net.minecraft.world.item.enchantment.ItemEnchantments enchants = stack.getOrDefault(
                    net.minecraft.core.component.DataComponents.ENCHANTMENTS,
                    net.minecraft.world.item.enchantment.ItemEnchantments.EMPTY);

            int impalingLevel = enchants.getLevel(impalingHolder);
            if (impalingLevel > 0) {
                return originalDamage + (impalingLevel * 2.0F);
            }
        }
        return originalDamage;
    }

    private void manualChannelingLogic(Entity target) {
        if (this.level() instanceof ServerLevel serverLevel) {
            ItemStack stack = ((AbstractArrowAccessor) this).invokeGetPickupItem();

            var registry = serverLevel.registryAccess()
                    .lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT);
            var channelingHolder = registry.getOrThrow(net.minecraft.world.item.enchantment.Enchantments.CHANNELING);

            net.minecraft.world.item.enchantment.ItemEnchantments enchants = stack.getOrDefault(
                    net.minecraft.core.component.DataComponents.ENCHANTMENTS,
                    net.minecraft.world.item.enchantment.ItemEnchantments.EMPTY);

            if (enchants.getLevel(channelingHolder) > 0) {
                boolean vanillaCondition = this.level().isThundering()
                        && this.level().canSeeSky(target.blockPosition());
                if (!vanillaCondition) {
                    BlockPos blockPos = target.blockPosition();
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
                    if (lightningBolt != null) {
                        lightningBolt.moveTo(Vec3.atBottomCenterOf(blockPos));
                        Entity owner = this.getOwner();
                        lightningBolt.setCause(owner instanceof ServerPlayer ? (ServerPlayer) owner : null);
                        serverLevel.addFreshEntity(lightningBolt);
                    }
                } else {
                    BlockPos blockPos = target.blockPosition();
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
                    if (lightningBolt != null) {
                        lightningBolt.moveTo(Vec3.atBottomCenterOf(blockPos));
                        Entity owner = this.getOwner();
                        lightningBolt.setCause(owner instanceof ServerPlayer ? (ServerPlayer) owner : null);
                        serverLevel.addFreshEntity(lightningBolt);
                    }
                }
            }
        }
    }

    @Inject(method = "onHitEntity", at = @At("RETURN"))
    private void manualChanneling(EntityHitResult result, CallbackInfo ci) {
        if (!ci.isCancelled()) {
            manualChannelingLogic(result.getEntity());
        }
    }

    private ServerLevel serverLevel() {
        return (ServerLevel) this.level();
    }

    @Inject(method = "onHitBlock", at = @At("HEAD"))
    private void setDealtDamageOnBlockHit(BlockHitResult result, CallbackInfo ci) {
        // For piercing tridents: set dealtDamage = true when hitting a block
        // This ensures Loyalty returns the trident after it stops
        ItemStack stack = ((AbstractArrowAccessor) this).invokeGetPickupItem();
        net.minecraft.world.item.component.CustomData customData = stack
                .get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);

        if (customData != null && customData.contains("catalyze_mod")) {
            net.minecraft.nbt.CompoundTag modTag = customData.copyTag().getCompound("catalyze_mod");
            if (modTag.getBoolean("piercing")) {
                this.dealtDamage = true;
            }
        }
    }
}
