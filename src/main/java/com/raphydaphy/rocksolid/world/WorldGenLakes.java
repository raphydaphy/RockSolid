package com.raphydaphy.rocksolid.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.raphydaphy.rocksolid.fluid.FluidWater;
import com.raphydaphy.rocksolid.init.ModTiles;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class WorldGenLakes implements IWorldGenerator
{
	private int fluidStart = 0;
	private Random rand;
	List<Pos2> desertPositions = new ArrayList<>();

	@Override
	public boolean shouldGenerate(IWorld world, IChunk chunk)
	{
		if (chunk.getGridX() == 0 || chunk.getGridY() < -1 || chunk.getGridY() > 3)
			return false;

		//if (rand.nextInt(3) != 1)
		//	return false;

		boolean desert = false;
		boolean air = false;

		boolean alreadyLake = false;

		fluidStart = 0;
		desertPositions.clear();

		for (int x = 0; x < 32; x++)
		{
			fluidStart += world.getLowestAirUpwards(TileLayer.MAIN, chunk.getX() + x, chunk.getY());
			for (int y = 0; y < 32; y++)
			{

				if (chunk.getBiomeInner(x, y).equals(GameContent.BIOME_DESERT))
				{
					desertPositions.add(new Pos2(x, y));
					desert = true;
				}
				Tile tile = chunk.getStateInner(x, y).getTile();
				if (tile.equals(GameContent.TILE_AIR))
				{
					air = true;
				} else if (tile.equals(ModTiles.WATER))
				{
					alreadyLake = true;
				}

				if (desert & air)
				{
					break;
				}
			}
		}
		fluidStart /= 32;
		return !alreadyLake && desert && air;
	}

	@Override
	public void initWorld(IWorld world)
	{
		rand = new Random(world.getSeed());
	}

	@Override
	public void generate(IWorld world, IChunk chunk)
	{
		System.out.println("New Lake @ " + chunk.getX() + ", " + chunk.getY());

		int chunkMapSizeX = 16;
		int chunkMapSizeY = 16;

		Pos2 start = desertPositions.get(rand.nextInt(desertPositions.size()));

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

		for (int x = 0; x < chunkMapSizeX; x++)
		{
			boolean liquidBelow = false;
			int depth = 0;
			for (int y = 0; y < chunkMapSizeY; y++)
			{
				if (!terrain[x][y] || liquidBelow)
				{
					depth++;

					if (depth < 6)
					{
						liquidBelow = true;

						if (!terrain[x][y] && y < fluidStart
								&& !(world.getState(chunk.getX() + start.getX() + x, chunk.getY() + y)).getTile()
										.isAir())
						{
							world.setState(TileLayer.LIQUIDS, chunk.getX() + start.getX() + x, chunk.getY() + y,
									ModTiles.WATER.getDefState().prop(((FluidWater) ModTiles.WATER).level, 11));

						}

					}

					world.setState(TileLayer.BACKGROUND, chunk.getX() + start.getX() + x, chunk.getY() + y,
							GameContent.TILE_AIR.getDefState());

					world.setState(TileLayer.MAIN, chunk.getX() + start.getX() + x, chunk.getY() + y,
							GameContent.TILE_AIR.getDefState());

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
		return -1;
	}

}