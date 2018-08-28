package com.raphydaphy.rocksolid.tile.layer;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tile.TileTempshiftPlate;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TempshiftTileLayer extends TileLayer
{

	public TempshiftTileLayer()
	{
		super(RockSolid.createRes("tempshift_layer"), -8, -8);
		this.register();
	}

	@Override
	public boolean canHoldTileEntities()
	{
		return true;
	}

	@Override
	public boolean canTileBeInLayer(IWorld world, int x, int y, Tile tile)
	{
		return tile != null && (tile.isAir() || tile instanceof TileTempshiftPlate);
	}

	@Override
	public boolean canCollide(MovableWorldObject object)
	{
		return false;
	}

	@Override
	public boolean canEditLayer(IGameInstance game, AbstractEntityPlayer player)
	{
		return true;
	}

}
