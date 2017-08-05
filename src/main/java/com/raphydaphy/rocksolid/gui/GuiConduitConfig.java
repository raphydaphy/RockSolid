package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.network.PacketConduitUpdate;
import com.raphydaphy.rocksolid.tileentity.TileEntityItemConduit;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiConduitConfig extends Gui
{
	private final TileEntity tile;
	// true = input false = output
	private int itemMode;
	private int editingSide;
	private int priority;

	public GuiConduitConfig(final AbstractEntityPlayer player, final TileEntity tile)
	{
		super(198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		buildInitialGui(game);
	}

	@Override
	public boolean onButtonActivated(IGameInstance game, int button)
	{
		// they are on the initial up/down/left/right screen
		if (button == 0 || button == 1 || button == 2 || button == 3)
		{
			buildSingleGui(game, button);
			return true;
		} else if (button == 4 || button == 6 || button == 7)
		{
			if (button == 4)
			{
				if (itemMode == 2)
				{
					itemMode = 0;
				} else
				{
					itemMode++;
				}

				((IConduit) tile).setSideMode(editingSide, itemMode);
			} else if ((button == 6 || button == 7) && tile instanceof TileEntityItemConduit)
			{
				if (priority < 9999 && button == 6)
				{
					priority++;
				}
				if (priority > 1 && button == 7)
				{
					priority--;
				}
				((TileEntityItemConduit) tile).setPriority(priority, editingSide);

			}
			if (tile instanceof TileEntityItemConduit)
			{
				RockBottomAPI.getNet().sendToServer(new PacketConduitUpdate(tile.x, tile.y, editingSide, itemMode,
						priority, ((TileEntityItemConduit) tile).getIsWhitelist(editingSide)));
			} else
			{
				RockBottomAPI.getNet()
						.sendToServer(new PacketConduitUpdate(tile.x, tile.y, editingSide, itemMode, 1, true));
			}

			buildSingleGui(game, editingSide);
			return true;
		} else if (button == 5)
		{
			buildInitialGui(game);
			return true;
		} else if (button == 8)
		{
			game.getGuiManager().closeGui();
		}
		return false;
	}

	public void buildInitialGui(IGameInstance game)
	{
		this.components.clear();
		this.components.add(new ComponentButton(this, 0, this.guiLeft + 74, this.guiTop + 50, 50, 18, "Up"));
		this.components.add(new ComponentButton(this, 1, this.guiLeft + 74, this.guiTop + 100, 50, 18, "Down"));
		this.components.add(new ComponentButton(this, 2, this.guiLeft + 21, this.guiTop + 75, 50, 18, "Left"));
		this.components.add(new ComponentButton(this, 3, this.guiLeft + 125, this.guiTop + 75, 50, 18, "Right"));
	}

	public void buildSingleGui(IGameInstance game, int direction)
	{
		editingSide = direction;
		this.components.clear();

		// Gui gui, int x, int y, int sizeX, int sizeY, boolean renderBox,
		// boolean selectable, boolean defaultActive, int maxLength, boolean
		// displayMaxLength)

		this.components.add(new ComponentButton(this, 5, this.guiLeft + 21, this.guiTop + 50, 50, 18, "Back"));

		if (tile instanceof TileEntityItemConduit && ((IConduit) tile).getSideMode(direction) != 2)
		{
			priority = ((TileEntityItemConduit) tile).getPriority(direction);
			this.components.add(new ComponentButton(this, 999, this.guiLeft + 80, this.guiTop + 25, 30, 18,
					Integer.toString(priority)));
			this.components.add(new ComponentButton(this, 7, this.guiLeft + 45, this.guiTop + 25, 30, 18, "-"));
			this.components.add(new ComponentButton(this, 6, this.guiLeft + 115, this.guiTop + 25, 30, 18, "+"));
			this.components.add(new ComponentButton(this, 8, this.guiLeft + 70, this.guiTop + 75, 50, 18, "Exit"));
		}

		if (((IConduit) tile).getSideMode(direction) == 0)
		{
			this.components.add(new ComponentButton(this, 4, this.guiLeft + 117, this.guiTop + 50, 50, 18, "Output",
					"Outputs contents into connected block."));
		} else if (((IConduit) tile).getSideMode(direction) == 1)
		{

			this.components.add(new ComponentButton(this, 4, this.guiLeft + 117, this.guiTop + 50, 50, 18, "Input",
					"Pulls contents into the conduit."));
		} else if (((IConduit) tile).getSideMode(direction) == 2)
		{
			this.components.add(new ComponentButton(this, 4, this.guiLeft + 117, this.guiTop + 50, 50, 18, "Disabled",
					"The conduit won't connect on this side."));
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidLib.makeRes("guiConduitConfig");
	}

}
