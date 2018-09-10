package com.raphydaphy.rocksolid.recipe.category;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.recipe.AlloyingRecipe;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.construction.compendium.CompendiumCategory;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentIngredient;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;
import java.util.Set;

public class AlloyingCategory extends CompendiumCategory
{
	public AlloyingCategory()
	{
		super(RockSolid.createRes("alloying"));
	}

	@Override
	public final ResourceName getIcon(IGameInstance var1, IAssetManager var2, IRenderer var3)
	{
		return RockSolid.createRes("gui.alloying_icon");
	}

	@Override
	public final ResourceName getBackgroundPicture(Gui var1, IAssetManager var2)
	{
		return RockSolid.createRes("gui.alloying_page");
	}

	@Override
	public final int getMaxIngredientAmount(Gui var1, List var2)
	{
		return 2;
	}

	@Override
	public final Pos2 getIngredientPosition(Gui var1, ComponentIngredient var2, int var3)
	{
		return new Pos2(94 + var3 * 16, 64);
	}

	@Override
	public final Set<AlloyingRecipe> getRecipes()
	{
		return AlloyingRecipe.REGISTRY.values();
	}

	@Override
	public final boolean shouldDisplay(AbstractEntityPlayer player)
	{
		return true;
	}
}
