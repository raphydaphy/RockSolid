package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.recipe.CompressingRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Collections;

public class TileEntityElectricCompressor extends TileEntityElectricFurnace {

    private final TileInventory inventory = new TileInventory(this, 2, (input) -> {
        ArrayList<Integer> avalableSlots = new ArrayList<>(1);
        if (CompressingRecipe.forInput(input) != null) {
            avalableSlots.add(0);
        }
        return avalableSlots;
    }, Collections.singletonList(1));

    public TileEntityElectricCompressor(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer);
    }

    @Override
    public TileInventory getTileInventory() {
        return this.inventory;
    }

    @Override
    protected void getRecipeAndStart() {
        ItemInstance item;
        CompressingRecipe recipe;
        if ((item = this.inventory.get(0)) != null && (recipe = CompressingRecipe.forInput(item)) != null) {
            IUseInfo input = recipe.in;
            if (item.getAmount() >= input.getAmount()) {
                item = recipe.out;
                ItemInstance curOutputSlot;
                if (((curOutputSlot = this.inventory.get(1)) == null || curOutputSlot.isEffectivelyEqual(item) && curOutputSlot.fitsAmount(item.getAmount()))) {
                    this.output = item.copy();

                    double chance = Math.pow(2, 5 * (getBonusYieldModifier() / 2f)); // Bonus Yield Modifier
                    if ((Util.RANDOM.nextDouble() * 100) < chance && (curOutputSlot == null || curOutputSlot.fitsAmount(item.getAmount() + 1))) {
                        this.output.addAmount(1);
                    }

                    this.maxSmeltTime.set((int) ((recipe.time / 5f) / getSpeedModifier())); // speed multiplier
                    this.inventory.remove(0, input.getAmount());
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

        if ((outSlot = this.inventory.get(1)) != null && outSlot.isEffectivelyEqual(this.output)) {
            this.inventory.add(1, this.output.getAmount());
        } else {
            this.inventory.set(1, this.output);
        }

        this.output = null;
    }

    @Override
    public int getEnergyCapacity(IWorld world, Pos2 pos) {
        return maxEnergyStored.get();
    }
}
