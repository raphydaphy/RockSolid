package com.raphydaphy.rocksolid.gui.component;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.construction.compendium.ICompendiumRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;
import java.util.List;

public class ComponentAssemblyIngredient extends GuiComponent
{
	private static final ResourceName RES = RockSolid.createRes("gui.assembly_ingredient_background");

	private final boolean hasItem;
	private final List<ItemInstance> inputs;

	public ComponentAssemblyIngredient(Gui gui, boolean hasItem, List<ItemInstance> inputs)
	{
		super(gui, 0, 0, 14, 14);
		this.hasItem = hasItem;
		this.inputs = inputs;
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y)
	{
		ItemInstance input = this.getInput(game);
		manager.getTexture(RES).draw(x, y, this.width, this.height);
		g.renderItemInGui(game, manager, input, x + 2, y + 2, 1.0F, this.hasItem ? Colors.WHITE : Colors.multiplyA(Colors.WHITE, 0.35F));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y)
	{
		if (!this.inputs.isEmpty() && this.isMouseOver(game))
		{
			ItemInstance instance = this.getInput(game);
			g.drawHoverInfoAtMouse(game, manager, false, 200, instance.getDisplayName() + " x" + instance.getAmount());
		}
	}

	private ItemInstance getInput(IGameInstance game)
	{
		return this.inputs.get((game.getTotalTicks() / Constants.TARGET_TPS) % this.inputs.size());
	}

	@Override
	public boolean shouldDoFingerCursor(IGameInstance game)
	{
		return false;
	}

	@Override
	public ResourceName getName()
	{
		return ResourceName.intern("ingredient");
	}

	@Override
	public boolean onMouseAction(IGameInstance game, int button, float x, float y)
	{
		return this.isMouseOver(game);
	}

	public static List<ComponentAssemblyIngredient> getIngredientButtons(ICompendiumRecipe recipe, Gui gui, AbstractEntityPlayer player)
	{
		List<ComponentAssemblyIngredient> ingredients = new ArrayList<>();
		for (IUseInfo info : recipe.getInputs())
		{
			ingredients.add(new ComponentAssemblyIngredient(gui, player.getInv().containsResource(info), info.getItems()));
		}
		return ingredients;
	}
}