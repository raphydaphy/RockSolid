package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.recipe.GranulatorRecipe;
import com.raphydaphy.rocksolid.recipe.SeparatorRecipe;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.smelting.SmeltingRecipe;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ModRecipes
{
	public static void init()
	{
		Registries.SMELTING_REGISTRY.register(RockSolid.createRes("tin_smelting"), new SmeltingRecipe(new ItemUseInfo(ModItems.TIN_CLUSTER, 2), new ItemInstance(ModItems.TIN_INGOT), 300));
		Registries.SMELTING_REGISTRY.register(RockSolid.createRes("iron_smelting"), new SmeltingRecipe(new ItemUseInfo(ModItems.IRON_CLUSTER, 2), new ItemInstance(ModItems.IRON_INGOT), 300));

		Registries.SMELTING_REGISTRY.register(RockSolid.createRes("copper_grit_smelting"), new SmeltingRecipe(new ItemUseInfo(ModItems.COPPER_GRIT), new ItemInstance(GameContent.ITEM_COPPER_INGOT), 300));
		Registries.SMELTING_REGISTRY.register(RockSolid.createRes("tin_grit_smelting"), new SmeltingRecipe(new ItemUseInfo(ModItems.TIN_GRIT), new ItemInstance(ModItems.TIN_INGOT), 300));
		Registries.SMELTING_REGISTRY.register(RockSolid.createRes("iron_grit_smelting"), new SmeltingRecipe(new ItemUseInfo(ModItems.IRON_GRIT), new ItemInstance(GameContent.ITEM_COPPER_INGOT), 300));
		GranulatorRecipe.REGISTRY.add(new GranulatorRecipe(ModItems.TIN_GRIT, ModItems.TIN_INGOT));
		GranulatorRecipe.REGISTRY.add(new GranulatorRecipe(ModItems.IRON_GRIT, ModItems.IRON_INGOT));

		SeparatorRecipe.REGISTRY.add(new SeparatorRecipe(GameContent.RES_COPPER_RAW, ModItems.COPPER_GRIT));
		SeparatorRecipe.REGISTRY.add(new SeparatorRecipe(ModItems.TIN_CLUSTER, ModItems.TIN_GRIT));
		SeparatorRecipe.REGISTRY.add(new SeparatorRecipe(ModItems.IRON_CLUSTER, ModItems.IRON_GRIT));

		AlloySmelterRecipe.REGISTRY.add(new AlloySmelterRecipe(GameContent.ITEM_COPPER_INGOT, 3, ModItems.TIN_INGOT, 1, ModItems.BRONZE_INGOT, 4));
		AlloySmelterRecipe.REGISTRY.add(new AlloySmelterRecipe(ModItems.COPPER_GRIT, 3, ModItems.TIN_GRIT, 1, ModItems.BRONZE_GRIT, 4));
		AlloySmelterRecipe.REGISTRY.add(new AlloySmelterRecipe(ModItems.IRON_INGOT, 1, ModItems.COKE, 1, ModItems.STEEL_INGOT, 1));
	}
}
