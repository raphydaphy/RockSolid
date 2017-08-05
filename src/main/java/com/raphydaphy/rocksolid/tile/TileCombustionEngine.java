package com.raphydaphy.rocksolid.tile;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.gui.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiCombustionEngine;
import com.raphydaphy.rocksolid.render.CombustionEngineRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityCombustionEngine;
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

public class TileCombustionEngine extends MultiTile
{
	private static final String name = "combustionEngine";
	private final IResourceName desc = RockBottomAPI.createRes(RockSolid.INSTANCE, "details." + name);

	public TileCombustionEngine()
	{
		super(RockSolidLib.makeRes(name));
		this.setHardness(25);
		this.addEffectiveTool(ToolType.PICKAXE, 1);
		this.register();
	}

	@Override
	protected ITileRenderer<TileCombustionEngine> createRenderer(final IResourceName name)
	{
		return new CombustionEngineRenderer(name, this);
	}

	@Override
	public boolean canProvideTileEntity()
	{
		return true;
	}

	@Override
	public TileEntity provideTileEntity(final IWorld world, final int x, final int y)
	{
		return this.isMainPos(x, y, world.getState(x, y)) ? new TileEntityCombustionEngine(world, x, y) : null;
	}

	@Override
	public int getLight(final IWorld world, final int x, final int y, final TileLayer layer)
	{
		/*
		 * if (this.isMainPos(x, y, world.getState(x, y))) { final
		 * TileEntityQuarry tile = world.getTileEntity(x, y,
		 * TileEntityQuarry.class); if (tile != null && tile.isActive()) {
		 * return 50; } }
		 */
		return 0;
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player)
	{
		ItemInstance selected = player.getInv().get(player.getSelectedSlot());
		if (selected != null)
		{
			if (selected.getItem().equals(RockSolidContent.bucket) && selected.getMeta() != 0)
			{
				return false;
			}
		}

		final Pos2 main = this.getMainPos(x, y, world.getState(x, y));
		final TileEntityCombustionEngine tile = world.getTileEntity(main.getX(), main.getY(),
				TileEntityCombustionEngine.class);
		if (tile != null)
		{
			player.openGuiContainer(new GuiCombustionEngine(player, tile), new ContainerEmpty(player));
			return true;
		}
		return false;
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
	public BoundBox getBoundBox(final IWorld world, final int x, final int y)
	{
		return null;
	}

	@Override
	public boolean isFullTile()
	{
		return false;
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true } };
	}

	@Override
	public int getWidth()
	{
		return 3;
	}

	@Override
	public int getHeight()
	{
		return 3;
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
	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced)
	{
		super.describeItem(manager, instance, desc, isAdvanced);
		desc.addAll(manager.getFont().splitTextToLength(500, 1f, true, manager.localize(this.desc)));
	}
}