package com.raphydaphy.rocksolid.api.fluid;

import java.util.ArrayList;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.render.FluidRenderer;
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
	public static final int BUCKET_VOLUME = 5;
	public static final IntProp fluidLevel = new IntProp("volume", 1, MAX_VOLUME + 1);
	private double thickness = 0.015;
	
	public Fluid(String name)
	{
		this(RockSolidLib.makeRes(name));
	}
	
	public Fluid(IResourceName name) 
	{
		super(name);
	}
	
	@Override
	public Fluid register(){
		RockSolidAPI.FLUID_REGISTRY.register(this.getName(), this);
        return (Fluid)super.register();
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
		System.out.println("set the placement state");
		TileState existingBlock = world.getState(x, y);
		if (existingBlock.getTile() == GameContent.TILE_AIR)
		{
			return this.getDefStateWithProp(fluidLevel,BUCKET_VOLUME);
		}
		else
		{
			if (existingBlock.get(fluidLevel) + BUCKET_VOLUME <= MAX_VOLUME)
			{
				return existingBlock.prop(fluidLevel, existingBlock.get(fluidLevel) + BUCKET_VOLUME);
			}
			else
			{
				return existingBlock.prop(fluidLevel, MAX_VOLUME);
			}
		}
	}
	
	
	@Override
	public void onAdded(IWorld world, int x, int y, TileLayer layer)
	{
		//System.out.println("added and scheduled update with meta of " + world.getState(x, y).get(fluidLevel));
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
		TileState thisState = world.getState(x, y);
		TileState downState = world.getState(x, y - 1);
		TileState leftTile = world.getState(x - 1, y);
		TileState rightTile = world.getState(x + 1, y);
		
		if (thisState.getTile() instanceof Fluid)
		{
			world.scheduleUpdate(x, y, layer, 8);
		}
		
		// if the tile below is the same fluid as this
		if (downState.getTile() == thisState.getTile() && downState.get(fluidLevel) < MAX_VOLUME)
		{
			// if we can safely move all the fluid into the lower block
			if (downState.get(fluidLevel) + thisState.get(fluidLevel) <= MAX_VOLUME)
			{
				// move the fluid into the lower block
				world.setState(x, y - 1, downState.prop(fluidLevel, downState.get(fluidLevel) + thisState.get(fluidLevel)));
				world.scheduleUpdate(x, y - 1, layer, 8);
				world.setState(x, y, GameContent.TILE_AIR.getDefState());
				return;
			}
			// if we need to keep some fluid in the top block
			else
			{
				
			}
		}
		// if the tile below is air
		else if (downState.getTile() == GameContent.TILE_AIR)
		{
			// move the fluid down a block
			world.setState(x, y - 1, thisState);
			world.setState(x, y, GameContent.TILE_AIR.getDefState());
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
