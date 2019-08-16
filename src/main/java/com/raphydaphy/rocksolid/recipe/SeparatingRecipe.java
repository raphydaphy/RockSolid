package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModItems;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ParentedNameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SeparatingRecipe extends MachineRecipe {
    public static final ParentedNameRegistry<SeparatingRecipe> REGISTRY = new ParentedNameRegistry<>(RockSolid.createRes("separator_registry"), true, MachineRecipe.MACHINE_RECIPES).register();
    public final IUseInfo in;
    public final ItemInstance out;
    public final ItemInstance biproduct;
    public final int biproductChance;
    public final int time;
    private final ResourceName name;

    public SeparatingRecipe(String inRes, Item out, int time) {
        this(RockSolid.createRes(inRes + "_separation"), inRes, out, time);
    }

    public SeparatingRecipe(ResourceName name, String inRes, Item out, int time) {
        this(name, new ResUseInfo(inRes), new ItemInstance(out), new ItemInstance(ModItems.SLAG), 3, time);
    }

    public SeparatingRecipe(ResourceName name, IUseInfo in, ItemInstance out, ItemInstance biproduct, int biproductChance, int time) {
        super(name);
        this.name = name;
        this.in = in;
        this.out = out;
        this.biproduct = biproduct;
        this.biproductChance = biproductChance;
        this.time = time;
    }

    public static SeparatingRecipe forInput(ItemInstance input) {
        if (input != null) {
            for (SeparatingRecipe recipe : REGISTRY.values()) {
                if (recipe.in.containsItem(input)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    public SeparatingRecipe register() {
        REGISTRY.register(this.name, this);
        return this;
    }

    @Override
    public List<IUseInfo> getInputs() {
        return Collections.singletonList(in);
    }

    @Override
    public List<ItemInstance> getOutputs() {
        return Arrays.asList(out, biproduct);
    }
}
