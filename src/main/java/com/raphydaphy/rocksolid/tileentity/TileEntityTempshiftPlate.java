package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.tile.machine.TileNuclearReactor;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityAssemblyConfigurable;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityTempshiftPlate extends TileEntityAssemblyConfigurable
{
	public TileEntityTempshiftPlate(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);

	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (!world.isClient() && world.getTotalTime() % (Math.round(4 / getSpeedModifier())) == 0)
		{
			TileState state = world.getState(x, y);
			TileEntityNuclearReactor reactor = ((TileNuclearReactor) state.getTile()).getTE(world, state, x, y);
			reactor.removeHeat(Math.round(4 * getEfficiencyModifier() * getCapacityModifier()));
		}
	}

	@Override
	protected boolean needsSync()
	{
		return super.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}
}
