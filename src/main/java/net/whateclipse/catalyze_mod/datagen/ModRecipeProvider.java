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

                // Blazing Catalyst Recipes
                net.minecraft.world.item.Item catalyst = ModItems.BLAZING_CATALYST.get();
                net.minecraft.world.item.Item template = ModItems.COMBAT_TEMPLATE.get();

                // Swords
                net.minecraft.world.item.Item[] swords = {
                                Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD,
                                Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD
                };
                for (net.minecraft.world.item.Item weapon : swords) {
                        catalystSmithing(recipeOutput, template, weapon, catalyst, weapon,
                                        "blazing_" + net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(weapon)
                                                        .getPath());
                }

                // Axes
                net.minecraft.world.item.Item[] axes = {
                                Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE,
                                Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE
                };
                for (net.minecraft.world.item.Item weapon : axes) {
                        catalystSmithing(recipeOutput, template, weapon, catalyst, weapon,
                                        "blazing_" + net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(weapon)
                                                        .getPath());
                }

                // Others
                catalystSmithing(recipeOutput, template, ModItems.NETHERITE_SCYTHE.get(), catalyst,
                                ModItems.NETHERITE_SCYTHE.get(), "blazing_netherite_scythe");
                catalystSmithing(recipeOutput, template, Items.TRIDENT, catalyst, Items.TRIDENT, "blazing_trident");
                catalystSmithing(recipeOutput, template, Items.MACE, catalyst, Items.MACE, "blazing_mace");
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
