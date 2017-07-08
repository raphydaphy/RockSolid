package com.raphydaphy.rocksolid.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class ConduitRenderer<T extends Tile> extends DefaultTileRenderer<T>
{
	public ConduitRenderer(IResourceName texture) {
		super(texture);
	}
	
	@Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, T tile, int x, int y, float renderX, float renderY, float scale, Color[] light){
        manager.getTexture(super.texture).drawWithLight(renderX, renderY, scale, scale, light);
        
        if (RockSolidLib.getTileFromPos(x + 1, y, world) != null)
        {
        	manager.getTexture(this.texture).drawWithLight(renderX, renderY, scale * 2, scale, light);
        }
        
        if (RockSolidLib.getTileFromPos(x - 1, y, world) != null)
        {
        	manager.getTexture(this.texture).drawWithLight(renderX - 70, renderY, scale * 2, scale, light);
        }
        
        if (RockSolidLib.getTileFromPos(x, y + 1, world) != null)
        {
        	manager.getTexture(this.texture).drawWithLight(renderX, renderY - 70, scale, scale * 2, light);
        }
        
        if (RockSolidLib.getTileFromPos(x, y - 1, world) != null)
        {
        	manager.getTexture(this.texture).drawWithLight(renderX, renderY, scale, scale * 2, light);
        }
    }


}
