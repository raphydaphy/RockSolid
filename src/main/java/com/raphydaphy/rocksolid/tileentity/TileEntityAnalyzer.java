package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.item.ItemAsteroidDataChip;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityAnalyzer extends TileEntity implements IInventoryHolder
{

	public static final int INPUT = 0;
	public final ContainerInventory inventory;
	private boolean shouldSync = false;

	public TileEntityAnalyzer(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
		this.inventory = new ContainerInventory(this, 1);
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

	public boolean isActive()
	{
		return false;
	}
	
	@Override
	public void update(IGameInstance game)
	{
		ItemInstance card = this.inventory.get(INPUT);
		if (card != null)
		{
			if (card.getAdditionalData() != null)
			{
				if (card.getAdditionalData().getInt("asteroidID") != 0)
				{
					if (card.getAdditionalData().getString("asteroidResource") == null)
					{
						ItemAsteroidDataChip.getChipInfo(card, true);
					}
				}
			}
		}
	}


	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		if (!forSync)
		{
			this.inventory.save(set);
		}
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
		if (input.getItem().equals(RockSolidContent.asteroidDataChip))
		{
			return Arrays.asList(INPUT);
		}
		return new ArrayList<Integer>();
	}

	@Override
	public List<Integer> getOutputSlots(Direction dir)
	{
		return new ArrayList<Integer>();
	}

}
