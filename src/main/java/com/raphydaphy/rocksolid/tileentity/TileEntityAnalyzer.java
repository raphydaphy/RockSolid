package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.util.IBasicIO;
import com.raphydaphy.rocksolid.api.util.TileEntityProgressBar;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.item.ItemAsteroidDataChip;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityAnalyzer extends TileEntityProgressBar implements IBasicIO
{

	public static final int INPUT = 0;
	public static final int OUTPUT = 1;

	public final ContainerInventory inventory;
	private boolean shouldSync = false;

	protected int smeltTime;
	protected int maxSmeltTime;
	private int lastSmelt;

	public TileEntityAnalyzer(final IWorld world, final int x, final int y, TileLayer layer)
	{
		super(world, x, y, layer);
		this.inventory = new ContainerInventory(this, 2);
	}

	@Override
	protected boolean needsSync()
	{
		return super.needsSync() || this.lastSmelt != this.smeltTime || shouldSync;
	}

	@Override
	protected void onSync()
	{
		super.onSync();
		shouldSync = false;
		this.lastSmelt = this.smeltTime;
	}

	@Override
	public boolean tryTickAction()
	{
		ItemInstance card = this.inventory.get(INPUT);
		ItemInstance output = this.inventory.get(OUTPUT);

		if (output == null && card != null)
		{
			if (card != null)
			{
				if (card.getAdditionalData() != null)
				{
					if (card.getAdditionalData().getInt("asteroidID") != 0)
					{
						if (card.getAdditionalData().getString("asteroidResource") == null)
						{
							if (RockBottomAPI.getNet().isClient() == false)
							{
								if (this.maxSmeltTime <= 0)
								{
									this.maxSmeltTime = 100;
								}
								++this.smeltTime;
								this.shouldSync = true;
							}
							if (this.smeltTime < this.maxSmeltTime)
							{
								return true;
							}
							if (RockBottomAPI.getNet().isClient() == false)
							{
								ItemInstance finalCard = this.inventory.get(INPUT).copy();
								ItemAsteroidDataChip.getChipInfo(finalCard, true);
								this.inventory.remove(INPUT, 1);
								this.inventory.set(OUTPUT, finalCard);
								this.smeltTime = 0;
								shouldSync = true;
							}
						}

					}
				}
			}
		}
		return false;
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
	public List<Integer> getInputSlots(ItemInstance input, Direction dir)
	{
		if (this.isValidInput(input))
		{
			return Arrays.asList(INPUT);
		}
		return new ArrayList<Integer>();
	}

	@Override
	public List<Integer> getOutputSlots(Direction dir)
	{
		return Arrays.asList(1);
	}

	@Override
	public boolean isValidInput(ItemInstance item)
	{
		return item.getItem().equals(RockSolidContent.asteroidDataChip);
	}

	@Override
	protected void onActiveChange(boolean active)
	{
	}

	@Override
	public boolean isActive()
	{
		return false;
	}

	@Override
	public float getSmeltPercentage()
	{
		return (float) this.smeltTime / (float) this.maxSmeltTime;
	}

}
