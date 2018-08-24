package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.machine.TileElectricFurnace;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricFurnace;
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

public class ElectricFurnaceRenderer extends MultiTileRenderer<TileElectricFurnace>
{
    public ElectricFurnaceRenderer(ResourceName texture, TileElectricFurnace tile)
    {
        super(texture, tile);

    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, TileElectricFurnace tile, TileState state)
    {
        Pos2 innerCoord = tile.getInnerCoord(state);
        return manager.getTexture(this.textures.get(innerCoord));
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileElectricFurnace tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
    {
        Pos2 innerCoord = tile.getInnerCoord(state);
        TileEntityElectricFurnace te = tile.getTE(world, state, x, y);
        if (te != null)
        {
            manager.getTexture(this.textures.get(innerCoord)).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

            ResourceName activeFull = this.texture.addSuffix(".active." + innerCoord.getX() + "." + innerCoord.getY());

            float pixel = scale / 12f;

            // draw fire
            if (te.isActive() && innerCoord.getY() == 0)
            {
                if (innerCoord.getX() == 0)
                {
                    float x1 = renderX + pixel * 6;

                    manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX + pixel * 6, renderY, renderX + scale, renderY + scale, 6, 0, 12, 12, light);
                }
                else
                {
                    manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
                }
            }

            // draw energy bar
            if (innerCoord.getX() == 0 && innerCoord.getY() == 0 && te.getEnergyFullness() > 0)
            {
                int energy = (int) Math.min((te.getEnergyFullness() * te.getEnergyCapacity(null, null)) / (te.getEnergyCapacity(null, null) / 8d), 8);

                float yMax = 10;

                float y1 = renderY + pixel * (yMax - energy);
                float y2 = y1 + pixel * energy;

                float srcY = yMax - energy;
                float srcY2 = srcY + energy;

                manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX, y1, renderX + pixel * 6, y2, 0, srcY, 6, srcY2, light);
            }
        }
    }

}