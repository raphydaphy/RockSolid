package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.api.IHasInventory;
import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.recipe.BlastFurnaceRecipe;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityBlastFurnace extends TileEntity implements IHasInventory
{

	public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    public final ContainerInventory inventory;
    protected int processTime;
    protected int maxProcessTime;
    private int lastSmelt;
    
    private boolean running = false;
    private boolean lastRunning;
    
    public TileEntityBlastFurnace(final IWorld world, final int x, final int y) 
    {
        super(world, x, y);
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
        this.lastSmelt = this.processTime;
    }
    
    @Override
    public void update(IGameInstance game) 
    {
        final ItemInstance input = this.inventory.get(0);
        if (input != null) 
        {
            final BlastFurnaceRecipe recipe = RockSolidAPI.getArcFurnaceRecipe(input);
            if (recipe != null) 
            {
                final ItemInstance recipeIngredient = recipe.getInput();
                if (input.getAmount() >= recipeIngredient.getAmount()) 
                {
                    final ItemInstance recipeOut = recipe.getOutput();
                    final ItemInstance output = this.inventory.get(1);
                    if (output == null || (output.isEffectivelyEqual(recipeOut) && output.getAmount() + recipeOut.getAmount() <= output.getMaxAmount())) 
                    {
                    	if (this.processTime == 0)
                    	{
                    		this.world.causeLightUpdate(this.x, this.y);
                    	}
                        if (this.maxProcessTime <= 0) 
                        {
                            this.maxProcessTime = recipe.getTime();
                        }
                        ++this.processTime;
                        if (this.processTime < this.maxProcessTime) 
                        {
                            return;
                        }
                        this.inventory.remove(0, recipeIngredient.getAmount());
                        if (output == null) 
                        {
                            this.inventory.set(1, recipeOut.copy());
                        }
                        else 
                        {
                            this.inventory.add(1, recipeOut.getAmount());
                        }
                        this.world.causeLightUpdate(this.x, this.y);
                    }
                }
            }
        }
        this.processTime = 0;
        this.maxProcessTime = 0;
    }
    
    public float getSmeltPercentage(){
        return (float)this.processTime/(float)this.maxProcessTime;
    }
    
    public boolean isActive(){
        return this.processTime > 0;
    }
    
    @Override
    public void save(final DataSet set, final boolean forSync) {
        super.save(set, forSync);
        if (!forSync) {
            this.inventory.save(set);
        }
        set.addInt("process", this.processTime);
        set.addInt("max_process", this.maxProcessTime);
        set.addBoolean("running", this.running);
        set.addBoolean("lastRunning", this.lastRunning);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) {
        super.load(set, forSync);
        if (!forSync) {
            this.inventory.load(set);
        }
        this.processTime = set.getInt("process");
        this.maxProcessTime = set.getInt("max_process");
        this.running = set.getBoolean("running");
        this.lastRunning = set.getBoolean("lastRunning");
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

}
