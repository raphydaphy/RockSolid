package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tile.machine.TileRefinery;
import com.raphydaphy.rocksolid.tileentity.TileEntityRefinery;
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

public class RefineryRenderer extends MultiTileRenderer<TileRefinery> {

    public RefineryRenderer(ResourceName texture, TileRefinery tile) {
        super(texture, tile);

    }

    @Override
    public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, TileRefinery tile, TileState state) {
        Pos2 innerCoord = tile.getInnerCoord(state);
        return manager.getTexture(this.textures.get(innerCoord));
    }

    @Override
    public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileRefinery tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light) {
        Pos2 innerCoord = tile.getInnerCoord(state);
        TileEntityRefinery te = tile.getTE(world, state, x, y);
        if (te != null) {
            manager.getTexture(this.textures.get(innerCoord)).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

            ResourceName activeFull = this.texture.addSuffix(".active." + innerCoord.getX() + "." + innerCoord.getY());
            if (te.getFuelFullness() > 0 && innerCoord.getX() != 0) {
                int FUEL = (int) Math.min((te.getFuelFullness() * te.getFluidCapacity(null, null, ModTiles.FUEL)) / (te.getFluidCapacity(null, null, ModTiles.FUEL) / 16d), 16);
                boolean render = false;

                float pixel = scale / 12f;

                int localFuel = FUEL;
                float yMax = 12;

                float y1;
                float y2;

                float srcY;
                float srcY2;

                float x1 = renderX + pixel * 10;
                float x2 = x1 + pixel * 2;

                int srcX = 10;
                int srcX2 = 12;

                if (innerCoord.getX() == 2) {
                    x1 = renderX;
                    x2 = renderX + pixel * 2;

                    srcX = 0;
                    srcX2 = 2;
                }

                if (innerCoord.getY() == 0) {
                    localFuel = Math.min(8, FUEL);
                    yMax = 8;
                    render = true;
                } else if (innerCoord.getY() == 1 && FUEL > 8) {
                    localFuel -= 8;
                    render = true;
                }

                if (render) {
                    y1 = renderY + pixel * (yMax - localFuel);
                    y2 = y1 + pixel * localFuel;

                    srcY = yMax - localFuel;
                    srcY2 = srcY + localFuel;


                    manager.getTexture(activeFull).getPositionalVariation(x, y).draw(x1, y1, x2, y2, srcX, srcY, srcX2, srcY2, light);
                }
            }
            if (te.getOilFullness() > 0 && innerCoord.getX() != 2) {
                int OIL = (int) Math.min((te.getOilFullness() * te.getFluidCapacity(null, null, ModTiles.OIL)) / (te.getFluidCapacity(null, null, ModTiles.OIL) / 16d), 16);
                boolean render = false;

                float pixel = scale / 12f;

                int localOil = OIL;
                float yMax = 12;

                float y1;
                float y2;

                float srcY;
                float srcY2;

                float x1 = renderX + pixel * 10;
                float x2 = x1 + pixel * 2;

                int srcX = 10;
                int srcX2 = 12;

                if (innerCoord.getX() == 1) {
                    x1 = renderX;
                    x2 = renderX + pixel * 2;

                    srcX = 0;
                    srcX2 = 2;
                }

                if (innerCoord.getY() == 0) {
                    localOil = Math.min(8, OIL);
                    yMax = 8;
                    render = true;
                } else if (innerCoord.getY() == 1 && OIL > 8) {
                    localOil -= 8;
                    render = true;
                }

                if (render) {
                    y1 = renderY + pixel * (yMax - localOil);
                    y2 = y1 + pixel * localOil;

                    srcY = yMax - localOil;
                    srcY2 = srcY + localOil;


                    manager.getTexture(activeFull).getPositionalVariation(x, y).draw(x1, y1, x2, y2, srcX, srcY, srcX2, srcY2, light);
                }
            }
            if (te.getEnergyFullness() > 0 && innerCoord.getX() == 0) {
                int ENERGY = (int) Math.min((te.getEnergyFullness() * te.getEnergyCapacity(null, null)) / (te.getEnergyCapacity(null, null) / 9d), 9);

                boolean render = false;

                float pixel = scale / 12f;

                int localEnergy = ENERGY;
                float yMax = 12;

                float y1;
                float y2;

                float srcY;
                float srcY2;

                if (innerCoord.getY() == 0) {
                    localEnergy = Math.min(6, ENERGY);
                    yMax = 5;
                    render = true;
                } else if (innerCoord.getY() == 1 && ENERGY > 5) {
                    localEnergy -= 5;
                    render = true;
                }

                if (render) {
                    y1 = renderY + pixel * (yMax - localEnergy);
                    y2 = y1 + pixel * localEnergy;

                    srcY = yMax - localEnergy;
                    srcY2 = srcY + localEnergy;


                    manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX + pixel * 2, y1, renderX + pixel * 4, y2, 2, srcY, 4, srcY2, light);
                }
            }
        }
    }

}
