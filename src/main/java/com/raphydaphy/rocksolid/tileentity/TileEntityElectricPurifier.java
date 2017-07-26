package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;
import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.recipe.PurifierRecipe;
import com.raphydaphy.rocksolid.api.util.IBasicIO;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.init.ModFluids;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityElectricPurifier extends TileEntityPowered implements IBasicIO, IFluidAcceptor
{

	public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    public final ContainerInventory inventory;
    protected int processTime;
    protected int maxProcessTime;
    private int lastSmelt;
    
    protected int fluidStored = 0;
    protected int maxFluid = 5000;
    protected String fluidType = ModFluids.fluidEmpty.toString();
    
    protected int powerStored = 0;
    private boolean shouldSync = false;
    
    public TileEntityElectricPurifier(final IWorld world, final int x, final int y) 
    {
        super(world, x, y, 5000, 3);
        this.inventory = new ContainerInventory(this, 2);
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
        shouldSync = false;
        this.lastSmelt = this.processTime;
    }
    
    @Override
    public boolean tryTickAction() 
    {
    	boolean hasRecipeAndSpace = false;
        final ItemInstance input = this.inventory.get(0);
        if (input != null) 
        {
            final PurifierRecipe recipe = RockSolidAPI.getPurifierRecipe(input, this.fluidType, this.fluidStored);
            if (recipe != null) 
            {
                final ItemInstance recipeIngredient = recipe.getInput();
                if (input.getAmount() >= recipeIngredient.getAmount()) 
                {
                    final ItemInstance recipeOut = recipe.getOutput();
                    final ItemInstance output = this.inventory.get(1);
                    if (output == null || (output.isEffectivelyEqual(recipeOut) && output.getAmount() + recipeOut.getAmount() <= output.getMaxAmount())) 
                    {
                    	hasRecipeAndSpace = true;
                    	if (this.getCurrentEnergy() >= super.getPowerPerOperation())
                    	{
                    		if (RockBottomAPI.getNet().isClient() == false)
							{
		                        if (this.maxProcessTime <= 0) 
		                        {
		                            this.maxProcessTime = recipe.getTime() / 5;
		                        }
		                        ++this.processTime;
		                        shouldSync = true;
							}
	                        if (this.processTime < this.maxProcessTime) 
	                        {
	                            return hasRecipeAndSpace;
	                        }
	                        if (RockBottomAPI.getNet().isClient() == false)
							{
		                        this.inventory.remove(0, recipeIngredient.getAmount());
		                        this.fluidStored -= recipe.getFluidVolume();
		                        if (this.fluidStored == 0)
		                        {
		                        	this.setFluidType(ModFluids.fluidEmpty.toString());
		                        }
		                        if (output == null) 
		                        {
		                            this.inventory.set(1, recipeOut.copy());
		                        }
		                        else 
		                        {
		                            this.inventory.add(1, recipeOut.getAmount());
		                        }
		                        shouldSync = true;
							}
	                        
	                       
                    	}
                		else if (this.processTime > 0) 
                        {
                			if (RockBottomAPI.getNet().isClient() == false)
							{
                				this.processTime = Math.max(this.processTime - 2, 0);
                				shouldSync = true;
							}
                            return hasRecipeAndSpace;
                    	}
                    }
                }
            }
        }
        if (RockBottomAPI.getNet().isClient() == false)
		{
        	this.processTime = 0;
        	this.maxProcessTime = 0;
        	shouldSync = true;
		}
        return hasRecipeAndSpace;
    }
    
    
    public float getSmeltPercentage(){
        return (float)this.processTime/(float)this.maxProcessTime;
    }
    
    public boolean isActive(){
        return this.processTime > 0;
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
        set.addBoolean("shouldSync", this.shouldSync);
        set.addInt("fluidStored", this.fluidStored);
        set.addInt("maxFluid", this.maxFluid);
        set.addString("fluidType", this.fluidType);
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
        this.shouldSync = set.getBoolean("shouldSync");
        this.fluidStored = set.getInt("fluidStored");
        this.maxFluid = set.getInt("maxFluid");
        this.fluidType = set.getString("fluidType");
    }
    

	@Override
	public ContainerInventory getInventory() 
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
		List<Integer> outputSlots = new ArrayList<Integer>();
		outputSlots.add(1);
		return outputSlots;
	}

	@Override
	protected void setPower(int power) 
	{
		this.powerStored = power;
		this.shouldSync = true;
	}

	@Override
	protected int getPower() 
	{
		return this.powerStored;
	}
	
	public float getTankFullnesss()
    {
    	if (fluidStored == 0)
    	{
    		return 0;
    	}
        return (float)this.fluidStored/(float)this.maxFluid;
    }

	@Override
	protected void onActiveChange(boolean active) {
		this.world.causeLightUpdate(this.x, this.y);
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
		if (this.fluidStored + amount <= this.maxFluid)
		{
			if (this.fluidType == null || type.equals(this.fluidType) || this.fluidType.equals(ModFluids.fluidEmpty.toString()))
			{
				this.fluidType = type;
				this.fluidStored += amount;
				this.shouldSync = true;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setFluidType(String type) 
	{
		if (this.fluidType == null || this.fluidType.equals(ModFluids.fluidEmpty.toString()) || this.fluidStored == 0)
		{
			this.fluidType = type;
			return true;
		}
		return false;
	}

	@Override
	public boolean isValidInput(ItemInstance item) 
	{
		return RockSolidAPI.existsInPurifierRecipe(item);
	}

}
