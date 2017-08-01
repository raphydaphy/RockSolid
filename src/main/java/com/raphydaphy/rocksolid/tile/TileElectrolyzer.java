package com.raphydaphy.rocksolid.tile;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.api.gui.ContainerEmpty;
import com.raphydaphy.rocksolid.api.render.PoweredMultiTileRenderer;
import com.raphydaphy.rocksolid.gui.GuiElectrolyzer;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectrolyzer;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileElectrolyzer extends MultiTile
{
	private static final String name = "electrolyzer";
	private final IResourceName desc = RockBottomAPI.createRes(RockSolid.INSTANCE, "details." + name);

	public TileElectrolyzer()
	{
		super(RockSolidLib.makeRes(name));
		this.setHardness(15);
		this.addEffectiveTool(ToolType.PICKAXE, 1);
		this.register();
	}

	@Override
	protected ITileRenderer<MultiTile> createRenderer(final IResourceName name)
	{
		return new PoweredMultiTileRenderer(name, this);
	}

	@Override
	public int getLight(final IWorld world, final int x, final int y, final TileLayer layer)
	{
		TileEntity mainTile = RockSolidLib.getTileFromPos(x, y, world);
		if (mainTile != null && ((TileEntityElectrolyzer) mainTile).isActive())
		{
			return 30;
		}
		return 0;
	}

	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y)
	{
		return this.isMainPos(x, y, world.getState(x, y)) ? new TileEntityElectrolyzer(world, x, y) : null;
	}

	@Override
	public boolean canProvideTileEntity()
	{
		return true;
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player)
	{
		ItemInstance selected = player.getInv().get(player.getSelectedSlot());
		if (selected != null)
		{
			if (selected.getItem().equals(ModItems.bucket) && selected.getMeta() != 0)
			{
				return false;
			}
		}

		Pos2 main = this.getMainPos(x, y, world.getState(x, y));
		TileEntityElectrolyzer tile = world.getTileEntity(main.getX(), main.getY(), TileEntityElectrolyzer.class);

		if (tile != null)
		{
			player.openGuiContainer(new GuiElectrolyzer(player, tile), new ContainerEmpty(player));
			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{

		int startX = x - this.getMainX();
		int startY = y - this.getMainY();

		for (int addX = 0; addX < this.getWidth(); addX++)
		{
			for (int addY = 0; addY < this.getHeight(); addY++)
			{
				if (this.isStructurePart(addX, addY))
				{
					int theX = startX + addX;
					int theY = startY + addY;

					if (!world.getState(layer, theX, theY).getTile().canReplace(world, theX, theY, layer, this))
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return new boolean[][] { { true, true }, { true, true } };
	}

	@Override
	public int getWidth()
	{
		return 2;
	}

	@Override
	public int getHeight()
	{
		return 2;
	}

	@Override
	public int getMainX()
	{
		return 0;
	}

	@Override
	public int getMainY()
	{
		return 0;
	}

	@Override
	public BoundBox getBoundBox(final IWorld world, final int x, final int y)
	{
		return null;
	}

	@Override
	public boolean isFullTile()
	{
		return false;
	}

	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced)
	{
		super.describeItem(manager, instance, desc, isAdvanced);
		desc.addAll(manager.getFont().splitTextToLength(500, 1f, true, manager.localize(this.desc)));
	}
}