package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModRecipes;
import com.raphydaphy.rocksolid.tileentity.TileEntityAssemblyStation;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.compendium.construction.ConstructionRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;

import java.util.Collections;
import java.util.List;

public class AssemblyRecipe extends ConstructionRecipe {
    private boolean capacity = true;
    private boolean efficiency = true;
    private boolean speed = true;
    private boolean bonusYield = true;
    private boolean throughput = true;

    private ItemInstance output;
    private IUseInfo metal;

    public AssemblyRecipe(float skillReward, ItemInstance output, int baseAmount, int metalAmount, int fuelAmount) {
        this(skillReward, new ResUseInfo(ModMisc.RES_ALL_INGOTS, metalAmount), output, baseAmount, fuelAmount);
    }

    public AssemblyRecipe(float skillReward, IUseInfo metal, ItemInstance output, int baseAmount, int fuelAmount) {
        super(output.getItem().getName(), null, false, skillReward, output, new ResUseInfo(ModMisc.RES_MACHINE_MATERIALS, baseAmount), metal, new ResUseInfo(ModMisc.RES_ALL_FUELS, fuelAmount));
        this.output = output;
        this.metal = metal;
    }

    public IUseInfo getMetal() {
        return this.metal;
    }

    public AssemblyRecipe registerAssembly() {
        ModRecipes.ASSEMBLY_STATION_RECIPES.register(this.getName(), this);
        return this;
    }

    public AssemblyRecipe disableCapacity() {
        capacity = false;
        return this;
    }

    public AssemblyRecipe disableEfficiency() {
        efficiency = false;
        return this;
    }

    public AssemblyRecipe disableSpeed() {
        speed = false;
        return this;
    }

    public AssemblyRecipe disableBonusYield() {
        bonusYield = false;
        return this;
    }

    public AssemblyRecipe disableThroughput() {
        throughput = false;
        return this;
    }

    public boolean hasCapacity() {
        return capacity;
    }

    public boolean hasEfficiency() {
        return efficiency;
    }

    public boolean hasSpeed() {
        return speed;
    }

    public boolean hasBonusYield() {
        return bonusYield;
    }

    public boolean hasThroughput() {
        return throughput;
    }

    @Override
    public List<ItemInstance> getActualOutputs(IInventory inputInventory, IInventory outputInventory, List<ItemInstance> inputs) {
        ItemInstance nbtOut = output.copy();

        nbtOut.getOrCreateAdditionalData().addFloat(ModUtils.ASSEMBLY_CAPACITY_KEY, hasCapacity() ? ModUtils.getAssemblyCapacity(inputs) * 2 : -1);
        nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_EFFICIENCY_KEY, hasEfficiency() ? ModUtils.getAssemblyEfficiency(inputs) * 2 : -1);
        nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_SPEED_KEY, hasSpeed() ? ModUtils.getAssemblySpeed(inputs) * 2 : -1);
        nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_BONUS_KEY, hasBonusYield() ? ModUtils.getAssemblyBonusYield(inputs) * 2 : -1);
        nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_THROUGHPUT_KEY, hasThroughput() ? ModUtils.getAssemblyThroughput(inputs) * 2 : -1);

        return Collections.singletonList(nbtOut);
    }

    // TODO: use handleMachine for assembly
    public void playerConstruct(AbstractEntityPlayer player, TileEntityAssemblyStation machine, int amount) {
        Inventory in = machine.getInvHidden();
        Inventory out = player.getInv();
        List<ItemInstance> remains = RockBottomAPI.getApiHandler().construct(player, in, out, this, machine, amount, this.getActualInputs(out), null, items -> this.getActualOutputs(in, out, items), this.getSkillReward());
        for (ItemInstance instance : remains) {
            AbstractEntityItem.spawn(player.world, instance, player.getX(), player.getY(), 0F, 0F);
        }
    }
}
