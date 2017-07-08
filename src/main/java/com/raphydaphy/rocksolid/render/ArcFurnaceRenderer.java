package com.raphydaphy.rocksolid.render;


import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.block.BlockArcFurnace;
import com.raphydaphy.rocksolid.tileentity.TileEntityArcFurnace;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class ArcFurnaceRenderer extends MultiTileRenderer<BlockArcFurnace>
{
	protected final Map<Pos2, IResourceName> texturesActive;
	
	public ArcFurnaceRenderer(final IResourceName texture, final MultiTile tile) {
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
    public void render(final IGameInstance game, final IAssetManager manager, final Graphics g, final IWorld world, final BlockArcFurnace tile, final int x, final int y, final float renderX, final float renderY, final float scale, final Color[] light) {
        final int meta = world.getMeta(x, y);
        final Pos2 innerCoord = tile.getInnerCoord(meta);
        final Pos2 mainPos = tile.getMainPos(x, y, meta);
        final TileEntityArcFurnace tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(), TileEntityArcFurnace.class);
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
