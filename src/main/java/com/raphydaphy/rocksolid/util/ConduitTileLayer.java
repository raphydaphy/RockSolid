package com.raphydaphy.rocksolid.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tile.conduit.TileConduit;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

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
		return tile != null && (tile instanceof TileConduit || tile.isAir());
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
			for (BoundBox box : getConduitBounds(game.getWorld(), tileXInt, tileYInt))
			{
				if (box.contains(tileX, tileY))
				{
					return true;
				}
			}
		}
		return false;
	}

	public List<BoundBox> getConduitBounds(IWorld world, int x, int y)
	{
		double pixel = 1d / 12d;

		List<BoundBox> boxes = new ArrayList<>();

		boxes.add(new BoundBox(4 * pixel, 4 * pixel, 8 * pixel, 8 * pixel).add(x, y));

		TileState state = world.getState(this, x, y);

		Map<Direction, BoundBox> subBoxes = new HashMap<>();

		subBoxes.put(Direction.UP, new BoundBox(4 * pixel, 8 * pixel, 8 * pixel, 1));
		subBoxes.put(Direction.DOWN, new BoundBox(4 * pixel, 0, 8 * pixel, 4 * pixel));
		subBoxes.put(Direction.LEFT, new BoundBox(0, 4 * pixel, 4 * pixel, 8 * pixel));
		subBoxes.put(Direction.RIGHT, new BoundBox(8 * pixel, 4 * pixel, 1, 8 * pixel));

		for (Direction dir : Direction.ADJACENT)
		{
			if (((TileConduit) state.getTile()).canConnect(world, new Pos2(x + dir.x, y + dir.y),
					world.getState(this, x + dir.x, y + dir.y), world.getState(x + dir.x, y + dir.y)))
			{
				if (subBoxes.containsKey(dir))
				{
					boxes.add(subBoxes.get(dir).copy().add(x, y));
				}
			}
		}
		return boxes;
	}

}
