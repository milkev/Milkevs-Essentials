package net.milkev.milkevsessentials.common.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.milkev.milkevsessentials.common.MilkevsEssentials;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MilkevsEssentialsRecipeGenerator extends FabricRecipeProvider {
    
    MilkevsEssentialsRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        MilkevsEssentials.recipesShaped.iterator().forEachRemaining(e -> e
                .criterion(FabricRecipeProvider.hasItem(Items.AIR), FabricRecipeProvider.conditionsFromItem(Items.AIR))
                .offerTo(recipeExporter));
        
        MilkevsEssentials.recipesSmelt.forEach((e, v) -> 
                RecipeProvider.offerSmelting(recipeExporter, e, RecipeCategory.MISC, v, 0.45f, 300, "misc"));
    }
}
