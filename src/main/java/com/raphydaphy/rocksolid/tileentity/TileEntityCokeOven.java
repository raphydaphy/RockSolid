package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.tileentity.base.IActivatable;
import com.raphydaphy.rocksolid.util.SlotInfo;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Collections;

public class TileEntityCokeOven extends TileEntity implements IActivatable
{
	private static final String KEY_BLAST_PROGRESS = "blast_progress";
	private final TileInventory inventory = new TileInventory(this, 2, (input) ->
	{
		ArrayList<Integer> avalableSlots = new ArrayList<>(1);
		if (input.getItem().equals(GameContent.TILE_COAL.getItem()))
		{
			avalableSlots.add(0);
		}
		return avalableSlots;
	}, Collections.singletonList(1));

	private int blastProgress = 0;
	private int lastBlastProgress = 0;

	public TileEntityCokeOven(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public TileInventory getTileInventory()
	{
		return this.inventory;
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(KEY_BLAST_PROGRESS, this.blastProgress);
		inventory.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		this.blastProgress = set.getInt(KEY_BLAST_PROGRESS);
		inventory.load(set);
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (!world.isClient())
		{
			if (this.inventory.get(0) != null && this.inventory.get(0).getItem().equals(GameContent.TILE_COAL.getItem()))
			{
				if (this.blastProgress < 5000)
				{
					this.blastProgress++;
				} else
				{
					boolean did = false;

					if (this.inventory.get(1) != null)
					{
						if (this.inventory.get(1).getItem().equals(ModItems.COKE) && this.inventory.get(1).getAmount() < this.inventory.get(1).getMaxAmount())
						{
							this.inventory.get(1).addAmount(1);
							did = true;
						}
					} else
					{
						this.inventory.set(1, new ItemInstance(ModItems.COKE));
						did = true;
					}

					if (did)
					{
						this.inventory.remove(0, 1);
						this.blastProgress = 0;
					}
				}
			} else if (this.blastProgress > 0)
			{
				this.blastProgress -= Math.min(25, this.blastProgress);
			}

			if (this.isActive() != this.lastActive())
			{
				world.causeLightUpdate(x, y);
			}
		}
	}

	public float getBlastPercentage()
	{
		return this.blastProgress / 5000f;
	}

	@Override
	public boolean isActive()
	{
		return this.blastProgress > 0;
	}

	public boolean lastActive()
	{
		return this.lastBlastProgress > 0;
	}

	@Override
	protected boolean needsSync()
	{
		return this.blastProgress != this.lastBlastProgress;
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastBlastProgress = this.blastProgress;
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}
}
