package com.raphydaphy.rocksolid.api;

import java.util.ArrayList;

import com.raphydaphy.rocksolid.render.FluidRenderer;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public abstract class Fluid extends TileBasic
{
	// the maximum meta value for a fluid block. meta starts at 1.
	public static final int MAX_VOLUME = 12;
	public static final IntProp fluidLevel = new IntProp("volume", 1, MAX_VOLUME + 1);
	private double thickness = 0.015;
	
	public Fluid(String name)
	{
		this(RockSolidLib.makeRes(name));
	}
	
	public Fluid(IResourceName name) 
	{
		super(name);
		this.register();
	}
	
	// set in fluid classes, should return the list of enemy fluids
	public abstract ArrayList<Fluid> getEnemyFluids();
	
	@Override
    public TileProp[] getProperties() {
        return new TileProp[]{ fluidLevel };
    }
	
	protected ITileRenderer<?> createRenderer(IResourceName name)
	{
        return new FluidRenderer<Fluid>(name);
    }
	
	@Override
	public TileState getPlacementState(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer)
	{
		
		return this.getDefStateWithProp(fluidLevel, MAX_VOLUME);
	}
	
	
	@Override
	public void onAdded(IWorld world, int x, int y, TileLayer layer)
	{
		world.scheduleUpdate(x, y, TileLayer.MAIN, 8);
    }
	
	// what should happen if there is about to be a collision with an enemy fluid
	public void onEnemyCollision(Tile enemyTile, Pos2 enemyPos, Pos2 thisPos, IWorld world)
	{
		world.setState(thisPos.getX(), thisPos.getY(), GameContent.TILE_ROCK.getDefState());
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
		System.out.println("UPDATE INCOMING!");
		// store all the block positions that the fluid could possibly move to
		Tile thisBlock = world.getState(x, y).getTile();
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
		if (!(thisBlock instanceof Fluid))
		{
			System.out.println("I'm not a fluid anymore! I'm a " + thisBlock.getName().toString());
			return;
		}
		
		// schedule the update after checking the block type to prevent lag
		world.scheduleUpdate(x, y, TileLayer.MAIN, 8);
		
		// only set the variables once we know that this is a fluid block
		downBlock = world.getState(x, y - 1).getTile();
		thisMeta = world.getState(x, y).get(fluidLevel);
		downMeta = 0;
		
		System.out.println("Updatinig with " + thisMeta + " fluid!");
		if (downBlock instanceof Fluid)
		{
			downMeta = world.getState(x, y - 1).get(fluidLevel);
		}
		
		// if the block below is either air or the same fluid, and the block below is not full of fluid
		if ((downBlock == thisBlock || downBlock == GameContent.TILE_AIR) && downMeta < MAX_VOLUME)
		{
			System.out.println("i think i can go down!");
			// if the block below is air
			if (downBlock == GameContent.TILE_AIR)
			{
				System.out.println("GOING DOWN TO AIR!");
				// set the block underneath this to the same fluid with the same volume
				world.setState(x, y - 1, thisBlock.getDefStateWithProp(fluidLevel, thisMeta));
				
				// delete this block
				world.setState(x, y, GameContent.TILE_AIR.getDefState());
				
				// stop running the code to prevent duplicating the fluid
				return;
			}
			// if the block below is the same fluid as this
			else
			{
				// if there is a small enough volume of fluid to merge the blocks together
				if (thisMeta <= MAX_VOLUME - downMeta)
				{
					System.out.println("GOING DOWN TO NOT FILL!");
					// move all the fluid into the bottom block
					world.setState(x, y - 1, this.getDefStateWithProp(fluidLevel, downMeta + thisMeta));
					
					// delete this block
					world.setState(x, y, GameContent.TILE_AIR.getDefState());
					
					// this code should stop running since we set the block to air but just in case
					return;
				}
				// if we have to keep some fluid in the top block
				else
				{
					System.out.println("GOING DOWN WITH EXCESS!");
					// remove the most fluid possible from this block
					world.setState(x, y, this.getDefStateWithProp(fluidLevel, thisMeta - (MAX_VOLUME - downMeta)));
					
					// move the maximum volume of fluid possible into the bottom block
					world.setState(x, y - 1, this.getDefStateWithProp(fluidLevel, 6));
					
					// stop running now that we have moved as much fluid as we can down
					return;
				}
			}
		}
		
		// variables for left and right block information
		leftBlock = world.getState(x - 1, y).getTile();
		rightBlock = world.getState(x + 1, y).getTile();
		leftMeta = 0;
		rightMeta = 0;
		if (leftBlock instanceof Fluid)
		{
			leftMeta = world.getState(x - 1, y).get(fluidLevel);
		}
		if (rightBlock instanceof Fluid)
		{
			rightMeta = world.getState(x + 1, y).get(fluidLevel); 
		}
		
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
				Tile tileRight = world.getState(x + i, y).getTile();
				Tile tileLeft = world.getState(x - i, y).getTile();
				
				// store the left and right meta values at the current distance
				int metaRight = 0;
				int metaLeft = 0;
				if (tileRight instanceof Fluid)
				{
					metaRight = world.getState(x + i, y).get(fluidLevel);
				}
				if (tileLeft instanceof Fluid)
				{
					metaLeft = world.getState(x - i, y).get(fluidLevel);
				}
				
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
		if (getEnemyFluids().contains(world.getState(x - 2, y).getTile()))
		{
			// there has been an enemy collision, calculate a result
			onEnemyCollision(world.getState(x + 2, y).getTile(), new Pos2(x - 2, y), new Pos2(x - 1, y), world);
			
			// cancel the displacing
			return;
		}
		// if the block to the left is air
		if (leftBlock == GameContent.TILE_AIR)
		{
			
			// remove one from the amount of fluid in this block
			world.setState(x, y, this.getDefStateWithProp(fluidLevel, thisMeta - 1));
			
			// schedule a new update because we changed the state
			world.scheduleUpdate(x, y, TileLayer.MAIN, 8);
			
			// make sure the new block has correct fluid level
			world.setState(x - 1, y, this.getDefStateWithProp(fluidLevel, 1));
			
			// make sure no more code runs in this update
			return;
		}
		// if the left block is the same fluid as this
		else
		{
			// add one the the fluid level of the left block
			world.setState(x - 1, y, this.getDefStateWithProp(fluidLevel, leftMeta + 1));
			
			// schedule a new update because we changed the state
			world.scheduleUpdate(x - 1, y, TileLayer.MAIN, 8);
			
			// remove one from this blocks fluid level
			world.setState(x, y, this.getDefStateWithProp(fluidLevel, thisMeta - 1));
			
			// schedule a new update because we changed the state
			world.scheduleUpdate(x, y, TileLayer.MAIN, 8);
			
			// exit out of the update
			return;
		}
	}
	
	public void displaceRight(IWorld world, Tile rightBlock, Tile thisBlock, int rightMeta, int thisMeta, int x, int y)
	{
		// if the block we would be displacing to is adjacent to an enemy fluid
		if (getEnemyFluids().contains(world.getState(x + 2, y).getTile()))
		{
			// there has been an enemy collision, so we need to do something
			onEnemyCollision(world.getState(x + 2, y).getTile(), new Pos2(x + 2, y), new Pos2(x + 1, y), world);
			
			// cancel the displacing
			return;
		}
				
		// if the right block is air
		if (rightBlock == GameContent.TILE_AIR)
		{
			
			// remove one from the fluid in this block
			world.setState(x, y, this.getDefStateWithProp(fluidLevel, thisMeta - 1));
			
			// schedule a new update because we changed the state
			world.scheduleUpdate(x, y, TileLayer.MAIN, 8);
			
			// add fluid to a new block to the right
			world.setState(x + 1, y, this.getDefStateWithProp(fluidLevel, 1));
			
			
			// stop running code
			return;
		}
		// if the right block is the same fluid as this
		else
		{
			// increase the right blocks fluid level
			world.setState(x + 1, y, this.getDefStateWithProp(fluidLevel, rightMeta + 1));
			
			// schedule a new update because we changed the state
			world.scheduleUpdate(x + 1, y, TileLayer.MAIN, 8);
						
			// decrease this blocks fluid level
			world.setState(x, y, this.getDefStateWithProp(fluidLevel, thisMeta - 1));
			
			// schedule a new update because we changed the state
			world.scheduleUpdate(x, y, TileLayer.MAIN, 8);
			
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
    }

}
