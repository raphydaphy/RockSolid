package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.recipe.SeparatorRecipe;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.construction.smelting.SmeltingRecipe;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ModRecipes
{
	public static void init()
	{
		Registries.SMELTING_REGISTRY.register(RockSolid.createRes("tin_smelting"), new SmeltingRecipe(new ResUseInfo(ModResources.RES_TIN_RAW, 2), new ItemInstance(ModItems.TIN_INGOT), 200));
		Registries.SMELTING_REGISTRY.register(RockSolid.createRes("iron_smelting"), new SmeltingRecipe(new ResUseInfo(ModResources.RES_IRON_RAW, 2), new ItemInstance(ModItems.IRON_INGOT), 200));

		Registries.SMELTING_REGISTRY.register(RockSolid.createRes("copper_grit_smelting"), new SmeltingRecipe(new ResUseInfo(ModResources.RES_COPPER_CRUSHED), new ItemInstance(GameContent.ITEM_COPPER_INGOT), 150));
		Registries.SMELTING_REGISTRY.register(RockSolid.createRes("tin_grit_smelting"), new SmeltingRecipe(new ResUseInfo(ModResources.RES_TIN_CRUSHED), new ItemInstance(ModItems.TIN_INGOT), 150));
		Registries.SMELTING_REGISTRY.register(RockSolid.createRes("iron_grit_smelting"), new SmeltingRecipe(new ResUseInfo(ModResources.RES_IRON_CRUSHED), new ItemInstance(GameContent.ITEM_COPPER_INGOT), 150));

		SeparatorRecipe.REGISTRY.add(new SeparatorRecipe(GameContent.RES_COPPER_RAW, ModItems.COPPER_GRIT));
		SeparatorRecipe.REGISTRY.add(new SeparatorRecipe(ModResources.RES_TIN_RAW, ModItems.TIN_GRIT));
		SeparatorRecipe.REGISTRY.add(new SeparatorRecipe(ModResources.RES_IRON_RAW, ModItems.IRON_GRIT));

		AlloySmelterRecipe.REGISTRY.add(new AlloySmelterRecipe(GameContent.RES_COPPER_PROCESSED, 3, ModResources.RES_TIN_PROCESSED, 1, ModItems.BRONZE_INGOT, 4));
		AlloySmelterRecipe.REGISTRY.add(new AlloySmelterRecipe(ModResources.RES_COPPER_CRUSHED, 3, ModResources.RES_TIN_CRUSHED, 1, ModItems.BRONZE_GRIT, 4));
		AlloySmelterRecipe.REGISTRY.add(new AlloySmelterRecipe(ModResources.RES_IRON_PROCESSED, 1, ModResources.RES_COAL_PROCESSED, 1, ModItems.STEEL_INGOT, 1));
	}
}
