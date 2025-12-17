package net.whateclipse.catalyze_mod.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.whateclipse.catalyze_mod.Catalyze_mod;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Catalyze_mod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
