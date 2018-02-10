package com.raphydaphy.rocksolid.util;

import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public interface IConduit<T extends Tile>
{
	public boolean canConnect(IWorld world, Pos2 pos, TileState state);
}
