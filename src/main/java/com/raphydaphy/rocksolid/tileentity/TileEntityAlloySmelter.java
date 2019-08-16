package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.recipe.AlloyingRecipe;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityFueledBase;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Collections;

public class TileEntityAlloySmelter extends TileEntityFueledBase {
    private static final String KEY_OUTPUT = "output";

    private final TileInventory inventory = new TileInventory(this, 4, (input) ->
    {
        ArrayList<Integer> avalableSlots = new ArrayList<>(3);
        if (FuelInput.getFuelTime(input) > 0) {
            avalableSlots.add(0);
        }
        if (AlloyingRecipe.forInput(input, this.inventory.get(2), true) != null) {
            avalableSlots.add(1);
        }
        if (AlloyingRecipe.forInput(input, this.inventory.get(1), true) != null) {
            avalableSlots.add(2);
        }
        return avalableSlots;
    }, Collections.singletonList(3));

    private ItemInstance output;

    public TileEntityAlloySmelter(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
    }

    @Override
    protected void getRecipeAndStart() {
        ItemInstance item1 = this.inventory.get(1);
        ItemInstance item2 = this.inventory.get(2);
        AlloyingRecipe recipe;
        if (item1 != null && item2 != null && (recipe = AlloyingRecipe.forInput(item1, item2, false)) != null) {
            IUseInfo input1 = recipe.in1;
            IUseInfo input2 = recipe.in2;
            if (item1.getAmount() >= input1.getAmount() && item2.getAmount() >= input2.getAmount()) {
                ItemInstance rOut = recipe.out;
                ItemInstance var4;
                if (((var4 = this.inventory.get(3)) == null || var4.isEffectivelyEqual(rOut) && var4.fitsAmount(rOut.getAmount()))) {
                    this.maxSmeltTime.set(recipe.time);
                    this.output = rOut.copy();
                    this.inventory.remove(1, input1.getAmount());
                    this.inventory.remove(2, input2.getAmount());
                }
            }
        }

        if (this.maxSmeltTime.get() <= 0) {
            this.output = null;
        }
    }

    @Override
    protected void putOutputItems() {
        ItemInstance outSlot;

        if ((outSlot = this.inventory.get(3)) != null && outSlot.isEffectivelyEqual(this.output)) {
            this.inventory.add(3, this.output.getAmount());
        } else {
            this.inventory.set(3, this.output);
        }

        this.output = null;
    }

    @Override
    public TileInventory getTileInventory() {
        return this.inventory;
    }

    @Override
    public void save(DataSet set, boolean forSync) {
        super.save(set, forSync);
        inventory.save(set);
        if (!forSync) {
            if (this.output != null) {
                DataSet tmpSet = new DataSet();
                this.output.save(tmpSet);
                set.addDataSet(KEY_OUTPUT, tmpSet);
            }
        }
    }

    @Override
    public void load(DataSet set, boolean forSync) {
        super.load(set, forSync);
        inventory.load(set);
        if (!forSync) {
            if (set.hasKey(KEY_OUTPUT)) {
                DataSet tepSet = set.getDataSet(KEY_OUTPUT);
                this.output = ItemInstance.load(tepSet);
            }
        }
    }

    @Override
    protected ItemInstance getFuel() {
        return this.getTileInventory().get(0);
    }

    @Override
    protected void removeFuel() {
        this.getTileInventory().remove(0, 1);
    }

    @Override
    protected void onActiveChange(boolean active) {
        world.causeLightUpdate(this.x, this.y);
    }

}
