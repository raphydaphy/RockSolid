package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityElectric;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Arrays;
import java.util.List;

public class TileEntityRefinery extends TileEntityElectric implements IFluidTile<TileEntityRefinery> {
    private SyncedInt oil = new SyncedInt("water");
    private SyncedInt fuel = new SyncedInt("steam");

    public TileEntityRefinery(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
        this.maxEnergyStored.set(2500);
    }

    @Override
    protected void getRecipeAndStart() {
        if (this.oil.get() >= 1 && this.fuel.get() < getFluidCapacity(world, null, ModTiles.FUEL)) {
            this.maxSmeltTime.set(Math.round(6 / getSpeedModifier()));
            this.oil.remove(1);
            world.setDirty(x, y);
        }
    }

    @Override
    protected void putOutputItems() {
        this.fuel.add(1);
        double chance = Math.pow(2, 5 * (getBonusYieldModifier() / 2f)); // Bonus Yield Modifier
        if (this.fuel.get() < getFluidCapacity(world, null, ModTiles.FUEL) && (Util.RANDOM.nextDouble() * 100) < chance) {
            world.setDirty(x, y);
            this.fuel.add(1);
        }
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        oil.save(set);
        fuel.save(set);
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        oil.load(set);
        fuel.load(set);
    }

    @Override
    protected boolean needsSync() {
        return super.needsSync() || oil.needsSync() || fuel.needsSync();
    }

    @Override
    public void onSync() {
        super.onSync();
        oil.onSync();
        fuel.onSync();
    }

    public float getFuelFullness() {
        return (float) this.fuel.get() / (float) getFluidCapacity(world, null, ModTiles.FUEL);
    }

    public float getOilFullness() {
        return (float) this.oil.get() / (float) getFluidCapacity(world, null, ModTiles.OIL);
    }

    @Override
    public boolean addFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate) {
        if (liquid.equals(ModTiles.OIL) && ml + this.oil.get() <= getFluidCapacity(world, pos, ModTiles.OIL)) {
            if (!simulate) {
                this.oil.add(ml);
                world.setDirty(x, y);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate) {
        if (liquid != null && liquid.equals(ModTiles.FUEL) && this.fuel.get() >= ml) {
            if (!simulate) {
                this.fuel.remove(ml);
                world.setDirty(x, y);
            }
            return true;
        }
        return false;
    }

    @Override
    public int getFluidCapacity(IWorld world, Pos2 pos, TileLiquid liquid) {
        return Math.round(300f / getCapacityModifier());
    }

    @Override
    public List<TileLiquid> getLiquidsAt(IWorld world, Pos2 pos) {
        return Arrays.asList(ModTiles.OIL, ModTiles.FUEL);
    }

    @Override
    public boolean doesTick() {
        return true;
    }

    public int getFuelVolume() {
        return fuel.get();
    }

    public int getOilVolume() {
        return oil.get();
    }
}
