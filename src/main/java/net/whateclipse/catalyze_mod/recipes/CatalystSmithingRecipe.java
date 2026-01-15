package net.whateclipse.catalyze_mod.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.whateclipse.catalyze_mod.items.ModItems;
import net.minecraft.world.item.component.CustomData;

public class CatalystSmithingRecipe extends SmithingTransformRecipe {
    final Ingredient template;
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;

    public CatalystSmithingRecipe(Ingredient template, Ingredient base, Ingredient addition, ItemStack result) {
        super(template, base, addition, result);
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public Ingredient getTemplate() {
        return template;
    }

    public Ingredient getBase() {
        return base;
    }

    public Ingredient getAddition() {
        return addition;
    }

    public ItemStack getResult() {
        return result;
    }

    @Override
    public boolean isSpecial() {
        return true; // Use this if we want to denote it's special, though strictly not necessary for
                     // functionality typically
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries) {
        ItemStack base = input.base();
        ItemStack addition = input.addition();
        ItemStack template = input.template();

        // Check if logic matches
        if (this.isTemplateIngredient(template) && this.isBaseIngredient(base) && this.isAdditionIngredient(addition)) {
            String tagKey = null;
            if (addition.is(ModItems.BLAZING_CATALYST.get()))
                tagKey = "blazing";
            else if (addition.is(ModItems.FREEZING_CATALYST.get()))
                tagKey = "freezing";
            else if (addition.is(ModItems.BLINDING_CATALYST.get()))
                tagKey = "blinding";
            else if (addition.is(ModItems.VENOMOUS_CATALYST.get()))
                tagKey = "venomous";
            else if (addition.is(ModItems.SERRATED_CATALYST.get()))
                tagKey = "serrated";
            else if (addition.is(ModItems.BLOOD_REAPER_CATALYST.get()))
                tagKey = "blood_reaper";
            else if (addition.is(ModItems.PIERCING_CATALYST.get()))
                tagKey = "piercing";
            else if (addition.is(ModItems.HASTE_CATALYST.get()))
                tagKey = "haste";

            if (tagKey != null) {
                ItemStack resultStack = base.copy();

                // Add the custom data
                CompoundTag modTag = new CompoundTag();
                modTag.putBoolean(tagKey, true);

                CompoundTag rootTag = new CompoundTag();
                rootTag.put("catalyze_mod", modTag);

                // Overwrite existing catalyst data by removing it first
                CustomData existing = resultStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                resultStack.set(DataComponents.CUSTOM_DATA, existing.update(tag -> {
                    tag.remove("catalyze_mod");
                    tag.merge(rootTag);
                }));

                return resultStack;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CATALYST_SMITHING_SERIALIZER.get();
    }
}
