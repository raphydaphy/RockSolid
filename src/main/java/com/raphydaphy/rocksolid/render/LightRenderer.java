package com.raphydaphy.rocksolid.render;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class LightRenderer<TileLight> extends DefaultTileRenderer {

    public LightRenderer(ResourceName texture) {
        super(texture);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, Tile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
    }

}
