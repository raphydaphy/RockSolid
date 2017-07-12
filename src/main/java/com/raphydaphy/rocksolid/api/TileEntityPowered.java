package com.raphydaphy.rocksolid.api;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class TileEntityPowered extends TileEntity implements IHasInventory, IEnergyAcceptor
{
    private boolean lastActive;
    private int maxPower;
    private int powerPerOperation;
    
    public TileEntityPowered(final IWorld world, final int x, final int y, final int maxPower, final int powerPerOperation) 
    {
        super(world, x, y);
        this.maxPower = maxPower;
        this.powerPerOperation = powerPerOperation;
    }
    
    @Override
    protected boolean needsSync() 
    {
        return super.needsSync();
    }
    
    @Override
    protected void onSync() 
    {
        super.onSync();
    }
    
    protected abstract void setPower(int power);
    protected abstract int getPower();
    protected abstract boolean tryTickAction();
    protected abstract void onActiveChange(boolean active);
    protected abstract boolean isActive();
    
    @Override
    public void update(IGameInstance game){
        super.update(game);

        if(!RockBottomAPI.getNet().isClient()){
            boolean smelted = this.tryTickAction();

            if(this.getPower() >= (0 + this.powerPerOperation) && smelted)
            {
                this.setPower(this.getPower() - this.powerPerOperation);
            }
        }

        boolean active = this.isActive();
        if(this.lastActive != active){
            this.lastActive = active;

            this.onActiveChange(active);
        }
    }
    
    @Override
    public void save(final DataSet set, final boolean forSync) 
    {
        super.save(set, forSync);
        set.addInt("maxPower", this.maxPower);
        set.addInt("powerPerOperation", this.powerPerOperation);
        set.addBoolean("lastActive", this.lastActive);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) 
    {
        super.load(set, forSync);
        this.maxPower = set.getInt("maxPower");
        this.powerPerOperation = set.getInt("powerPerOperation");
        this.lastActive = set.getBoolean("lastActive");
    }
	
	public float getEnergyFullness()
	{
		if (this.getPower() == 0)
    	{
    		return 0;
    	}
        return (float)this.getPower()/(float)this.maxPower;
	}

	@Override
	public int getCurrentEnergy() 
	{
		return this.getPower();
	}

	@Override
	public int getMaxEnergy() 
	{
		return this.maxPower;
	}

	@Override
	public boolean addEnergy(int amount) {
		if (this.getPower() <= (this.maxPower - amount))
		{
			this.setPower(this.getPower() + amount);
			return true;
		}
		return false;
	}
	
	public int getPowerPerOperation()
	{
		return this.powerPerOperation;
	}

}
