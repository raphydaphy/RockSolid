package com.raphydaphy.rocksolid.api.recipe;

import de.ellpeck.rockbottom.api.item.ItemInstance;

public class AlloySmelterRecipe 
{
	private final ItemInstance input1;
	private final ItemInstance input2;
    private final ItemInstance output;
    private final int time;

    public AlloySmelterRecipe(ItemInstance output, ItemInstance input1, ItemInstance input2, int time){
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.time = time;
    }

    public ItemInstance getInput1(){
        return this.input1;
    }
    public ItemInstance getInput2(){
        return this.input2;
    }

    public ItemInstance getOutput(){
        return this.output;
    }

    public int getTime(){
        return this.time;
    }
}
