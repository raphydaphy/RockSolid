package com.raphydaphy.rocksolid.tile.layer;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tile.conduit.TileConduit;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Map;

public class ConduitTileLayer extends TileLayer
{

	public ConduitTileLayer()
	{
		super(RockSolid.createRes("conduit_layer"), 5, 5);
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
		return tile != null && (tile.isAir() || tile instanceof TileConduit);
	}

	@Override
	public boolean canCollide(MovableWorldObject object)
	{
		return false;
	}

	@Override
	public boolean isVisible(IGameInstance game, AbstractEntityPlayer player, IChunk chunk, int x, int y,
			boolean isRenderingForeground)
	{
		// TODO: wrench mode disables render
		return true;
	}

	@Override
	public boolean canEditLayer(IGameInstance game, AbstractEntityPlayer player)
	{
		double tileX = game.getRenderer().getMousedTileX();
		double tileY = game.getRenderer().getMousedTileY();

		int tileXInt = (int) Math.floor(tileX);
		int tileYInt = (int) Math.floor(tileY);

		TileState state = game.getWorld().getState(this, tileXInt, tileYInt);

		if (state != null && state.getTile() instanceof TileConduit)
		{
			for (Map.Entry<Direction, BoundBox> entry : TileConduit
					.getConduitBounds(game.getWorld(), tileXInt, tileYInt).entrySet())
			{
				if (entry.getValue().add(tileXInt, tileYInt).contains(tileX, tileY))
				{
					return true;
				}
			}
		}
		return false;
	}

}
