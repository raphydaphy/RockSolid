package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
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

public class GuiItemConduitConfig extends GuiContainer
{
	private final TileEntityItemConduit tile;
	private ConduitMode itemMode;
	private boolean isWhitelist;
	private ConduitSide editingSide;
	private int priority;

	public GuiItemConduitConfig(final AbstractEntityPlayer player, final TileEntityItemConduit tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void init(final IGameInstance game)
	{
		super.init(game);
		buildInitialGui(game);
	}

	public Boolean onButtonActivated(IGameInstance game, int button)
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
				((TileEntityItemConduit) tile).setPriority(editingSide, priority);

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
						.sendToServer(new PacketConduitUpdate(tile.x, tile.y, editingSide, itemMode, 1, true));
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
		this.components.add(new ComponentButton(this, this.x + 74, this.y + 50, 50, 18,
				() -> this.onButtonActivated(game, 0), "Up"));
		this.components.add(new ComponentButton(this, this.x + 74, this.y + 100, 50, 18,
				() -> this.onButtonActivated(game, 1), "Down"));
		this.components.add(new ComponentButton(this, this.x + 21, this.y + 75, 50, 18,
				() -> this.onButtonActivated(game, 2), "Left"));
		this.components.add(new ComponentButton(this, this.x + 125, this.y + 75, 50, 18,
				() -> this.onButtonActivated(game, 3), "Right"));
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
		isWhitelist = tile.getIsWhitelist(direction);
		this.components.clear();

		// Gui gui, int x, int y, int sizeX, int sizeY, boolean renderBox,
		// boolean selectable, boolean defaultActive, int maxLength, boolean
		// displayMaxLength)

		this.components.add(new ComponentButton(this, this.x + 26, this.y + 25, 50, 18,
				() -> this.onButtonActivated(game, 5), "Back"));

		if (((IConduit) tile).getSideMode(direction) != ConduitMode.DISABLED)
		{
			priority = ((TileEntityItemConduit) tile).getPriority(direction);
			this.components.add(new ComponentButton(this, this.x + 85, this.y, 30, 18,
					() -> this.onButtonActivated(game, 999), Integer.toString(priority)));
			this.components.add(
					new ComponentButton(this, this.x + 50, this.y, 30, 18, () -> this.onButtonActivated(game, 7), "-"));
			this.components.add(new ComponentButton(this, this.x + 120, this.y, 30, 18,
					() -> this.onButtonActivated(game, 6), "+"));
			this.components.add(new ComponentSlot(this,
					new ContainerSlot(tile.inventory, editingSide.getID(), this.x + 90, this.y + 26), 10, this.x + 90,
					this.y + 26));
			this.addPlayerInventory(player, this.x + 20, this.y + 75);

			if (isWhitelist)
			{
				this.components.add(new ComponentButton(this, this.x + 73, this.y + 50, 54, 18,
						() -> this.onButtonActivated(game, 8), "Whitelist"));
			} else
			{
				this.components.add(new ComponentButton(this, this.x + 73, this.y + 50, 54, 18,
						() -> this.onButtonActivated(game, 8), "Blacklist"));
			}
		}

		this.components.add(new ComponentButton(this, this.x + 123, this.y + 25, 50, 18,
				() -> this.onButtonActivated(game, 4), itemMode.getName(), itemMode.getDesc()));

	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiConduitConfig");
	}

}
