package net.whateclipse.catalyze_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.whateclipse.catalyze_mod.items.ModItems;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.NETHERITE_SCYTHE.get())
                .pattern("NNS")
                .pattern(" S ")
                .pattern("S  ")
                .define('S', Items.STICK)
                .define('N', Items.NETHERITE_INGOT)
                .unlockedBy("has_stick", has(Items.NETHERITE_INGOT)).save(recipeOutput);

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.COMBAT_TEMPLATE.get()),
                        Ingredient.of(Items.NETHERITE_SWORD),
                        Ingredient.of(ModItems.BLAZING_CATALYST.get()),
                        RecipeCategory.COMBAT,
                        ModItems.BLAZING_SWORD.get())
                .unlocks("has_blazing_catalyst", has(ModItems.BLAZING_CATALYST.get()))
                .save(recipeOutput, "blazing_sword_smithing");

    }
}
