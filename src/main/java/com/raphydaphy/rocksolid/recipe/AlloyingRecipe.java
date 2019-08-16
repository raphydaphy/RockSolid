package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ParentedNameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AlloyingRecipe extends MachineRecipe {
    public static final ParentedNameRegistry<AlloyingRecipe> REGISTRY = new ParentedNameRegistry<>(RockSolid.createRes("alloy_smelting_registry"), true, MachineRecipe.MACHINE_RECIPES).register();
    public final IUseInfo in1;
    public final IUseInfo in2;
    public final ItemInstance out;
    public final int time;
    private final ResourceName name;

    public AlloyingRecipe(String in1, int quantity1, String in2, int quantity2, Item out, int quantityOut, int time) {
        this(new ResUseInfo(in1, quantity1), new ResUseInfo(in2, quantity2), new ItemInstance(out, quantityOut), time);
    }

    public AlloyingRecipe(IUseInfo in1, IUseInfo in2, ItemInstance out, int time) {
        this(out.getItem().getName(), in1, in2, out, time);
    }

    public AlloyingRecipe(ResourceName name, IUseInfo in1, IUseInfo in2, ItemInstance out, int time) {
        super(name);
        this.name = name;
        this.in1 = in1;
        this.in2 = in2;
        this.out = out;
        this.time = time;
    }

    public static AlloyingRecipe forInput(ItemInstance input1, ItemInstance input2, boolean ignoreNull) {
        for (AlloyingRecipe recipe : REGISTRY.values()) {
            if (input1 != null && input2 != null) {
                if ((recipe.in1.containsItem(input1) && recipe.in2.containsItem(input2))) {
                    return recipe;
                } else if ((recipe.in2.containsItem(input1) && recipe.in1.containsItem(input2))) {
                    return new AlloyingRecipe(recipe.in2, recipe.in1, recipe.out, recipe.time);
                }
            }
            if (input1 != null && ignoreNull && input2 == null && (recipe.in1.containsItem(input1) || recipe.in2.containsItem(input1))) {
                return recipe;
            }
        }
        return null;
    }

    public AlloyingRecipe register() {
        REGISTRY.register(this.name, this);
        return this;
    }

    @Override
    public List<IUseInfo> getInputs() {
        return Arrays.asList(in1, in2);
    }

    @Override
    public List<ItemInstance> getOutputs() {
        return Collections.singletonList(out);
    }
}
