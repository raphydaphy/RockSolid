package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.util.FilteredTileInventory;
import com.raphydaphy.rocksolid.util.SlotInfo;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityArcFurnace extends TileEntity
{
	private static final String KEY_BLAST_PROGRESS = "blast_progress";
	public final FilteredTileInventory inventory = new FilteredTileInventory(this, SlotInfo.makeList(
		new SlotInfo.SimpleSlotInfo(SlotInfo.SlotType.INPUT, instance -> instance.getItem().equals(GameContent.TILE_COAL.getItem())),
		new SlotInfo.SimpleSlotInfo(SlotInfo.SlotType.INPUT, (ItemInstance instance) -> false)));
	private int blastProgress = 0;
	private int lastBlastProgress = 0;

	public TileEntityArcFurnace(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public FilteredTileInventory getInventory()
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
	}

	public float getBlastPercentage()
	{
		return this.blastProgress / 500;
	}

	public boolean isActive()
	{
		return this.blastProgress > 0;
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
}
