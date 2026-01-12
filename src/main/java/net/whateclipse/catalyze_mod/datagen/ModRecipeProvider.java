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

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SERRATED_CATALYST.get())
                                .pattern("F F")
                                .pattern(" S ")
                                .pattern("F F")
                                .define('F', Items.FLINT)
                                .define('S', Items.SPIDER_EYE)
                                .unlockedBy("has_flint", has(Items.FLINT))
                                .unlockedBy("has_spider_eye", has(Items.SPIDER_EYE))
                                .save(recipeOutput);

                addCatalystRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.BLAZING_CATALYST.get(),
                                "blazing");
                addCatalystRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.FREEZING_CATALYST.get(),
                                "freezing");
                addCatalystRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.BLINDING_CATALYST.get(),
                                "blinding");
                addCatalystRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.VENOMOUS_CATALYST.get(),
                                "venomous");
                addCatalystRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.SERRATED_CATALYST.get(),
                                "serrated");
        }

        private void addCatalystRecipes(RecipeOutput recipeOutput, net.minecraft.world.item.Item template,
                        net.minecraft.world.item.Item catalyst, String prefix) {
                // Swords
                net.minecraft.world.item.Item[] swords = {
                                Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD,
                                Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD
                };
                for (net.minecraft.world.item.Item weapon : swords) {
                        catalystSmithing(recipeOutput, template, weapon, catalyst, weapon,
                                        prefix + "_" + net.minecraft.core.registries.BuiltInRegistries.ITEM
                                                        .getKey(weapon)
                                                        .getPath());
                }

                net.minecraft.world.item.Item[] axes = {
                                Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE,
                                Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE
                };
                for (net.minecraft.world.item.Item weapon : axes) {
                        catalystSmithing(recipeOutput, template, weapon, catalyst, weapon,
                                        prefix + "_" + net.minecraft.core.registries.BuiltInRegistries.ITEM
                                                        .getKey(weapon)
                                                        .getPath());
                }

                catalystSmithing(recipeOutput, template, ModItems.NETHERITE_SCYTHE.get(), catalyst,
                                ModItems.NETHERITE_SCYTHE.get(), prefix + "_netherite_scythe");
                catalystSmithing(recipeOutput, template, Items.TRIDENT, catalyst, Items.TRIDENT, prefix + "_trident");
                catalystSmithing(recipeOutput, template, Items.MACE, catalyst, Items.MACE, prefix + "_mace");
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
