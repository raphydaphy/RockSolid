package com.raphydaphy.rocksolid.world;

import java.util.Random;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;

public class WorldGenCaves implements IWorldGenerator {

	@Override
	public boolean shouldGenerate(IWorld world, IChunk chunk, Random rand) {
		return true;
	}

	@Override
	public void generate(IWorld world, IChunk chunk, Random rand) {
		int caveWidth = 32;
		int caveHeight = 32;

		// starting positions of cave
		int curX = 0;
		int curY = 0;
		boolean foundStartPos = false;
		if (chunk.getGridY() == -1)
		{
			curX = rand.nextInt(32);
			curY = world.getLowestAirUpwards(TileLayer.MAIN, chunk.getX() + curX, 0);
			foundStartPos = true;
		}
		else if (chunk.getGridY() == 0)
		{
			IChunk chunkBelow = world.getChunkFromGridCoords(chunk.getGridX(), chunk.getGridY() - 1);
			if (world.isChunkLoaded(chunkBelow.getGridX(), chunkBelow.getGridY()))
			{
				for (int x = 0; x < 32; x++)
				{
					if (world.getTile(chunkBelow.getX() + x, chunk.getY() - 1).equals(GameContent.TILE_AIR))
					{
						curX = x;
						curY = world.getLowestAirUpwards(TileLayer.MAIN, curX, chunk.getY());
						System.out.println("found suitable pos at " + curX + chunkBelow.getX());
						foundStartPos = true;
					}
				}
				
				
			}
			
		}
		else if (chunk.getGridY() < -1)
		{
			IChunk chunkAbove = world.getChunkFromGridCoords(chunk.getGridX(), chunk.getGridY() + 1);
			if (world.isChunkLoaded(chunkAbove.getGridX(), chunkAbove.getGridY()))
			{
				for (int x = 0; x < 32; x++)
				{
					if (world.getTile(chunkAbove.getX() + x, chunkAbove.getY()).equals(GameContent.TILE_AIR))
					{
						curX = x;
						curY = 31;
						foundStartPos = true;
					}
				}
				
				
			}
			
			if (!foundStartPos)
			{
				IChunk chunkLeft = world.getChunkFromGridCoords(chunk.getGridX() - 1, chunk.getGridY());
				if (world.isChunkLoaded(chunkLeft.getGridX(), chunkLeft.getGridY()))
				{
					for (int y = 0; y < 32; y++)
					{
						if (world.getTile(chunkLeft.getX(), chunkLeft.getY() + y).equals(GameContent.TILE_AIR))
						{
							curX = 0;
							curY = y;
							foundStartPos = true;
						}
					}
					
					
				}
				
				if (!foundStartPos)
				{
					IChunk chunkRight = world.getChunkFromGridCoords(chunk.getGridX() + 1, chunk.getGridY());
					if (world.isChunkLoaded(chunkRight.getGridX(), chunkRight.getGridY()))
					{
						for (int y = 0; y < 32; y++)
						{
							if (world.getTile(chunkRight.getX(), chunkRight.getY() + y).equals(GameContent.TILE_AIR))
							{
								curX = 31;
								curY = y;
								foundStartPos = true;
							}
						}
						
						
					}
				}
			}
		}
		
		if (!foundStartPos)
		{
			return;
		}
		// int curY = rand.nextInt(32);
		// true = air block
		char[] caveCells = new char[caveWidth * caveHeight];
		
		try
		{
			while (caveCells[curY * caveWidth + curX] == ' ') {
				curY++;
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return;
		}

		// drill down until we are deep underground
		if (chunk.getGridY() > -2)
		{
			while (caveCells[curY * caveWidth + curX - 1] == ' ' || caveCells[curY * caveWidth + curX + 1] == ' ') {
				caveCells[curY * caveWidth + curX] = '.';
				curY++;
			}
		}

		// starting from the entrance, walk randomly within the terrain,
		// "carving out" the cave
		// make sure we do not create new entrances; the carving must not go
		// adjacent to an "open air" cell
		for (int i = 0; i < 1000; i++) {
			// "carve out" the current cell
			caveCells[curY * caveWidth + curX] = '.';

			// Get random direction:
			// 0: up
			// 1: right
			// 2: down
			// 3: left
			int dir = rand.nextInt(4);
			switch (dir) {
			case 0: // up\
				if (isValidCaveCell(curX, curY + 1, caveHeight, caveWidth, caveCells)) {
					curY++;
				}
				break;
			case 1: // right
				if (isValidCaveCell(curX + 1, curY, caveHeight, caveWidth, caveCells)) {
					curX++;
				}
				break;
			case 2: // down
				if (isValidCaveCell(curX, curY - 1, caveHeight, caveWidth, caveCells)) {
					curY--;
				}
				break;
			case 3: // left
				if (isValidCaveCell(curX - 1, curY, caveHeight, caveWidth, caveCells)) {
					curX--;
				}
				break;
			}
		}

		// Show our results
		for (int y = 0; y < caveHeight; y++) {
			for (int x = 0; x < caveWidth; x++) {
				if (caveCells[y * caveWidth + x] == '.') {
					if (!world.getTile(chunk.getX() + x, chunk.getY() + y).equals(GameContent.TILE_LOG)
							&& !world.getTile(chunk.getX() + x, chunk.getY() + y).equals(GameContent.TILE_LEAVES))
						if (world.isPosLoaded(chunk.getX() + x, chunk.getY() + y)) {
							world.setTile(chunk.getX() + x, chunk.getY() + y, GameContent.TILE_AIR);
						}
					
					
				}
				
				if (caveCells[y * caveWidth + x] == ' ') {
					if (!world.getTile(chunk.getX() + x, chunk.getY() + y).equals(GameContent.TILE_LOG)
							&& !world.getTile(chunk.getX() + x, chunk.getY() + y).equals(GameContent.TILE_LEAVES))
						if (world.isPosLoaded(chunk.getX() + x, chunk.getY() + y)) {
							world.setTile(chunk.getX() + x, chunk.getY() + y, GameContent.TILE_AIR);
							world.setTile(TileLayer.BACKGROUND,chunk.getX() + x, chunk.getY() + y, GameContent.TILE_AIR);
						}
					
					
				}
			}
		}
	}

	private boolean isValidCaveCell(int x, int y, int height, int width, char[] cells) {
		// a cave cell is valid if:
		// - it doesn't exceed the world boundaries
		// - it isn't adjacent to an "open air" cell
		if (x < 0 || x >= width || y < 0 || y >= height) {
			// out of world bounds
			return false;
		}
		for (int dx = -1; dx <= 1; dx++) {
			if (x + dx < 0 || x + dx >= width) {
				continue;
			}
			for (int dy = -1; dy <= 1; dy++) {
				if (y + dy < 0 || y + dy >= height) {
					continue;
				}
				if (cells[(y + dy) * width + (x + dx)] == ' ') {
					// adjacent to "open air" cell
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int getPriority() {
		return 250;
	}

}
