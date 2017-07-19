package com.raphydaphy.rocksolid.render;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.block.BlockAlloySmelter;
import com.raphydaphy.rocksolid.tileentity.TileEntityAlloySmelter;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class AlloySmelterRenderer<T extends BlockAlloySmelter> extends MultiTileRenderer<T>
{
	private final IResourceName texActive;
	
    public AlloySmelterRenderer(final IResourceName texture, final MultiTile tile) 
    {
        super(texture, tile);
        this.texActive = this.texture.addSuffix(".active");
    }


    @Override
    public void render(final IGameInstance game, final IAssetManager manager, final Graphics g, final IWorld world, final T tile, final int x, final int y, final float renderX, final float renderY, final float scale, final Color[] light) 
    {
        if (tile.isMainPos(x, y, world.getMeta(x, y))) 
        {
            final TileEntityAlloySmelter tileEntity = world.getTileEntity(x, y, TileEntityAlloySmelter.class);
            if (tileEntity != null && tileEntity.isActive()) 
            {
                manager.getTexture(this.texActive).drawWithLight(renderX, renderY, scale, scale, light);
                return;
            }
        }
        super.render(game, manager, g, world, tile, x, y, renderX, renderY, scale, light);
    }
}
