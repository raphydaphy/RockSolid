package com.raphydaphy.rocksolid.world;

import java.util.Random;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;

/*
 * A idea i had
 * 
 * Makes caves using game of life celluar thing
 * 
 * Where:
 * 
 *  Living = rock
 *  Dead = air
 *  
 *  We can process ore gen etc after the caves generate to make the sides of the caves generate ore but not in the middle where it is air
 * 
 * If a living cell has less than two living neighbours, it dies.
 * If a living cell has two or three living neighbours, it stays alive.
 * If a living cell has more than three living neighbours, it dies.
 * If a dead cell has exactly three living neighbours, it becomes alive.
 */
public class WorldGenCaves implements IWorldGenerator{

	@Override
	public boolean shouldGenerate(IWorld world, IChunk chunk, Random rand) 
	{
		return chunk.getY() < 0;
	}
	
	 
	@Override
	public void generate(IWorld world, IChunk chunk, Random rand) 
	{
		// if this chunk should contain a cave
		//if (rand.nextFloat() > 0.7)
		if (true)
		{
			boolean[][] chunkMap = fillChunkRandomly(rand);
			
			// regenerate the chunk a set amount of times to get less bumpy caves
			for (int generations = 0; generations < 2; generations++)
			{
				chunkMap = makeNewMap(chunkMap);
			}
			
			for (int x = 0; x < 32; x++)
			{
				for (int y = 0; y < 32; y++)
				{
					if (chunkMap[x][y] == false)
					{
						world.setTile(chunk.getX() + x, chunk.getY() + y, GameContent.TILE_AIR);
					}
				}
			}
		}
	}
	
	private boolean[][] fillChunkRandomly(Random rand)
	{
		boolean[][] caveMap = new boolean[32][32];
		
		for (int x = 0; x < 32; x++)
		{
			for (int y = 0; y < 32; y++)
			{
				if (rand.nextFloat() > 0.55)
				{
					// this tile should be rock in the initial generation
					caveMap[x][y] = true;
				}
			}
		}
		
		return caveMap;
	}
	
	private boolean[][] makeNewMap(boolean[][] oldMap)
	{
		boolean[][] newCaveMap = new boolean[32][32];
		for (int x = 0; x < 32; x++)
		{
			for (int y = 0; y < 32; y++)
			{
				// Check how many adjacent rock blocks the current selected block has
				int adjRocks = countAdjacentRockBlocks(oldMap, new Pos2(x, y));
				
				if (oldMap[x][y])
				{
					// if the amount of adjacent rock blocks is less than the limit for it to also be a rock block
					if (adjRocks < 3)
					{
						newCaveMap[x][y] = false;
						continue;
					}
					// if it has enough adjacent rock blocks to become a rock block too
					else
					{
						newCaveMap[x][y] = true;
					}
				}
				else
				{
					// If the amount of adjacent air blocks is high enough for it to also become an air block
					if (adjRocks > 4)
					{
						newCaveMap[x][y] = true;
					}
					else
					{
						// if there are not enough adjacent air blocks, keep it as a rock block
						newCaveMap[x][y] = false;
					}
				}
			}
		}
		
		return newCaveMap;
	}
	
	private int countAdjacentRockBlocks(boolean[][] curMap, Pos2 mapPos)
	{
		int rockBlocks = 0;
		
		for (int modX = -1; modX < 2; modX++)
		{
			for (int modY = -1; modY < 2; modY++)
			{
				int adjX = mapPos.getX() + modX;
				int adjY = mapPos.getY() + modY;
				
				if (modX == 0 && modY == 0)
				{
					// dont add the center block to the adjacent tiles
					continue;
				} 
				
				else if (adjX < 0 || adjY < 0 || adjX >= 32 || adjY >= 32)
				{
					// this place is off the edge of the map so we will assume that it is rock
					rockBlocks++;
					continue;
				}
				
				else if (curMap[adjX][adjY])
				{
					// the adjacent tile is within the map and it is rock
					rockBlocks++;
					continue;
				}
			}
		}
		return rockBlocks;
	}

	@Override
	public int getPriority() 
	{
		return 250;
	}

}
