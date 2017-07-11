package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.api.IHasInventory;
import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntityFueled;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityAlloySmelter extends TileEntityFueled implements IHasInventory
{

	public static final int INPUT1 = 0;
	public static final int INPUT2 = 1;
    public static final int OUTPUT = 3;
    public static final int COAL = 2;
    public final ContainerInventory inventory;
    protected int processTime;
    protected int maxProcessTime;
    private int lastSmelt;
    
    public TileEntityAlloySmelter(final IWorld world, final int x, final int y) 
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
    
    @Override
    protected boolean tryTickAction() 
    {
        boolean hasRecipeAndSpace = false;
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
                        hasRecipeAndSpace = true;
                        if (this.coalTime > 0) 
                        {
                            if (this.maxProcessTime <= 0) 
                            {
                                this.maxProcessTime = recipe.getTime();
                            }
                            ++this.processTime;
                            if (this.processTime < this.maxProcessTime) 
                            {
                                return hasRecipeAndSpace;
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
                            return hasRecipeAndSpace;
                        }
                    }
                }
            }
        }
        this.processTime = 0;
        this.maxProcessTime = 0;
        return hasRecipeAndSpace;
    }
    
    @Override
    protected float getFuelModifier() 
    {
        return 1.0f;
    }
    
    @Override
    protected ItemInstance getFuel() 
    {
        return this.inventory.get(2);
    }
    
    @Override
    protected void removeFuel() 
    {
        this.inventory.remove(2, 1);
    }
    
    @Override
    protected void onActiveChange(final boolean active) 
    {
        this.world.causeLightUpdate(this.x, this.y);
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

}
