package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ParentedNameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Collections;
import java.util.List;

public class CompressingRecipe extends MachineRecipe {
    public static final ParentedNameRegistry<CompressingRecipe> REGISTRY = new ParentedNameRegistry<>(RockSolid.createRes("compressing_registry"), true, MachineRecipe.MACHINE_RECIPES).register();
    public final IUseInfo in;
    public final ItemInstance out;
    public final int time;
    private final ResourceName name;

    public CompressingRecipe(String inRes, int inAmount, Item out, int time) {
        this(RockSolid.createRes(inRes + "_compressing"), inRes, inAmount, out, time);
    }

    public CompressingRecipe(ResourceName name, String inRes, int inAmount, Item out, int time) {
        this(name, new ResUseInfo(inRes, inAmount), new ItemInstance(out), time);
    }

    public CompressingRecipe(ResourceName name, IUseInfo in, ItemInstance out, int time) {
        super(name);
        this.name = name;
        this.in = in;
        this.out = out;
        this.time = time;
    }

    public static CompressingRecipe forInput(ItemInstance input) {
        if (input != null) {
            for (CompressingRecipe recipe : REGISTRY.values()) {
                if (recipe.in.containsItem(input)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    public CompressingRecipe register() {
        REGISTRY.register(this.name, this);
        return this;
    }

    @Override
    public List<IUseInfo> getInputs() {
        return Collections.singletonList(in);
    }

    @Override
    public List<ItemInstance> getOutputs() {
        return Collections.singletonList(out);
    }
}
