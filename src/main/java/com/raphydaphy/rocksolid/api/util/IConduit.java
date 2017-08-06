package com.raphydaphy.rocksolid.api.util;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;

import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;

// Should be implemented on any block that uses the ConduitRenderer system to connect the textures
public interface IConduit
{
	// Returns the side mode of the specified side
	ConduitMode getSideMode(ConduitSide side);

	// Should set the side mode for the specified side
	void setSideMode(ConduitSide side, ConduitMode mode);

	// Returns true if the block can connect to the specified adjacent block
	// type
	boolean canConnectTo(Pos2 pos, TileEntity tile);

	void setSync();
}
