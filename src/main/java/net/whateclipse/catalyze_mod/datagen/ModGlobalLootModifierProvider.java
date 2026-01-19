package net.whateclipse.catalyze_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.whateclipse.catalyze_mod.Catalyze_mod;
import net.whateclipse.catalyze_mod.items.ModItems;
import net.whateclipse.catalyze_mod.loot.AddItemModifier;

import java.util.concurrent.CompletableFuture;

/**
 * Data generator for Global Loot Modifiers.
 * Adds dormant catalyst to ancient city chest loot tables.
 */
public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {

        public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
                super(output, registries, Catalyze_mod.MODID);
        }

        @Override
        protected void start() {
                // Add guaranteed dormant catalyst to the ancient city portal chest
                // (city_center)
                add("add_guaranteed_dormant_catalyst_to_portal_chest",
                                new AddItemModifier(
                                                new LootItemCondition[] {
                                                                LootTableIdCondition.builder(
                                                                                ResourceLocation.withDefaultNamespace(
                                                                                                "chests/ancient_city_city_center"))
                                                                                .build()
                                                },
                                                ModItems.DORMANT_CATALYST.get(),
                                                1));

                // Add dormant catalyst to other ancient city chests with a very rare chance
                add("add_rare_dormant_catalyst_to_ancient_city",
                                new AddItemModifier(
                                                new LootItemCondition[] {
                                                                // Only apply to ancient city chest loot table
                                                                LootTableIdCondition.builder(
                                                                                ResourceLocation.withDefaultNamespace(
                                                                                                "chests/ancient_city"))
                                                                                .build(),
                                                                // Much rarer chance (1%) as requested
                                                                net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
                                                                                .randomChance(0.01f).build()
                                                },
                                                ModItems.DORMANT_CATALYST.get(),
                                                1));
        }
}
