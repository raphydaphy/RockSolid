package com.raphydaphy.rocksolid.tile;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.GuiNuclearReactor;
import com.raphydaphy.rocksolid.gui.container.ContainerNuclearReactor;
import com.raphydaphy.rocksolid.render.NuclearReactorRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.Entity;
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

public class TileNuclearReactor extends MultiTile
{
	private static final String name = "nuclearReactor";
	private final IResourceName desc = RockBottomAPI.createRes(RockSolid.INSTANCE, "details." + name);

	public TileNuclearReactor()
	{
		super(RockSolidLib.makeRes(name));
		this.setHardness(15);
		this.addEffectiveTool(ToolType.PICKAXE, 1);
		this.register();
	}

	@Override
	protected ITileRenderer<TileNuclearReactor> createRenderer(final IResourceName name)
	{
		return new NuclearReactorRenderer(name, this);
	}

	@Override
	public int getLight(final IWorld world, final int x, final int y, final TileLayer layer)
	{
		return 30;
	}

	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y)
	{
		return this.isMainPos(x, y, world.getState(x, y)) ? new TileEntityNuclearReactor(world, x, y) : null;
	}

	@Override
	public boolean canProvideTileEntity()
	{
		return true;
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
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player)
	{
		TileEntity tile = RockSolidLib.getTileFromPos(x, y, world);

		if (tile != null)
		{
			if (tile instanceof TileEntityNuclearReactor)
			{
				player.openGuiContainer(new GuiNuclearReactor(player, (TileEntityNuclearReactor) tile),
						new ContainerNuclearReactor(player, (TileEntityNuclearReactor) tile));
				return true;
			}
		}
		return false;
	}

	@Override
	public void onDestroyed(final IWorld world, final int x, final int y, final Entity destroyer, final TileLayer layer,
			final boolean forceDrop)
	{
		super.onDestroyed(world, x, y, destroyer, layer, forceDrop);
		if (!RockBottomAPI.getNet().isClient())
		{
			final Pos2 main = this.getMainPos(x, y, world.getState(x, y));
			final TileEntityNuclearReactor tile = world.getTileEntity(main.getX(), main.getY(),
					TileEntityNuclearReactor.class);
			if (tile != null)
			{
				tile.dropInventory(tile.inventory);
			}
		}
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true },
				{ true, true, true } };
	}

	@Override
	public int getWidth()
	{
		return 3;
	}

	@Override
	public int getHeight()
	{
		return 4;
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

	/*
	 * if youu are brave
	 * 
	 * @Override public void updateRandomlyForRendering(IWorld world, int x, int
	 * y, TileLayer layer, AbstractEntityPlayer player) { if (this.isMainPos(x,
	 * y, world.getState(x, y))) { for (int i =0; i < 30; i++) {
	 * RockBottomAPI.getGame().getParticleManager().addSmokeParticle(
	 * RockBottomAPI.getGame().getWorld(), x+ 0.25 + Util.RANDOM.nextFloat(), y
	 * + 0.5 + (Util.RANDOM.nextFloat() / 10),0, -0,
	 * RockBottomAPI.getGame().getWorldScale() * 0.0005f); } } }
	 */

	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced)
	{
		super.describeItem(manager, instance, desc, isAdvanced);
		desc.addAll(manager.getFont().splitTextToLength(500, 1f, true, manager.localize(this.desc)));
	}
}
