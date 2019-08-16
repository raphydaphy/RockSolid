package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.BiomeBasic;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;

public abstract class MoonBiome extends BiomeBasic {
    public MoonBiome(ResourceName name, int weight, BiomeLevel... levels) {
        super(name, weight, levels);
    }

    @Override
    public Biome register() {
        Registries.BIOME_REGISTRY.register(this.getName(), this);
        ModMisc.MOON_BIOME_REGISTRY.add(this);
        return this;
    }

    @Override
    public boolean shouldGenerateInWorld(IWorld world) {
        return world.getSubName() != null && world.getSubName().equals(ModMisc.MOON_WORLD);
    }
}
