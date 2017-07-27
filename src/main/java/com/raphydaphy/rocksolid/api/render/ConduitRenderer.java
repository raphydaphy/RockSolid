package com.raphydaphy.rocksolid.api.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class ConduitRenderer<T extends Tile> extends DefaultTileRenderer<T>
{
	public ConduitRenderer(IResourceName texture) {
		super(texture);
	}
	
	@Override
    public void render (IGameInstance game, IAssetManager manager, Graphics g, IWorld world, T tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light){
        
        
        TileEntity conduit = RockSolidLib.getTileFromPos(x, y, world);
        manager.getTexture(this.texture).drawWithLight(renderX, renderY, scale, scale, light);
        // right
        if (RockSolidLib.getTileFromPos(x + 1, y, world) != null)
        {
        	if (((IConduit)conduit).canConnectTo(RockSolidLib.getTileFromPos(x + 1, y, world).getClass()) && ((IConduit)conduit).getSideMode(3) != 2)
            {
            	manager.getTexture(this.texture.addSuffix(".right")).drawWithLight(renderX, renderY, scale, scale, light);
            }
        }
        
        // left
        if (RockSolidLib.getTileFromPos(x - 1, y, world) != null)
        {
	        if (((IConduit)conduit).canConnectTo(RockSolidLib.getTileFromPos(x - 1, y, world).getClass()) && ((IConduit)conduit).getSideMode(2) != 2)
	        {
	        	manager.getTexture(this.texture.addSuffix(".left")).drawWithLight(renderX, renderY, scale, scale, light);
	        }
        }
        
        // up
        if (RockSolidLib.getTileFromPos(x, y + 1, world) != null)
        {
		    if (((IConduit)conduit).canConnectTo(RockSolidLib.getTileFromPos(x, y + 1, world).getClass()) && ((IConduit)conduit).getSideMode(0) != 2)
		    {
		    	manager.getTexture(this.texture.addSuffix(".up")).drawWithLight(renderX, renderY, scale, scale, light);
		    }
        }
        
        // down
        if (RockSolidLib.getTileFromPos(x, y - 1, world) != null)
        {
	        if (((IConduit)conduit).canConnectTo(RockSolidLib.getTileFromPos(x, y - 1, world).getClass()) && ((IConduit)conduit).getSideMode(1) != 2)
	        {
	        	manager.getTexture(this.texture.addSuffix(".down")).drawWithLight(renderX, renderY, scale, scale, light);
	        }
        }
        
    }


}
