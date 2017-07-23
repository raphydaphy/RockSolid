package com.raphydaphy.rocksolid.api;

import static de.ellpeck.rockbottom.api.RockBottomAPI.createRes;

import java.util.ArrayList;

import com.raphydaphy.rocksolid.RockSolid;

import de.ellpeck.rockbottom.api.tile.TileBasic;

public abstract class Fluid extends TileBasic
{
	// the maximum meta value for a fluid block. meta starts at 1.
	public static final int MAX_VOLUME = 12;
	//private double thickness = 0.015;
	
	public Fluid(String name) {
		super(createRes(RockSolid.INSTANCE, name));
		this.register();
	}
	
	// set in fluid classes, should return the list of enemy fluids
	public abstract ArrayList<Fluid> getEnemyFluids();
	/*
	protected ITileRenderer<?> createRenderer(IResourceName name)
	{
        return new FluidRenderer<Fluid>(name);
    }
	
	@Override
	public int getPlacementMeta(IWorld world, int x, int y, TileLayer layer, ItemInstance instance)
	{
		// placed fluids should be a full block
		return MAX_VOLUME;
    }
	
	@Override
	public void onAdded(IWorld world, int x, int y, TileLayer layer)
	{
		world.scheduleUpdate(x, y, TileLayer.MAIN, 8);
    }
	
	// what should happen if there is about to be a collision with an enemy fluid
	public void onEnemyCollision(Tile enemyTile, Pos2 enemyPos, Pos2 thisPos, IWorld world)
	{
		world.setTile(thisPos.getX(), thisPos.getY(), GameContent.TILE_ROCK);
	}
	
	@Override
	public void onCollideWithEntity(IWorld world, int x, int y, TileLayer layer, Entity entity)
	{
		if (entity.onGround == false)
		{
			entity.motionY = -thickness;
		}
		if (entity instanceof AbstractEntityPlayer)
		{
			if (RockBottomAPI.getGame().getInput().isKeyDown(RockBottomAPI.getGame().getSettings().keyJump.key))
			{
				entity.onGround = true;
				entity.motionY += thickness * 3;
			}
		}
	}
	
	@Override
	public void onScheduledUpdate(IWorld world, int x, int y, TileLayer layer)
	{
		// store all the block positions that the fluid could possibly move to
		Tile thisBlock = world.getTile(x, y);
		Tile downBlock;
		Tile leftBlock;
		Tile rightBlock;
		
		// store all the meta values of adjacent blocks
		int thisMeta;
		int downMeta;
		int leftMeta;
		int rightMeta;
		
		// store the closest empty left and right blocks
		int closestLeft;
		int closestRight;
		
		// check that this block is actually a fluid
		if (!(thisBlock instanceof BlockFluid))
		{
			return;
		}
		
		// schedule the update after checking the block type to prevent lag
		world.scheduleUpdate(x, y, TileLayer.MAIN, 8);
		
		// only set the variables once we know that this is a fluid block
		downBlock = world.getTile(x, y - 1);
		thisMeta = world.getMeta(x, y);
		downMeta = world.getMeta(x, y - 1);
		
		
		// if the block below is either air or the same fluid, and the block below is not full of fluid
		if ((downBlock == thisBlock || downBlock == GameContent.TILE_AIR) && downMeta < MAX_VOLUME)
		{
			// if the block below is air
			if (downBlock == GameContent.TILE_AIR)
			{
				// set the block underneath this to the same fluid
				world.setTile(x, y - 1, thisBlock);
				
				// copy the fluid volume from this to the block below
				world.setMeta(x, y - 1, thisMeta);
				
				// delete this block
				world.setTile(x, y, GameContent.TILE_AIR);
				
				// stop running the code to prevent duplicating the fluid
				return;
			}
			// if the block below is the same fluid as this
			else
			{
				// if there is a small enough volume of fluid to merge the blocks together
				if (thisMeta <= MAX_VOLUME - downMeta)
				{
					// move all the fluid into the bottom block
					world.setMeta(x, y - 1, downMeta + thisMeta);
					
					// delete this block
					world.setTile(x, y, GameContent.TILE_AIR);
					
					// this code should stop running since we set the block to air but just in case
					return;
				}
				// if we have to keep some fluid in the top block
				else
				{
					// remove the most fluid possible from this block
					world.setMeta(x, y, thisMeta - (MAX_VOLUME - downMeta));
					
					// move the maximum volume of fluid possible into the bottom block
					world.setMeta(x, y - 1, 6);
					
					// stop running now that we have moved as much fluid as we can down
					return;
				}
			}
		}
		
		// variables for left and right block information
		leftBlock = world.getTile(x - 1, y);
		rightBlock = world.getTile(x + 1, y);
		leftMeta = world.getMeta(x - 1, y);
		rightMeta = world.getMeta(x + 1, y);
		
		// if there is a fluid or air block to the left, and if this block can flow into the block on the left
		if ((leftBlock == thisBlock || leftBlock == GameContent.TILE_AIR) && leftMeta + 1 < thisMeta)
		{
			// move one unit of fluid to the left
			this.displaceLeft(world, leftBlock, thisBlock, leftMeta, thisMeta, x, y);
			
			// exit from the update
			return;
		}
		
		// if there is a fluid or air block to the right, and if it is able to receive fluid
		if ((rightBlock == thisBlock || rightBlock == GameContent.TILE_AIR) && rightMeta + 1 < thisMeta)
		{
			// move one unit of fluid to the right
			this.displaceRight(world, rightBlock, thisBlock, rightMeta, thisMeta, x, y);
			
			// stop updating the block to prevent running extra code
			return;
		}
		// initialize the left and right distance variables
		closestRight = 13;
		closestLeft = 13;
		
		// set if there is a block in the way on the left or the right
		boolean finishedRight = false;
		boolean finishedLeft = false;
		
		if (thisMeta > 1 && (rightMeta < MAX_VOLUME || leftMeta < MAX_VOLUME))
		{
			// loop up to 12 to find the closest block that fluids can flow to
			for(int i = 1; i < 13; i++)
			{
				// store the left and right block at the current distance
				Tile tileRight = world.getTile(x + i, y);
				Tile tileLeft = world.getTile(x - i, y);
				
				// store the left and right meta values at the current distance
				int metaRight = world.getMeta(x + i, y);
				int metaLeft = world.getMeta(x - i, y);
				
				// if the current distance to the right is a valid block to flow towards
				if (!finishedRight && (tileRight == GameContent.TILE_AIR || tileRight == thisBlock))
				{
					// if we can flow to the selected block on the right
					if (metaRight + 1 < thisMeta)
					{
						// if this block is the first valid block found to the right
						if (closestRight > i)
						{
							// set the closest right block to this block
							closestRight = i;
						}
					}
				}
				// if the right side has hit a solid block
				else
				{
					// stop calculating blocks to the right
					finishedRight = true;
				}
				
				// if the current distance to the left contains a valid block to flow to
				if (!finishedLeft && (tileLeft == GameContent.TILE_AIR || tileLeft == thisBlock))
				{
					// if we can flow to the left
					if (metaLeft + 1 < thisMeta)
					{
						// if this block is the first valid one found to the left
						if (closestLeft > i)
						{
							// set the closest left block to the current distance
							closestLeft = i;
						}
					}
				}
				// if we have hit a solid block on the left
				else
				{
					// stop calculating the left block distance
					finishedLeft = true;
				}
				
				// if both the left and right sides have hit a solid block
				if (finishedLeft && finishedRight)
				{
					// stop calculating anything to prevent excess lag
					break;
				}
			}
			
			// if the closest valid block to the left is closer than the right or equal
			if (closestLeft <= closestRight && closestLeft < 13)
			{
				// check that the block to the right isn't fuller than this
				if (leftMeta < thisMeta)
				{
					this.displaceRight(world, leftBlock, thisBlock, leftMeta, thisMeta, x, y);
				}
				
				// exit out of the update
				return;
			}
			// if there is a valid block to the right
			else if (closestRight < 13)
			{
				// if it is safe to move fluid to the right
				if (rightMeta < thisMeta)
				{
					// move one unit of fluid to the right
					this.displaceRight(world, rightBlock, thisBlock, rightMeta, thisMeta, x, y);
				}
				
				// exit out of the update
				return;
			}
		}
    }
	
	public void displaceLeft(IWorld world, Tile leftBlock, Tile thisBlock, int leftMeta, int thisMeta, int x, int y)
	{
		// if the block we would be displacing to is adjacent to an enemy fluid
		if (getEnemyFluids().contains(world.getTile(x - 2, y)))
		{
			// there has been an enemy collision, calculate a result
			onEnemyCollision(world.getTile(x + 2, y), new Pos2(x - 2, y), new Pos2(x - 1, y), world);
			
			// cancel the displacing
			return;
		}
		// if the block to the left is air
		if (leftBlock == GameContent.TILE_AIR)
		{
			// set the left block to the same fluid as this
			world.setTile(x - 1, y, thisBlock);
			
			// remove one from the amount of fluid in this block
			world.setMeta(x, y, thisMeta - 1);
			
			// make sure the new block has correct fluid level
			world.setMeta(x - 1, y, 1);
			
			System.out.println("made new block to the left. This: " + world.getMeta(x, y) + " That: " + world.getMeta(x - 1, y));
			
			// make sure no more code runs in this update
			return;
		}
		// if the left block is the same fluid as this
		else
		{
			// add one the the fluid level of the left block
			world.setMeta(x - 1, y, leftMeta + 1);
			
			// remove one from this blocks fluid level
			world.setMeta(x, y, thisMeta - 1);
			
			System.out.println("added fluid to the left. This: " + world.getMeta(x, y) + " That: " + world.getMeta(x - 1, y));
			
			// exit out of the update
			return;
		}
	}
	
	public void displaceRight(IWorld world, Tile rightBlock, Tile thisBlock, int rightMeta, int thisMeta, int x, int y)
	{
		// if the block we would be displacing to is adjacent to an enemy fluid
		if (getEnemyFluids().contains(world.getTile(x + 2, y)))
		{
			// there has been an enemy collision, so we need to do something
			onEnemyCollision(world.getTile(x + 2, y), new Pos2(x + 2, y), new Pos2(x + 1, y), world);
			
			// cancel the displacing
			return;
		}
				
		// if the right block is air
		if (rightBlock == GameContent.TILE_AIR)
		{
			// set the right block to the same fluid as this
			world.setTile(x + 1, y, thisBlock);
			
			// remove one from the fluid in this block
			world.setMeta(x, y, thisMeta - 1);
			
			// add fluid to the new block
			world.setMeta(x + 1, y, 1);
			
			System.out.println("made new block to the right. This: " + world.getMeta(x, y) + " That: " + world.getMeta(x + 1, y));
			
			
			// stop running code
			return;
		}
		// if the right block is the same fluid as this
		else
		{
			// increase the right blocks fluid level
			world.setMeta(x + 1, y, rightMeta + 1);
			
			// decrease this blocks fluid level
			world.setMeta(x, y, thisMeta - 1);
			
			System.out.println("added fluid to the right. This: " + world.getMeta(x, y) + " That: " + world.getMeta(x + 1, y));
			
			// stop to prevent calling code accidently
			return;
		}
	}
	
	public void setThickness(double thickness)
	{
		this.thickness = thickness;
	}
	
	@Override
	public boolean isFullTile()
	{
		return false;
	}
	
	@Override
	public boolean canBreak(IWorld world, int x, int y, TileLayer layer)
	{
		return false;
    }
	 
	@Override
	public BoundBox getBoundBox(IWorld world, int x, int y)
	{
        return null;
    }
	
	@Override
	public boolean canReplace(IWorld world, int x, int y, TileLayer layer, Tile replacementTile){
        return true;
    }
	
	@Override
	public boolean canPlaceInLayer(TileLayer layer){
        return layer != TileLayer.BACKGROUND || !this.canProvideTileEntity();
    }*/

}
