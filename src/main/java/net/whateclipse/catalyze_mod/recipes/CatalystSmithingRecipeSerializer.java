package net.whateclipse.catalyze_mod.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CatalystSmithingRecipeSerializer implements RecipeSerializer<CatalystSmithingRecipe> {
    public static final MapCodec<CatalystSmithingRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Ingredient.CODEC.fieldOf("template").forGetter(CatalystSmithingRecipe::getTemplate),
                    Ingredient.CODEC.fieldOf("base").forGetter(CatalystSmithingRecipe::getBase),
                    Ingredient.CODEC.fieldOf("addition").forGetter(CatalystSmithingRecipe::getAddition),
                    ItemStack.STRICT_CODEC.fieldOf("result").forGetter(CatalystSmithingRecipe::getResult))
                    .apply(instance, CatalystSmithingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CatalystSmithingRecipe> STREAM_CODEC = StreamCodec
            .composite(
                    Ingredient.CONTENTS_STREAM_CODEC, CatalystSmithingRecipe::getTemplate,
                    Ingredient.CONTENTS_STREAM_CODEC, CatalystSmithingRecipe::getBase,
                    Ingredient.CONTENTS_STREAM_CODEC, CatalystSmithingRecipe::getAddition,
                    ItemStack.STREAM_CODEC, CatalystSmithingRecipe::getResult,
                    CatalystSmithingRecipe::new);

    @Override
    public MapCodec<CatalystSmithingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CatalystSmithingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
