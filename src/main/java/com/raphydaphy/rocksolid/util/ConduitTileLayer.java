package com.raphydaphy.rocksolid.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raphydaphy.rocksolid.RockSolid;

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
		// TODO: set this back to 5 when new RB is released
		super(RockSolid.createRes("conduit_layer"), 5, -20);
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
		return tile != null && (tile instanceof IConduit || tile.isAir());
	}

	@Override
	public boolean forceForegroundRender()
	{
		return false;
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
		Pos2 mousedTile = new Pos2((int) game.getRenderer().getMousedTileX(),
				(int) game.getRenderer().getMousedTileY());
		boolean mouseInTile = false;
		if (game.getWorld().getState(this, mousedTile.getX(), mousedTile.getY()).getTile() instanceof IConduit)
		{
			for (BoundBox box : getConduitBounds(game.getWorld(), mousedTile.getX(), mousedTile.getY()))
			{
				if (box.contains(game.getRenderer().getMousedTileX(), game.getRenderer().getMousedTileY()))
				{
					mouseInTile = true;
					break;
				}
			}
		}
		
		return mouseInTile || true;
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
			if (((IConduit<?>) state.getTile()).canConnect(world, new Pos2(x + dir.x, y + dir.y), world.getState(this, x + dir.x, y + dir.y)))
			{
				if (subBoxes.containsKey(dir))
				{
					boxes.add(subBoxes.get(dir).add(x, y));
				}
			}
		}
		return boxes;
	}

}
