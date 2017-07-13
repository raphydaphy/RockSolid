package com.raphydaphy.rocksolid.render;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.block.BlockCharger;
import com.raphydaphy.rocksolid.tileentity.TileEntityCharger;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class ChargerRenderer extends MultiTileRenderer<BlockCharger>
{
	
    public ChargerRenderer(final IResourceName texture, final MultiTile tile) 
    {
        super(texture, tile);
    }


    @Override
    public void render(final IGameInstance game, final IAssetManager manager, final Graphics g, final IWorld world, final BlockCharger tile, final int x, final int y, final float renderX, final float renderY, final float scale, final Color[] light) 
    {
    	final int meta = world.getMeta(x, y);
        final Pos2 innerCoord = tile.getInnerCoord(meta);
        final Pos2 mainPos = tile.getMainPos(x, y, meta);
        final TileEntityCharger tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(), TileEntityCharger.class);
        IResourceName tex;
        
       
        if (innerCoord.getX() == 2)
        {
        	if (tileEntity.getEnergyFullness() > 0.98)
        	{
        		tex = stageToTex(6, innerCoord);
        	}
        	else if (tileEntity.getEnergyFullness() > 0.81)
        	{
        		tex = stageToTex(5, innerCoord);
        	}
        	else if (tileEntity.getEnergyFullness() > 0.64)
        	{
        		tex = stageToTex(4, innerCoord);
        	}
        	else if (tileEntity.getEnergyFullness() > 0.48)
        	{
        		tex = stageToTex(3, innerCoord);
        	}
        	else if (tileEntity.getEnergyFullness() > 0.31)
        	{
        		tex = stageToTex(2, innerCoord);
        	}
        	else if (tileEntity.getEnergyFullness() > 0.14)
        	{
        		tex = stageToTex(1, innerCoord);
        	}
        	else
        	{
        		tex = this.textures.get(innerCoord);
        	}
        }
        else
        {
        	tex = this.textures.get(innerCoord);
        }
        
        if (innerCoord.getX() == 2 && innerCoord.getY() == 1)
        {
        	if (tileEntity.getInventory().get(0)!= null)
        	{
        		if (tileEntity.getInventory().get(0).getItem() != null)
        		{
        			Item theItemToRender = tileEntity.getInventory().get(0).getItem();
        			manager.getTexture(RockSolidLib.makeRes(theItemToRender.getName().addPrefix("items.").getResourceName().toString())).drawWithLight(renderX - (scale*1.3f), renderY+(scale*0.92f), scale*0.5f , scale * 0.5f, light);
        		}
        	}
        }
        
        manager.getTexture(tex).drawWithLight(renderX, renderY, scale, scale, light);
    }
    
    private IResourceName stageToTex(int stage, Pos2 innerCoord)
    {
    	if (stage < 4)
    	{
    		if (innerCoord.getY() == 0)
    		{
    			return this.texture.addSuffix("." + stage + "." + innerCoord.getX() + "." + innerCoord.getY());
    		}
    	}
    	else if (stage > 3)
    	{
    		if (innerCoord.getY() == 1)
    		{
    			return this.texture.addSuffix("." + stage + "." + innerCoord.getX() + "." + innerCoord.getY());
    		}
    		return this.texture.addSuffix(".4.2.0");
    	}
    	return this.textures.get(innerCoord);
    }
}
