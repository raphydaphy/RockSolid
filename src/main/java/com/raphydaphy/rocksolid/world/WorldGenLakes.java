package com.raphydaphy.rocksolid.world;

import java.util.Random;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.FluidTile;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class WorldGenLakes implements IWorldGenerator
{

	@Override
	public boolean shouldGenerate(IWorld world, IChunk chunk)
	{
		Random rand = new Random(world.getSeed());
		if (chunk.getGridY() == 0 && chunk.getGridX() != 0)
		{
			return rand.nextInt(6) == 2;
		}

		return false;
	}

	@Override
	public void generate(IWorld world, IChunk chunk)
	{
		Random rand = new Random(world.getSeed());

		int chunkMapSizeX = 32;
		int chunkMapSizeY = 32;

		int startX = -10;
		int startY = -10;

		int fluidStart = 1 - rand.nextInt(3);

		int type = rand.nextInt(9);

		boolean[][] terrain = new boolean[chunkMapSizeX][chunkMapSizeY];

		for (int x = 0; x < chunkMapSizeX; x++)
		{
			for (int y = 0; y < chunkMapSizeY; y++)
			{
				if (rand.nextFloat() > 0.5f)
				{
					terrain[x][y] = true;
				}
			}
		}
		// 3 steps was good
		for (int step = 0; step < 5; step++)
		{
			terrain = doSimulationStep(terrain).clone();
		}

		for (int x = 0; x < 32; x++)
		{
			for (int y = 0; y < 32; y++)
			{
				Tile thisTile = world.getState(chunk.getX() + x + startX, chunk.getY() + y + startY).getTile();
				if (thisTile == GameContent.TILE_LEAVES || thisTile == GameContent.TILE_LOG)
				{
					world.setState(chunk.getX() + x + startX, chunk.getY() + y + startY,
							GameContent.TILE_AIR.getDefState());
				}
			}
		}
		for (int x = 0; x < chunkMapSizeX; x++)
		{
			for (int y = 0; y < chunkMapSizeY; y++)
			{
				if (!terrain[x][y])
				{
					if (world.getState(chunk.getX() + x + startX, chunk.getY() + y + startY)
							.getTile() != GameContent.TILE_AIR)
					{
						if (chunk.getY() + y + startY < fluidStart)
						{
							if (type < 6)
							{
								world.setState(TileLayer.MAIN, chunk.getX() + x + startX, chunk.getY() + y + startY,
										RockSolidContent.FLUID.getDefState().prop(FluidTile.fluidLevel, 12)
												.prop(FluidTile.fluidType, Fluid.WATER));

								if (chunk.getY() + y + startY == fluidStart - 7)
								{
									if (rand.nextBoolean())
									{
										world.setState(chunk.getX() + x + startX, chunk.getY() + y + startY,
												RockSolidContent.clay.getDefState());
									}
								} else if (chunk.getY() + y + startY < fluidStart - 7)
								{
									world.setState(chunk.getX() + x + startX, chunk.getY() + y + startY,
											RockSolidContent.clay.getDefState());
								}
							} else
							{
								world.setState(TileLayer.MAIN, chunk.getX() + x + startX, chunk.getY() + y + startY,
										RockSolidContent.FLUID.getDefState().prop(FluidTile.fluidLevel, 12)
												.prop(FluidTile.fluidType, Fluid.OIL));
							}
						} else
						{
							world.setState(TileLayer.MAIN, chunk.getX() + x + startX, chunk.getY() + y + startY,
									GameContent.TILE_AIR.getDefState());
							world.setState(TileLayer.BACKGROUND, chunk.getX() + x + startX, chunk.getY() + y + startY,
									GameContent.TILE_AIR.getDefState());
						}

					}
				}
			}
		}
	}

	public boolean[][] doSimulationStep(boolean[][] oldMap)
	{
		boolean[][] newMap = new boolean[oldMap.length][oldMap[0].length];

		int deathLimit = 4;
		int birthLimit = 4;
		// Loop over each row and column of the map
		for (int x = 0; x < oldMap.length; x++)
		{
			for (int y = 0; y < oldMap[0].length; y++)
			{
				int nbs = countAliveNeighbours(oldMap, x, y);
				// The new value is based on our simulation rules
				// First, if a cell is alive but has too few neighbours, kill
				// it.
				if (oldMap[x][y])
				{
					if (nbs < deathLimit)
					{
						newMap[x][y] = false;
					} else
					{
						newMap[x][y] = true;
					}
				} // Otherwise, if the cell is dead now, check if it has the
					// right number of neighbours to be 'born'
				else
				{
					if (nbs > birthLimit)
					{
						newMap[x][y] = true;
					} else
					{
						newMap[x][y] = false;
					}
				}
			}
		}
		return newMap;
	}

	public int countAliveNeighbours(boolean[][] map, int x, int y)
	{
		int count = 0;
		for (int i = -1; i < 2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				int neighbour_x = x + i;
				int neighbour_y = y + j;
				// If we're looking at the middle point
				if (i == 0 && j == 0)
				{
					// Do nothing, we don't want to add ourselves in!
				}
				// In case the index we're looking at it off the edge of the map
				else if (neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.length
						|| neighbour_y >= map[0].length)
				{
					count = count + 1;
				}
				// Otherwise, a normal check of the neighbour
				else if (map[neighbour_x][neighbour_y])
				{
					count = count + 1;
				}
			}
		}
		return count;
	}

	@Override
	public int getPriority()
	{
		return 250;
	}

}
