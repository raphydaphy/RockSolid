package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tileentity.base.IActivatable;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ActivatableRenderer extends MultiTileRenderer<MultiTile> {
    public ActivatableRenderer(ResourceName texture, MultiTile tile) {
        super(texture, tile);
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, MultiTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        Pos2 innerCoord = tile.getInnerCoord(state);

        Pos2 main = tile.getMainPos(x, y, state);
        TileEntity te = world.getTileEntity(main.getX(), main.getY(), TileEntity.class);

        if (te instanceof IActivatable) {
            ResourceName tex = this.textures.get(innerCoord);
            if (((IActivatable) te).isActive()) {
                tex = this.texture.addSuffix(".active." + innerCoord.getX() + "." + innerCoord.getY());
            }
            manager.getTexture(tex).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
        }
    }

}
