package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.multi.TileElectricSeparator;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricSeparator;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ElectricSeparatorRenderer extends MultiTileRenderer<TileElectricSeparator>
{
    public ElectricSeparatorRenderer(ResourceName texture, TileElectricSeparator tile)
    {
        super(texture, tile);

    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, TileElectricSeparator tile, TileState state)
    {
        Pos2 innerCoord = tile.getInnerCoord(state);
        return manager.getTexture(this.textures.get(innerCoord));
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileElectricSeparator tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
    {
        Pos2 innerCoord = tile.getInnerCoord(state);
        TileEntityElectricSeparator te = tile.getTE(world, state, x, y);
        if (te != null)
        {
            manager.getTexture(this.textures.get(innerCoord)).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

            ResourceName activeFull = this.texture.addSuffix(".active." + innerCoord.getX() + "." + innerCoord.getY());

            // draw fire
            if (te.isActive() && innerCoord.getX() == 0 || innerCoord.getY() == 1)
            {
                manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
            }

            // draw energy bar
            if (innerCoord.getX() == 1 && innerCoord.getY() == 0 && te.getEnergyFullness() > 0)
            {
                int energy = (int) Math.min((te.getEnergyFullness() * te.getEnergyCapacity(null, null)) / (te.getEnergyCapacity(null, null) / 8d), 8);
                float pixel = scale / 12f;

                float yMax = 10;

                float y1 = renderY + pixel * (yMax - energy);
                float y2 = y1 + pixel * energy;

                float srcY = yMax - energy;
                float srcY2 = srcY + energy;

                manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX, y1, renderX + scale, y2, 0, srcY, 12, srcY2, light);
            }
        }
    }

}