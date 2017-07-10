package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.util.IEnergyAcceptor;
import com.raphydaphy.rocksolid.util.IHasInventory;
import com.raphydaphy.rocksolid.util.RockSolidAPI;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityElectricAlloySmelter extends TileEntity implements IHasInventory, IEnergyAcceptor
{

	public static final int INPUT1 = 0;
	public static final int INPUT2 = 1;
    public static final int OUTPUT = 3;
    public final ContainerInventory inventory;
    protected int processTime;
    protected int maxProcessTime;
    private int lastSmelt;
    
    protected int powerStored = 0;
    protected int maxPower = 5000;
    protected int powerPerOperation = 20;
    
    public TileEntityElectricAlloySmelter(final IWorld world, final int x, final int y) 
    {
        super(world, x, y);
        this.inventory = new ContainerInventory(this, 4);
    }
    
    @Override
    protected boolean needsSync() 
    {
        return super.needsSync() || this.lastSmelt != this.processTime;
    }
    
    @Override
    protected void onSync() 
    {
        super.onSync();
        this.lastSmelt = this.processTime;
    }
    
    public boolean isActive(){
        return this.processTime > 0;
    }
    
    @Override
    public void update(IGameInstance game) 
    {
        final ItemInstance input1 = this.inventory.get(0);
        final ItemInstance input2 = this.inventory.get(1);
        if (input1 != null && input2 != null) 
        {
            final AlloySmelterRecipe recipe = RockSolidAPI.getAlloySmelterRecipe(input1, input2);
            if (recipe != null) 
            {
                final ItemInstance recipeIngredient1 = recipe.getInput1();
                final ItemInstance recipeIngredient2 = recipe.getInput2();
                if (input1.getAmount() >= recipeIngredient1.getAmount() &&
                	input2.getAmount() >= recipeIngredient2.getAmount()) 
                {
                    final ItemInstance recipeOut = recipe.getOutput();
                    final ItemInstance output = this.inventory.get(3);
                    if (output == null || (output.isEffectivelyEqual(recipeOut) && output.getAmount() + recipeOut.getAmount() <= output.getMaxAmount())) 
                    {
                        if (this.getCurrentEnergy() >= this.powerPerOperation) 
                        {
                            if (this.maxProcessTime <= 0) 
                            {
                                this.maxProcessTime = recipe.getTime() / 5;
                            }
                            ++this.processTime;
                            this.powerStored -= this.powerPerOperation;
                            if (this.processTime < this.maxProcessTime) 
                            {
                                return;
                            }
                            this.inventory.remove(0, recipeIngredient1.getAmount());
                            this.inventory.remove(1, recipeIngredient2.getAmount());
                            if (output == null) 
                            {
                                this.inventory.set(3, recipeOut.copy());
                            }
                            else 
                            {
                                this.inventory.add(3, recipeOut.getAmount());
                            }
                        }
                        else if (this.processTime > 0) 
                        {
                            this.processTime = Math.max(this.processTime - 2, 0);
                            return;
                        }
                    }
                }
            }
        }
        this.processTime = 0;
        this.maxProcessTime = 0;
        return;
    }
    
    public float getSmeltPercentage(){
        return (float)this.processTime/(float)this.maxProcessTime;
    }
    
    @Override
    public void save(final DataSet set, final boolean forSync) 
    {
        super.save(set, forSync);
        if (!forSync) {
            this.inventory.save(set);
        }
        set.addInt("process", this.processTime);
        set.addInt("max_process", this.maxProcessTime);
        set.addInt("powerStored", this.powerStored);
        set.addInt("maxPower", this.maxPower);
        set.addInt("powerPerOperation", this.powerPerOperation);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) 
    {
        super.load(set, forSync);
        if (!forSync) {
            this.inventory.load(set);
        }
        this.processTime = set.getInt("process");
        this.maxProcessTime = set.getInt("max_process");
        this.powerStored = set.getInt("powerStored");
        this.maxPower = set.getInt("maxPower");
        this.powerPerOperation = set.getInt("powerPerOperation");
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
		insertSlots.add(1);
		insertSlots.add(2);
		return insertSlots;
	}

	@Override
	public List<Integer> getOutputs() 
	{
		List<Integer> outputSlots = new ArrayList<Integer>();
		outputSlots.add(3);
		return outputSlots;
	}
	
	public float getEnergyFullness()
	{
		if (powerStored == 0)
    	{
    		return 0;
    	}
        return (float)this.powerStored/(float)this.maxPower;
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
	public boolean addEnergy(int amount) {
		if (this.powerStored <= (this.maxPower - amount))
		{
			this.powerStored += amount;
			return true;
		}
		return false;
	}


}
