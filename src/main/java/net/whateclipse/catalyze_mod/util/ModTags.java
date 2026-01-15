package net.whateclipse.catalyze_mod.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.whateclipse.catalyze_mod.Catalyze_mod;

public class ModTags {
    public static class Blocks {
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> IMMUNE_TO_BLEEDING = tag("immune_to_bleeding");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(Catalyze_mod.MODID, name));
        }


        public static boolean isImmuneToBleeding(LivingEntity entity) {
            EntityType<?> type = entity.getType();

            // Hardcoded fallbacks for guaranteed immunity to bleeding
            if (type == EntityType.IRON_GOLEM ||
                    type == EntityType.SNOW_GOLEM ||
                    type == EntityType.SHULKER ||
                    type == EntityType.BLAZE ||
                    type == EntityType.BREEZE ||
                    type == EntityType.MAGMA_CUBE ||
                    type == EntityType.SLIME ||
                    type == EntityType.GUARDIAN ||
                    type == EntityType.ELDER_GUARDIAN ||
                    type == EntityType.VEX ||
                    type == EntityType.ALLAY) {
                return true;
            }

            // Data-driven tag check
            return entity.getType().is(IMMUNE_TO_BLEEDING);
        }
    }
}
