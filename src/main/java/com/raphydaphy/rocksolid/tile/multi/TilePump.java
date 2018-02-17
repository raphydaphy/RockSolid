package com.raphydaphy.rocksolid.tile.multi;

import com.raphydaphy.rocksolid.container.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiPump;
import com.raphydaphy.rocksolid.render.PumpRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityPump;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TilePump extends MultiTileBase
{

	public TilePump()
	{
		super("pump", 6, new ToolInfo(ToolType.PICKAXE, 1));
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(2, 2);
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
	public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer)
	{
		return null;
	}

	@Override
	public boolean isFullTile()
	{
		return false;
	}

	@Override
	public boolean canPlaceInLayer(TileLayer layer)
	{
		return layer == TileLayer.MAIN;
	}

	@Override
	protected ITileRenderer<TilePump> createRenderer(IResourceName name)
	{
		return new PumpRenderer(name, this);
	}

	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		return layer == TileLayer.MAIN && this.isMainPos(x, y, state) ? new TileEntityPump(world, x, y, layer) : null;
	}

	@Override
	public boolean canProvideTileEntity()
	{
		return true;
	}

	public TileEntityPump getTE(IWorld world, TileState state, int x, int y)
	{
		Pos2 main = this.getMainPos(x, y, state);
		return world.getTileEntity(main.getX(), main.getY(), TileEntityPump.class);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player)
	{
		TileEntityPump te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiPump(player, te), new ContainerEmpty(player, 0, 41));
		return true;
	}

}
