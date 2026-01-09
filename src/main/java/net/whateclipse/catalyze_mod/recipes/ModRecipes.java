package net.whateclipse.catalyze_mod.recipes;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.catalyze_mod.Catalyze_mod;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, Catalyze_mod.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, CatalystSmithingRecipeSerializer> CATALYST_SMITHING_SERIALIZER = RECIPE_SERIALIZERS
            .register("catalyst_smithing", CatalystSmithingRecipeSerializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
