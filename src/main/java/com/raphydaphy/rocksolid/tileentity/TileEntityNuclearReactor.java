package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.api.IEnergyProducer;
import com.raphydaphy.rocksolid.api.IHasInventory;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntityFueled;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityNuclearReactor extends TileEntityFueled implements IHasInventory, IEnergyProducer
{

    public static final int COAL = 0;
    public final ContainerInventory inventory;
    protected int powerStored;
    protected int maxPower;
    protected int productionPerTick;
    private boolean shouldSync = false;
    
    public TileEntityNuclearReactor(final IWorld world, final int x, final int y) 
    {
        super(world, x, y);
        this.inventory = new ContainerInventory(this, 4);
        
        maxPower = 1000000;
    	productionPerTick = 150;
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
    
    @Override
    protected boolean tryTickAction() 
    {
    	if (powerStored < (maxPower - productionPerTick - 1))
    	{
    		if (RockBottomAPI.getNet().isClient() == false && this.coalTime > 0) 
            {
            	powerStored += productionPerTick;
            	shouldSync = true;
            	
            }
            return true;
    	}
        return false;
    }
    
    @Override
    protected float getFuelModifier() 
    {
        return 0.25f;
    }
    
    @Override
    protected ItemInstance getFuel() 
    {
        return this.inventory.get(0);
    }
    
    @Override
    protected void removeFuel() 
    {
        this.inventory.remove(0, 1);
    }
    
    @Override
    protected void onActiveChange(final boolean active) 
    {
        this.world.causeLightUpdate(this.x, this.y);
    }
    
    public float getGeneratorFullness()
    {
    	if (powerStored == 0)
    	{
    		return 0;
    	}
        return (float)this.powerStored/(float)this.maxPower;
    }
    
    @Override
    public void save(final DataSet set, final boolean forSync) 
    {
        super.save(set, forSync);
        if (!forSync) {
            this.inventory.save(set);
        }
        set.addInt("powerStored", this.powerStored);
        set.addInt("maxPower", this.maxPower);
        set.addInt("productionPerTick", this.productionPerTick);
        set.addBoolean("shouldSync", this.shouldSync);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) 
    {
        super.load(set, forSync);
        if (!forSync) {
            this.inventory.load(set);
        }
        this.powerStored = set.getInt("powerStored");
        this.maxPower = set.getInt("maxPower");
        this.productionPerTick = set.getInt("productionPerTick");
        this.shouldSync = set.getBoolean("shouldSync");
    }

	@Override
	public Inventory getInventory() 
	{
		return this.inventory;
	}

	@Override
	public List<Integer> getInputs() 
	{
		List<Integer> insertSlots = new ArrayList<Integer>();
		insertSlots.add(0);
		return insertSlots;
	}

	@Override
	public List<Integer> getOutputs() 
	{
		return null;
	}

	@Override
	public int getCurrentEnergy() 
	{
		return this.powerStored;
	}

	@Override
	public int getMaxEnergy() 
	{
		return this.maxPower;
	}

	@Override
	public boolean removeEnergy(int amount) 
	{
		if (this.powerStored >= amount)
		{
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.powerStored -= amount;
				this.shouldSync = true;
			}
			return true;
		}
		return false;
	}

}
