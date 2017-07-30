package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;
import com.raphydaphy.rocksolid.api.util.IBasicIO;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.SmelterRecipe;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityElectricSmelter extends TileEntityPowered implements IBasicIO
{
	public static final int INPUT = 0;
	public static final int OUTPUT = 1;
	public final ContainerInventory inventory;
	protected int smeltTime;
	protected int maxSmeltTime;
	private int lastSmelt;
	private int powerStored;
	private boolean shouldSync;

	public TileEntityElectricSmelter(final IWorld world, final int x, final int y)
	{
		super(world, x, y, 5000, 20);
		this.inventory = new ContainerInventory(this, 3);
	}

	@Override
	protected boolean needsSync()
	{
		return super.needsSync() || this.lastSmelt != this.smeltTime || this.shouldSync;
	}

	@Override
	protected void onSync()
	{
		super.onSync();
		this.lastSmelt = this.smeltTime;
		this.shouldSync = false;
	}

	@Override
	protected boolean tryTickAction()
	{
		boolean hasRecipeAndSpace = false;
		final ItemInstance input = this.inventory.get(0);
		if (input != null)
		{
			final SmelterRecipe recipe = RockBottomAPI.getSmelterRecipe(input);
			if (recipe != null)
			{
				final ItemInstance recipeIn = recipe.getInput();
				if (input.getAmount() >= recipeIn.getAmount())
				{
					final ItemInstance recipeOut = recipe.getOutput();
					final ItemInstance output = this.inventory.get(1);
					if (output == null || (output.isEffectivelyEqual(recipeOut)
							&& output.getAmount() + recipeOut.getAmount() <= output.getMaxAmount()))
					{
						hasRecipeAndSpace = true;
						if (this.powerStored > 0)
						{
							if (RockBottomAPI.getNet().isClient() == false)
							{
								if (this.maxSmeltTime <= 0)
								{
									this.maxSmeltTime = recipe.getTime() / 5;
								}
								++this.smeltTime;
								this.shouldSync = true;
							}
							if (this.smeltTime < this.maxSmeltTime)
							{
								return hasRecipeAndSpace;
							}
							if (RockBottomAPI.getNet().isClient() == false)
							{
								this.inventory.remove(0, recipeIn.getAmount());
								if (output == null)
								{
									this.inventory.set(1, recipeOut.copy());
								} else
								{
									this.inventory.add(1, recipeOut.getAmount());
								}
								shouldSync = true;
							}
						} else if (this.smeltTime > 0)
						{
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
		if (RockBottomAPI.getNet().isClient() == false)
		{
			this.smeltTime = 0;
			this.maxSmeltTime = 0;
			this.shouldSync = true;
		}
		return hasRecipeAndSpace;
	}

	@Override
	protected void onActiveChange(final boolean active)
	{
		this.world.causeLightUpdate(this.x, this.y);
	}

	public float getSmeltPercentage()
	{
		return (float) this.smeltTime / (float) this.maxSmeltTime;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		if (!forSync)
		{
			this.inventory.save(set);
		}
		set.addInt("smelt", this.smeltTime);
		set.addInt("max_smelt", this.maxSmeltTime);
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
		this.smeltTime = set.getInt("smelt");
		this.maxSmeltTime = set.getInt("max_smelt");
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

	public boolean isActive()
	{
		return this.smeltTime > 0;
	}

	@Override
	public boolean isValidInput(ItemInstance item)
	{
		return RockBottomAPI.getSmelterRecipe(item) != null;
	}
}