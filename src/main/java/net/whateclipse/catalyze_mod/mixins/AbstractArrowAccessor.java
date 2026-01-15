package net.whateclipse.catalyze_mod.mixins;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessor {
    @Invoker("getPickupItem")
    ItemStack invokeGetPickupItem();

    @Invoker("setPierceLevel")
    void invokeSetPierceLevel(byte level);

    @Accessor("piercingIgnoreEntityIds")
    IntOpenHashSet getPiercingIgnoreEntityIds();

    @Accessor("piercingIgnoreEntityIds")
    void setPiercingIgnoreEntityIds(IntOpenHashSet set);
}
