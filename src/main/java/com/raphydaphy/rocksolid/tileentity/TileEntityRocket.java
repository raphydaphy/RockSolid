package com.raphydaphy.rocksolid.tileentity;

import java.util.Arrays;
import java.util.List;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;
import com.raphydaphy.rocksolid.entity.EntityRocket;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileEntityRocket extends TileEntity implements IFluidAcceptor, IInventoryHolder
{

	protected int fluidStored;
	protected int maxFluid = 10000;
	protected String fluidType;
	
	protected boolean shouldSync;
	protected boolean isMoving;
	private EntityRocket entity = null;
	
	public final ContainerInventory inventory;

	public TileEntityRocket(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
		this.inventory = new ContainerInventory(this, 18);
		if (fluidType == null)
		{
			fluidType = Fluid.EMPTY.getName();
			
			
		}
	}
	
	private TileEntityRocket(int x, int y, TileEntityRocket old) 
	{
        super(old.world, x, y);
        this.inventory = new ContainerInventory(this, 20);
        this.entity = old.entity;
        this.shouldSync = true;
	}
	
	public TileEntityRocket(final IWorld world, int x, int y, EntityRocket entity)
	{
		super(world, x, y);
		this.inventory = new ContainerInventory(this, entity.getInv().getSlotAmount());
		for (int slot = 0; slot < entity.getInv().getSlotAmount(); slot++)
		{
			this.inventory.set(slot, entity.getInv().get(slot));
		}
		this.fluidStored = entity.getFuel();
		if (fluidStored > 0)
		{
			this.fluidType = Fluid.FUEL.getName();
		}
		this.entity = entity;
		this.shouldSync = true;
	}

	@Override
	protected boolean needsSync()
	{
		return super.needsSync() || shouldSync;
	}

	@Override
	protected void onSync()
	{
		super.onSync();
		shouldSync = false;
	}
	
	public boolean displayLaunchBtn()
	{
		if (this.isMoving == false)
		{
			return true;
		}
		return false;
	}
	
	public void launch()
	{
		if (displayLaunchBtn() && this.fluidStored > 0 && this.fluidType.equals(Fluid.FUEL.getName()))
		{
			this.isMoving = true;
			Tile thisTile = world.getState(x, y).getTile();
			this.entity = new EntityRocket(world, thisTile.getName(), x, y, this.fluidStored, this.inventory);
            world.addEntity(this.entity);
            thisTile.doBreak(world, x, y, TileLayer.MAIN, null, false, false);
            this.shouldSync = true;
		}
	}
	
	

	public float getFluidTankFullnesss()
	{
		if (fluidStored == 0)
		{
			return 0;
		}
		return (float) this.fluidStored / (float) this.maxFluid;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		if (!forSync)
		{
			this.inventory.save(set);
		}
		set.addInt(Fluid.KEY, this.fluidStored);
		set.addInt(Fluid.MAX_KEY, this.maxFluid);
		set.addString(Fluid.TYPE_KEY, this.fluidType);
		set.addBoolean("shouldSync", this.shouldSync);
		set.addBoolean("isMoving", this.isMoving);
	}
	

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		if (!forSync)
		{
			this.inventory.load(set);
		}
		this.fluidStored = set.getInt(Fluid.KEY);
		this.maxFluid = set.getInt(Fluid.MAX_KEY);
		this.fluidType = set.getString(Fluid.TYPE_KEY);
		this.shouldSync = set.getBoolean("shouldSync");
		this.isMoving = set.getBoolean("isMoving");
	}

	@Override
	public int getCurrentFluid()
	{
		return this.fluidStored;
	}

	@Override
	public int getMaxFluid()
	{
		return this.maxFluid;
	}

	@Override
	public String getFluidType()
	{
		return this.fluidType;
	}

	@Override
	public boolean addFluid(int amount, String type)
	{
		if (this.fluidType == null || type.equals(this.fluidType)
				|| this.fluidType.equals(Fluid.EMPTY.getName()))
		{
			if (this.fluidStored + amount <= this.maxFluid)
			{
				if (!world.isClient())
				{
					if (this.fluidType == null || !this.fluidType.equals(type))
					{
						this.fluidType = type;
						RockBottomAPI.getGame().getWorld().causeLightUpdate(x, y);
					}
					this.fluidStored += amount;
					this.shouldSync = true;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setFluidType(String type)
	{
		if (this.fluidType == Fluid.EMPTY.getName() || this.fluidStored == 0)
		{
			if (!world.isClient())
			{
				this.fluidType = type;
				this.shouldSync = true;
			}
			return true;
		}
		return false;
	}

	@Override
	public IInventory getInventory()
	{
		return this.inventory;
	}

	@Override
	public List<Integer> getInputSlots(ItemInstance instance, Direction dir)
	{
		return Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
	}

	@Override
	public List<Integer> getOutputSlots(Direction dir)
	{
		return Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
	}

}
