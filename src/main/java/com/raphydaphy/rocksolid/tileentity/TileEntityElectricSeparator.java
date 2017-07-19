package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.api.TileEntityPowered;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.SeparatorRecipe;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityElectricSeparator extends TileEntityPowered
{
    public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    public static final int BYPRODUCT = 2;
    public final ContainerInventory inventory;
    protected int smeltTime;
    protected int maxSmeltTime;
    private int lastSmelt;
    private int powerStored;
    private boolean shouldSync = false;
   
    public TileEntityElectricSeparator(final IWorld world, final int x, final int y) {
        super(world, x, y, 5000, 25);
        this.inventory = new ContainerInventory(this, 4);
    }
   
    @Override
    protected boolean needsSync() {
        return super.needsSync() || this.lastSmelt != this.smeltTime || this.shouldSync;
    }
   
    @Override
    protected void onSync() {
        super.onSync();
        this.lastSmelt = this.smeltTime;
        shouldSync = false;
    }
   
    @Override
    protected boolean tryTickAction() {
        boolean hasRecipeAndSpace = false;
        final ItemInstance input = this.inventory.get(0);
        if (input != null) {
            final SeparatorRecipe recipe = RockBottomAPI.getSeparatorRecipe(input);
            if (recipe != null) {
                final ItemInstance recipeIn = recipe.getInput();
                if (input.getAmount() >= recipeIn.getAmount()) {
                    final ItemInstance recipeOut = recipe.getOutput();
                    final ItemInstance output = this.inventory.get(1);
                    if (output == null || (output.isEffectivelyEqual(recipeOut) && output.fitsAmount(recipeOut.getAmount()))) {
                        final ItemInstance recipeBy = recipe.getByproduct();
                        final ItemInstance byproduct = this.inventory.get(2);
                        if (recipeBy == null || byproduct == null || (byproduct.isEffectivelyEqual(recipeBy) && byproduct.fitsAmount(recipeBy.getAmount()))) {
                            hasRecipeAndSpace = true;
                            if (this.powerStored > 0) {
                            	if (RockBottomAPI.getNet().isClient() == false)
								{
	                                if (this.maxSmeltTime <= 0) {
	                                    this.maxSmeltTime = recipe.getTime() / 5;
	                                }
	                                ++this.smeltTime;
	                                this.shouldSync = true;
								}
                                if (this.smeltTime < this.maxSmeltTime) {
                                    return hasRecipeAndSpace;
                                }
                                if (RockBottomAPI.getNet().isClient() == false)
								{
	                                this.inventory.remove(0, recipeIn.getAmount());
	                                if (output == null) {
	                                    this.inventory.set(1, recipeOut.copy());
	                                }
	                                else {
	                                    this.inventory.add(1, recipeOut.getAmount());
	                                }
	                                if (recipeBy != null && Util.RANDOM.nextFloat() <= recipe.getByproductChance()) {
	                                    if (byproduct == null) {
	                                        this.inventory.set(2, recipeBy.copy());
	                                    }
	                                    else {
	                                        this.inventory.add(2, recipeBy.getAmount());
	                                    }
	                                }
	                                this.shouldSync = true;
								}
                                
                            }
                            else if (this.smeltTime > 0) {
                            	if (RockBottomAPI.getNet().isClient() == false)
								{
                            		this.smeltTime = Math.max(this.smeltTime - 2, 0);
                            		this.shouldSync = true;
								}
                                return hasRecipeAndSpace;
                            }
                        }
                    }
                }
            }
        }
        if (RockBottomAPI.getNet().isClient() == false)
		{
	        this.smeltTime = 0;
	        this.maxSmeltTime = 0;
	        this.shouldSync = true;
		}
        return hasRecipeAndSpace;
    }
   
    
   
    @Override
    protected void onActiveChange(final boolean active) {
        this.world.causeLightUpdate(this.x, this.y);
    }
   
    public float getSmeltPercentage() {
    	return (float)this.smeltTime/(float)this.maxSmeltTime;
    }
   
    @Override
    public void save(final DataSet set, final boolean forSync) {
        super.save(set, forSync);
        if (!forSync) {
            this.inventory.save(set);
        }
        set.addInt("smelt", this.smeltTime);
        set.addInt("max_smelt", this.maxSmeltTime);
        set.addInt("powerStored", this.powerStored);
        set.addBoolean("shouldSync", this.shouldSync);
    }
   
    @Override
    public void load(final DataSet set, final boolean forSync) {
        super.load(set, forSync);
        if (!forSync) {
            this.inventory.load(set);
        }
        this.smeltTime = set.getInt("smelt");
        this.maxSmeltTime = set.getInt("max_smelt");
        this.powerStored = set.getInt("powerStored");
        this.shouldSync = set.getBoolean("shouldSync");
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
		outputSlots.add(2);
		return outputSlots;
	}
    
    @Override
	protected void setPower(int power) 
	{
		this.powerStored = power;
	}

	@Override
	protected int getPower() 
	{
		return this.powerStored;
	}
	
	public boolean isActive(){
        return this.smeltTime > 0;
    }
}