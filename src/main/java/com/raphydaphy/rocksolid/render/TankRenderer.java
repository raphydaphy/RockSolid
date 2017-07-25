package com.raphydaphy.rocksolid.render;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.init.ModFluids;
import com.raphydaphy.rocksolid.tile.TileTank;
import com.raphydaphy.rocksolid.tileentity.TileEntityTank;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TankRenderer extends MultiTileRenderer<TileTank>
{
	
	public TankRenderer(final IResourceName texture, final TileTank tile) {
        super(texture, tile);
        
    }
    
    @Override
    public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, TileTank tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light)
    {
        final Pos2 innerCoord = tile.getInnerCoord(state);
        final Pos2 mainPos = tile.getMainPos(x, y, state);
        TileEntityTank tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(), TileEntityTank.class);
        IResourceName tex = this.texture.addSuffix("." + innerCoord.getX() + "." + innerCoord.getY());
        float fullness = tileEntity.getTankFullnesss();
        int stage = Util.floor(fullness * 20);
        if (stage > 0)
        {
        	if (stage == 21)
        	{
        		stage = 20;
        	}
	        if (tileEntity.getFluidType().equals(ModFluids.fluidWater.toString()))
	        {
	        	tex = this.texture.addSuffix(".fluidWater." + stage + "." + innerCoord.getX() + "." + innerCoord.getY());
	        }
	        else if (tileEntity.getFluidType().equals(ModFluids.fluidLava.toString()))
	        {
	        	tex = this.texture.addSuffix(".fluidLava." + stage + "." + innerCoord.getX() + "." + innerCoord.getY());
	        }
	        
        }
        
        manager.getTexture(tex).drawWithLight(renderX, renderY, scale, scale, light);
    }

}
