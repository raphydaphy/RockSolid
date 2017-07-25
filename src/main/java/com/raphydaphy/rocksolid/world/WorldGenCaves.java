package com.raphydaphy.rocksolid.world;

import java.util.Random;

import com.raphydaphy.rocksolid.api.Fluid;
import com.raphydaphy.rocksolid.init.ModFluids;
import com.raphydaphy.rocksolid.init.ModTiles;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;

public class WorldGenCaves implements IWorldGenerator {

	
	@Override
	public boolean shouldGenerate(IWorld world, IChunk chunk, Random rand) 
	{
		if(chunk.getY() < 2)
		{
			if (chunk.getY() < -50)
			{
				return rand.nextInt(3) == 1;
			}
			else if (chunk.getY() < -25)
			{
				return rand.nextInt(8) == 1;
			}
			else if (chunk.getY() < -15)
			{
				return rand.nextInt(15) == 1;
			}
			else if (chunk.getY() < -5)
			{
				return rand.nextInt(18) == 1;
			}
			else
			{
				return rand.nextInt(23) == 1;
			}
			
		}
		
		return false;
	}

	@Override
	public void generate(IWorld world, IChunk chunk, Random rand) 
	{
		int chunkMapSizeX = rand.nextInt(16) + 16;
		int chunkMapSizeY = rand.nextInt(16) + 16;
		int startX = rand.nextInt(32 - chunkMapSizeX);
		int startY = rand.nextInt(32 - chunkMapSizeY);
		
		boolean hasFluid = rand.nextInt(10) == 1;
		boolean fluidIsWater = false;
		int fluidHeight = 0;
		
		if (hasFluid)
		{
			// true = water, false = lava
			fluidIsWater = rand.nextBoolean();
			// used to calculate highest fluid in the chunk with chunkMapSizeY / this
			fluidHeight = (rand.nextInt(3) + 2);
		}
		
		
		boolean[][] terrain = new boolean[chunkMapSizeX][chunkMapSizeY];
		
		
		for (int x = 0; x < chunkMapSizeX; x++)
		{
			for (int y = 0; y < chunkMapSizeY; y++)
			{
				if (rand.nextFloat() > rand.nextFloat() )
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
			for (int y = 0; y < chunkMapSizeY; y++)
			{
				if (!terrain[x][y])
				{
						
						
					if (chunk.getGridY() < 0)
					{
						if (chunk.getGridY() == -1)
						{
							if (y > chunkMapSizeY - 2)
							{
								continue;
							}
						}
						if (!hasFluid || y > (chunkMapSizeY / fluidHeight))
						{
							world.setState(chunk.getX() + x + startX, chunk.getY() + y+ startY, GameContent.TILE_AIR.getDefState());
						}
						else if (fluidIsWater)
						{
							world.setState(chunk.getX() + x + startX, chunk.getY() + y+ startY, ModFluids.fluidWater.getDefStateWithProp(Fluid.fluidLevel, Fluid.MAX_VOLUME));
						}
						else
						{
							world.setState(chunk.getX() + x + startX, chunk.getY() + y+ startY, ModFluids.fluidLava.getDefStateWithProp(Fluid.fluidLevel, Fluid.MAX_VOLUME));
						}
						world.setState(TileLayer.BACKGROUND, chunk.getX() + x+ startX, chunk.getY() + y+ startY, ModTiles.rockLight.getDefState());
					}
				}
			}
		}
	}
	
	public boolean[][] doSimulationStep(boolean[][] oldMap){
	    boolean[][] newMap = new boolean[oldMap.length][oldMap[0].length];
	    
	    int deathLimit = 4;
	    int birthLimit = 4;
	    //Loop over each row and column of the map
	    for(int x=0; x<oldMap.length; x++)
	    {
	        for(int y=0; y<oldMap[0].length; y++){
	            int nbs = countAliveNeighbours(oldMap, x, y);
	            //The new value is based on our simulation rules
	            //First, if a cell is alive but has too few neighbours, kill it.
	            if(oldMap[x][y]){
	                if(nbs < deathLimit){
	                    newMap[x][y] = false;
	                }
	                else{
	                    newMap[x][y] = true;
	                }
	            } //Otherwise, if the cell is dead now, check if it has the right number of neighbours to be 'born'
	            else{
	                if(nbs > birthLimit){
	                    newMap[x][y] = true;
	                }
	                else{
	                    newMap[x][y] = false;
	                }
	            }
	        }
	    }
	    return newMap;
	}
	
	public int countAliveNeighbours(boolean[][] map, int x, int y){
	    int count = 0;
	    for(int i=-1; i<2; i++){
	        for(int j=-1; j<2; j++){
	            int neighbour_x = x+i;
	            int neighbour_y = y+j;
	            //If we're looking at the middle point
	            if(i == 0 && j == 0){
	                //Do nothing, we don't want to add ourselves in!
	            }
	            //In case the index we're looking at it off the edge of the map
	            else if(neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.length || neighbour_y >= map[0].length){
	                count = count + 1;
	            }
	            //Otherwise, a normal check of the neighbour
	            else if(map[neighbour_x][neighbour_y])
	            {
	                count = count + 1;
	            }
	        }
	    }
	    return count;
	}

	@Override
	public int getPriority() {
		return 250;
	}
	
	
}
