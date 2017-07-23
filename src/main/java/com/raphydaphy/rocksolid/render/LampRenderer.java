package com.raphydaphy.rocksolid.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class LampRenderer<T extends Tile> extends DefaultTileRenderer<T>
{
	public LampRenderer(IResourceName texture) {
		super(texture);
	}
	
	@Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light){
        
        
        manager.getTexture(this.texture).drawWithLight(renderX, renderY, scale, scale, light);
        
        // right
        if (state.getTile() != null)
        {
        	if (world.getState(x + 1, y).getTile() != GameContent.TILE_AIR)
            {
            	manager.getTexture(this.texture.addSuffix(".right")).drawWithLight(renderX, renderY, scale, scale, light);
            }
        }
        
        // left
        if (state.getTile() != null)
        {
	        if (world.getState(x - 1, y).getTile() != GameContent.TILE_AIR)
	        {
	        	manager.getTexture(this.texture.addSuffix(".left")).drawWithLight(renderX, renderY, scale, scale, light);
	        }
        }
        
        // up
        if (state.getTile() != null)
        {
		    if (world.getState(x, y + 1).getTile() != GameContent.TILE_AIR)
		    {
		    	manager.getTexture(this.texture.addSuffix(".up")).drawWithLight(renderX, renderY, scale, scale, light);
		    }
        }
        
        // down
        if (state.getTile() != null)
        {
	        if (world.getState(x, y - 1).getTile() != GameContent.TILE_AIR)
	        {
	        	manager.getTexture(this.texture.addSuffix(".down")).drawWithLight(renderX, renderY, scale, scale, light);
	        }
        }
        
    }


}
