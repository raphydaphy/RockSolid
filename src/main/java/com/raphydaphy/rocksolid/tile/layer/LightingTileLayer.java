package com.raphydaphy.rocksolid.tile.layer;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tile.TileLight;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class LightingTileLayer extends TileLayer {

    public LightingTileLayer() {
        super(RockSolid.createRes("lighting_layer"), -100, -100);
        this.register();
    }

    @Override
    public boolean canHoldTileEntities() {
        return true;
    }

    @Override
    public boolean canTileBeInLayer(IWorld world, int x, int y, Tile tile) {
        return tile != null && (tile.isAir() || tile instanceof TileLight);
    }

    @Override
    public boolean canCollide(MovableWorldObject object) {
        return false;
    }

    @Override
    public boolean isVisible(IGameInstance game, AbstractEntityPlayer player, IChunk chunk, int x, int y,
                             boolean isRenderingForeground) {
        return true;
    }

    @Override
    public boolean canEditLayer(IGameInstance game, AbstractEntityPlayer player) {
        return false;
    }

}
