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

                catalystSmithing(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), Items.NETHERITE_SWORD,
                                ModItems.BLAZING_CATALYST.get(), Items.NETHERITE_SWORD, "flaming_netherite_sword");
                catalystSmithing(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.NETHERITE_SCYTHE.get(),
                                ModItems.BLAZING_CATALYST.get(), ModItems.NETHERITE_SCYTHE.get(),
                                "flaming_netherite_scythe");
        }

        private void catalystSmithing(RecipeOutput recipeOutput, net.minecraft.world.item.Item template,
                        net.minecraft.world.item.Item base, net.minecraft.world.item.Item addition,
                        net.minecraft.world.item.Item result, String recipeName) {
                Ingredient templateIng = Ingredient.of(template);
                Ingredient baseIng = Ingredient.of(base);
                Ingredient additionIng = Ingredient.of(addition);
                net.minecraft.world.item.ItemStack resultStack = new net.minecraft.world.item.ItemStack(result);

                net.whateclipse.catalyze_mod.recipes.CatalystSmithingRecipe recipe = new net.whateclipse.catalyze_mod.recipes.CatalystSmithingRecipe(
                                templateIng, baseIng, additionIng, resultStack);

                net.minecraft.resources.ResourceLocation id = net.minecraft.resources.ResourceLocation
                                .fromNamespaceAndPath(net.whateclipse.catalyze_mod.Catalyze_mod.MODID, recipeName);

                recipeOutput.accept(id, recipe, null);

        }
}
