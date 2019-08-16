package com.raphydaphy.rocksolid.tileentity;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityTempshiftPlate extends TileEntity {
    private float capacityModifier = 1;

    public TileEntityTempshiftPlate(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
    }

    @Override
    public boolean doesTick() {
        return false;
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        set.addFloat("assembly_stat_capacity", capacityModifier);
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        capacityModifier = set.getFloat("assembly_stat_capacity");
    }

    public float getCapacityModifier() {
        return capacityModifier;
    }

    public void setCapacityModifier(float capacityModifier) {
        this.capacityModifier = capacityModifier;
        world.setDirty(x, y);
    }
}
