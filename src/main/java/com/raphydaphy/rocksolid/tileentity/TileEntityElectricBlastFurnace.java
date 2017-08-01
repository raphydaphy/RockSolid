package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;
import com.raphydaphy.rocksolid.api.recipe.BlastFurnaceRecipe;
import com.raphydaphy.rocksolid.api.util.IBasicIO;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityElectricBlastFurnace extends TileEntityPowered implements IBasicIO
{

	public static final int INPUT = 0;
	public static final int OUTPUT = 1;
	public final ContainerInventory inventory;
	protected int processTime;
	protected int maxProcessTime;
	private int lastSmelt;

	protected int powerStored = 0;
	private boolean shouldSync = false;

	public TileEntityElectricBlastFurnace(final IWorld world, final int x, final int y)
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
			final BlastFurnaceRecipe recipe = RockSolidAPI.getArcFurnaceRecipe(input);
			if (recipe != null)
			{
				final ResUseInfo recipeIngredient = recipe.getInput();
				if (input.getAmount() >= recipeIngredient.getAmount())
				{
					final ItemInstance recipeOut = recipe.getOutput();
					final ItemInstance output = this.inventory.get(1);
					if (output == null || (output.isEffectivelyEqual(recipeOut)
							&& output.getAmount() + recipeOut.getAmount() <= output.getMaxAmount()))
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
								if (output == null)
								{
									this.inventory.set(1, recipeOut.copy());
								} else
								{
									this.inventory.add(1, recipeOut.getAmount());
								}
								shouldSync = true;
							}

						} else if (this.processTime > 0)
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

	public float getSmeltPercentage()
	{
		return (float) this.processTime / (float) this.maxProcessTime;
	}

	public boolean isActive()
	{
		return this.processTime > 0;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		if (!forSync)
		{
			this.inventory.save(set);
		}
		set.addInt("process", this.processTime);
		set.addInt("max_process", this.maxProcessTime);
		set.addInt("powerStored", this.powerStored);
		set.addBoolean("shouldSync", this.shouldSync);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		if (!forSync)
		{
			this.inventory.load(set);
		}
		this.processTime = set.getInt("process");
		this.maxProcessTime = set.getInt("max_process");
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

	@Override
	protected void onActiveChange(boolean active)
	{
		this.world.causeLightUpdate(this.x, this.y);
	}

	@Override
	public boolean isValidInput(ItemInstance item)
	{
		return RockSolidAPI.getArcFurnaceRecipe(item) != null;
	}

}
