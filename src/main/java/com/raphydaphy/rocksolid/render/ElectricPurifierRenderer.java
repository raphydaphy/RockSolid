package com.raphydaphy.rocksolid.render;


import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.tile.TileElectricPurifier;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricPurifier;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class ElectricPurifierRenderer extends MultiTileRenderer<TileElectricPurifier>
{
	protected final Map<Pos2, IResourceName> texturesActive;
	
	public ElectricPurifierRenderer(final IResourceName texture, final MultiTile tile) {
        super(texture, tile);
        this.texturesActive = new HashMap<Pos2, IResourceName>();
        for (int x = 0; x < tile.getWidth(); ++x) {
            for (int y = 0; y < tile.getHeight(); ++y) {
                if (tile.isStructurePart(x, y)) {
                    this.texturesActive.put(new Pos2(x, y), this.texture.addSuffix(".active." + x + "." + y));
                }
            }
        }
    }
    
    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, TileElectricPurifier tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light)
    {
        final Pos2 innerCoord = tile.getInnerCoord(state);
        final Pos2 mainPos = tile.getMainPos(x, y, state);
        final TileEntityElectricPurifier tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(), TileEntityElectricPurifier.class);
        IResourceName tex;
        if (tileEntity != null && tileEntity.isActive()) {
            tex = this.texturesActive.get(innerCoord);
        }
        else {
            tex = this.textures.get(innerCoord);
        }
        manager.getTexture(tex).drawWithLight(renderX, renderY, scale, scale, light);
    }

}
