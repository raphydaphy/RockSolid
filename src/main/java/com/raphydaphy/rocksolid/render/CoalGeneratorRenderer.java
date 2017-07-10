package com.raphydaphy.rocksolid.render;


import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.block.BlockCoalGenerator;
import com.raphydaphy.rocksolid.tileentity.TileEntityCoalGenerator;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class CoalGeneratorRenderer extends MultiTileRenderer<BlockCoalGenerator>
{
	protected final Map<Pos2, IResourceName> texturesActive;
	
	protected final Map<Pos2, IResourceName> textures0;
	protected final Map<Pos2, IResourceName> textures1;
	protected final Map<Pos2, IResourceName> textures2;
	protected final Map<Pos2, IResourceName> textures3;
	protected final Map<Pos2, IResourceName> textures4;
	protected final Map<Pos2, IResourceName> textures5;
	protected final Map<Pos2, IResourceName> textures6;
	
	public CoalGeneratorRenderer(final IResourceName texture, final MultiTile tile) {
        super(texture, tile);
        this.texturesActive = new HashMap<Pos2, IResourceName>();
        this.textures0 = new HashMap<Pos2, IResourceName>();
        this.textures1 = new HashMap<Pos2, IResourceName>();
        this.textures2 = new HashMap<Pos2, IResourceName>();
        this.textures3 = new HashMap<Pos2, IResourceName>();
        this.textures4 = new HashMap<Pos2, IResourceName>();
        this.textures5 = new HashMap<Pos2, IResourceName>();
        this.textures6 = new HashMap<Pos2, IResourceName>();
        
        for (int x = 0; x < tile.getWidth(); x++)
        {
	        for (int y = 0; y < tile.getHeight(); ++y) 
	        {
	            if (tile.isStructurePart(x, y)) 
	            {
	            	if (x == 0)
	            	{
	            		this.texturesActive.put(new Pos2(x, y), this.texture.addSuffix(".active." + x + "." + y));
	            	}
	            	else if (x == 2)
	            	{
	            		this.texturesActive.put(new Pos2(x, y), this.texture.addSuffix("." + x + "." + y));
	            		
	            		this.textures0.put(new Pos2(x, y), this.texture.addSuffix("_0." + x + "." + y));
	            	}
	            	else
	            	{
	            		this.texturesActive.put(new Pos2(x, y),  this.texture.addSuffix("." + x + "." + y));
	            	}
	            }
	        }
        }
    }
    
    @Override
    public void render(final IGameInstance game, final IAssetManager manager, final Graphics g, final IWorld world, final BlockCoalGenerator tile, final int x, final int y, final float renderX, final float renderY, final float scale, final Color[] light) {
        final int meta = world.getMeta(x, y);
        final Pos2 innerCoord = tile.getInnerCoord(meta);
        final Pos2 mainPos = tile.getMainPos(x, y, meta);
        final TileEntityCoalGenerator tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(), TileEntityCoalGenerator.class);
        IResourceName tex;
        if (innerCoord.getX() == 0)
        {
        	if (tileEntity != null && tileEntity.isActive()) {
                tex = this.texturesActive.get(innerCoord);
            }
            else {
                tex = this.textures.get(innerCoord);
            }
        }
        else if (innerCoord.getX() == 2)
        {
        	if (tileEntity.getGeneratorFullness() > 0.98)
        	{
        		tex = stageToTex(6, innerCoord);
        	}
        	else if (tileEntity.getGeneratorFullness() > 0.81)
        	{
        		tex = stageToTex(5, innerCoord);
        	}
        	else if (tileEntity.getGeneratorFullness() > 0.64)
        	{
        		tex = stageToTex(4, innerCoord);
        	}
        	else if (tileEntity.getGeneratorFullness() > 0.48)
        	{
        		tex = stageToTex(3, innerCoord);
        	}
        	else if (tileEntity.getGeneratorFullness() > 0.31)
        	{
        		tex = stageToTex(2, innerCoord);
        	}
        	else if (tileEntity.getGeneratorFullness() > 0.14)
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
