package com.raphydaphy.rocksolid.tile.conduit;

import com.raphydaphy.rocksolid.tileentity.TileEntityItemConduit;

import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileItemConduit extends TileConduit
{

	public TileItemConduit()
	{
		super("item_conduit");
	}


	@Override
	public boolean canConnectAbstract(IWorld world, TileEntity te, Pos2 pos, TileState state)
	{
		return (te != null && te.getInventory() != null);
	}
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityItemConduit(world, x, y, layer);
	}

}
