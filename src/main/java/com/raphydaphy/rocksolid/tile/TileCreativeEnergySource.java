package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.tileentity.TileEntityCreativeEnergySource;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;


public class TileCreativeEnergySource extends TileBase
{
	public TileCreativeEnergySource()
	{
		super("creative_energy_source", 8, new ToolInfo(ToolType.PICKAXE, 1));
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
	public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer)
	{
		return null;
	}

	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityCreativeEnergySource(world, x, y, layer);
	}

	@Override
	public boolean canProvideTileEntity()
	{
		return true;
	}
}