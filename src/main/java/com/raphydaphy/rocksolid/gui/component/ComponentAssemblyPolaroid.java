package com.raphydaphy.rocksolid.gui.component;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.construction.compendium.ICompendiumRecipe;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentPolaroid;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ComponentAssemblyPolaroid extends ComponentPolaroid
{
	private static final ResourceName RES = RockSolid.createRes("gui.assembly_item_background");
	private static final ResourceName RES_HIGHLIGHTED = RockSolid.createRes("gui.assembly_item_background_highlighted");
	private static final ResourceName RES_SELECTED = ResourceName.intern("gui.construction.item_background_selected");

	public ComponentAssemblyPolaroid(Gui gui, ICompendiumRecipe recipe, boolean canConstruct)
	{
		super(gui, recipe, canConstruct);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y)
	{
		ResourceName res = this.recipe != null && this.isSelected ? RES_SELECTED : (this.isMouseOverPrioritized(game) ? RES_HIGHLIGHTED : RES);
		manager.getTexture(res).draw(x, y, this.width, this.height);

		if (this.recipe != null)
		{
			g.renderItemInGui(game, manager, this.getOutput(game), x + 4, y + 4, 1.0F, this.canConstruct ? Colors.WHITE : Colors.multiplyA(Colors.WHITE, 0.35F), false, false);
		} else
		{
			manager.getFont().drawString(x + 6, y + 5, "?", 0, 1, 0.5F, Colors.DARK_GRAY, Colors.NO_COLOR);
		}
	}

	public static ComponentAssemblyPolaroid getPolaroidButton(ICompendiumRecipe recipe, Gui gui, AbstractEntityPlayer player, boolean canConstruct)
	{
		return new ComponentAssemblyPolaroid(gui, recipe, canConstruct);
	}
}
