package com.raphydaphy.rocksolid.api.util;


import java.util.Arrays;
import java.util.List;

import de.ellpeck.rockbottom.api.tile.state.TileProp;

public class EnumProp<T extends Enum<T>> extends TileProp<T>{

    private final T def;
    private final List<T> allowedValues;

    public EnumProp(String name, T def, Class<? extends T> enumClass){
        this(name, def, enumClass.getEnumConstants());
    }

    public EnumProp(String name, T def, T... allowedValues){
        this(name, def, Arrays.asList(allowedValues));
    }

    public EnumProp(String name, T def, List<T> allowedValues){
        super(name);
        this.def = def;
        this.allowedValues = allowedValues;
    }

    @Override
    public int getVariants(){
        return this.allowedValues.size();
    }

    @Override
    public T getValue(int index){
        return this.allowedValues.get(index);
    }

    @Override
    public int getIndex(T value){
        return this.allowedValues.indexOf(value);
    }

    @Override
    public T getDefault(){
        return this.def;
    }
}
