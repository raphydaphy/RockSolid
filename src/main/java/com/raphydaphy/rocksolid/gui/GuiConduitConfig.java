package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.api.util.TileEntityConduit;
import com.raphydaphy.rocksolid.network.PacketConduitUpdate;
import com.raphydaphy.rocksolid.tileentity.TileEntityItemConduit;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiConduitConfig extends GuiContainer
{
	private final TileEntityConduit<?> tile;
	private ConduitMode itemMode;
	private boolean isWhitelist;
	private ConduitSide editingSide;
	private int priority;

	public GuiConduitConfig(final AbstractEntityPlayer player, final TileEntityConduit<?> tile)
	{
		super(player, 198, 150);
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
			buildSingleGui(game, ConduitSide.getByID(button));
			return true;
		} else if (button == 4 || button == 6 || button == 7 || button == 8)
		{
			if (button == 4)
			{
				if (itemMode == ConduitMode.DISABLED)
				{
					itemMode = ConduitMode.OUTPUT;
				} else
				{
					itemMode = ConduitMode.getByID(itemMode.getID() + 1);
				}

				((IConduit) tile).setSideMode(editingSide, itemMode);
			} else if ((button == 6 || button == 7))
			{
				if (priority < 9999 && button == 6)
				{
					priority++;
				}
				if (priority > 1 && button == 7)
				{
					priority--;
				}
				((TileEntityConduit<?>) tile).setPriority(editingSide, priority);

			} else if (button == 8 && tile instanceof TileEntityItemConduit)
			{
				isWhitelist = !isWhitelist;
				((TileEntityItemConduit) tile).setIsWhitelist(editingSide, isWhitelist);
			}
			if (tile instanceof TileEntityItemConduit)
			{
				RockBottomAPI.getNet().sendToServer(
						new PacketConduitUpdate(tile.x, tile.y, editingSide, itemMode, priority, isWhitelist));
			} else
			{
				RockBottomAPI.getNet()
						.sendToServer(new PacketConduitUpdate(tile.x, tile.y, editingSide, itemMode, priority, true));
			}

			buildSingleGui(game, editingSide);
			return true;
		} else if (button == 5)
		{
			buildInitialGui(game);
			return true;
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

	protected void addSlotGrid(IInventory inventory, int start, int end, int xStart, int yStart, int width)
	{
		int x = xStart;
		int y = yStart;
		for (int i = start; i < end; i++)
		{
			this.components.add(new ComponentSlot(this, new ContainerSlot(inventory, i, x, y), 1, x, y));

			x += 20;
			if ((i + 1) % width == 0)
			{
				y += 20;
				x = xStart;
			}
		}
	}

	protected void addPlayerInventory(AbstractEntityPlayer player, int x, int y)
	{
		this.addSlotGrid(player.getInv(), 0, 8, x, y, 8);
		this.addSlotGrid(player.getInv(), 8, player.getInv().getSlotAmount(), x, y + 25, 8);
	}

	public void buildSingleGui(IGameInstance game, ConduitSide direction)
	{
		editingSide = direction;
		itemMode = ((IConduit) tile).getSideMode(direction);
		
		if (tile instanceof TileEntityItemConduit)
		{
			isWhitelist = ((TileEntityItemConduit)tile).getIsWhitelist(direction);
		}
		this.components.clear();

		// Gui gui, int x, int y, int sizeX, int sizeY, boolean renderBox,
		// boolean selectable, boolean defaultActive, int maxLength, boolean
		// displayMaxLength)

		this.components.add(new ComponentButton(this, 5, this.guiLeft + 26, this.guiTop + 25, 50, 18, "Back"));

		if (((IConduit) tile).getSideMode(direction) != ConduitMode.DISABLED)
		{
			priority = tile.getPriority(direction);
			this.components.add(
					new ComponentButton(this, 999, this.guiLeft + 85, this.guiTop, 30, 18, Integer.toString(priority)));
			this.components.add(new ComponentButton(this, 7, this.guiLeft + 50, this.guiTop, 30, 18, "-"));
			this.components.add(new ComponentButton(this, 6, this.guiLeft + 120, this.guiTop, 30, 18, "+"));
			
			if (tile instanceof TileEntityItemConduit)
			{
				this.components.add(new ComponentSlot(this,
					new ContainerSlot(((TileEntityItemConduit)tile).inventory, editingSide.getID(), this.guiLeft + 90, this.guiTop + 26), 10,
					this.guiLeft + 90, this.guiTop + 26));
			}
			this.addPlayerInventory(player, this.guiLeft + 20, this.guiTop + 75);

			if (isWhitelist)
			{
				this.components
						.add(new ComponentButton(this, 8, this.guiLeft + 73, this.guiTop + 50, 54, 18, "Whitelist"));
			} else
			{
				this.components
						.add(new ComponentButton(this, 8, this.guiLeft + 73, this.guiTop + 50, 54, 18, "Blacklist"));
			}
		}

		this.components.add(new ComponentButton(this, 4, this.guiLeft + 123, this.guiTop + 25, 50, 18,
				itemMode.getName(), itemMode.getDesc()));

	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiConduitConfig");
	}

}