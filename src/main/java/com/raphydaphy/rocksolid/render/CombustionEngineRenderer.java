package com.raphydaphy.rocksolid.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.init.ModFluids;
import com.raphydaphy.rocksolid.tile.TileCombustionEngine;
import com.raphydaphy.rocksolid.tileentity.TileEntityCombustionEngine;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class CombustionEngineRenderer extends MultiTileRenderer<TileCombustionEngine>
{

	public CombustionEngineRenderer(final IResourceName texture, final TileCombustionEngine tile)
	{
		super(texture, tile);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, TileCombustionEngine tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light)
	{
		final Pos2 innerCoord = tile.getInnerCoord(state);
		final Pos2 mainPos = tile.getMainPos(x, y, state);
		TileEntityCombustionEngine tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(), TileEntityCombustionEngine.class);
		IResourceName fluidTank = RockSolidLib.makeRes("tiles.tank");
		IResourceName tex = this.texture.addSuffix("." + innerCoord.getX() + "." + innerCoord.getY());
		manager.getTexture(tex).drawWithLight(renderX, renderY, scale, scale, light);
		float fullness = tileEntity.getFluidTankFullness();
		int stage = Util.floor(fullness * 20);
		if (stage > 0 && innerCoord.getX() == 0 && innerCoord.getY() < 2)
		{
			if (stage > 20)
			{
				stage = 20;
			}
			
			
			if (innerCoord.getY() == 1)
			{
				if (stage < 10)
				{
					return;
				}
				else
				{
					stage -= 10;
				}
			}
			else if (innerCoord.getY() == 0) 
			{ 
				if (stage >10) 
				{ 
					stage = 10; 
				}
			}
			if (tileEntity.getFluidType().equals(ModFluids.fluidWater.toString()))
			{
				tex = fluidTank.addSuffix(".fluidWater." + innerCoord.getX() + "." + innerCoord.getY());
			} else if (tileEntity.getFluidType().equals(ModFluids.fluidLava.toString()))
			{
				tex = fluidTank.addSuffix(".fluidLava." + innerCoord.getX() + "." + innerCoord.getY());
			}else if (tileEntity.getFluidType().equals(ModFluids.fluidOil.toString()))
			{
				tex = fluidTank.addSuffix(".fluidOil." + innerCoord.getX() + "." + innerCoord.getY());
			}
			
			float scaleY = (float)(scale / 12);
			
			float startY = (scale) - (scaleY * stage);
		
			if (innerCoord.getY() == 0)
			{
				startY -= (2 * scaleY);
			}
			manager.getTexture(tex).drawWithLight(renderX, renderY + startY, renderX + scale, renderY + startY + (scaleY * stage), 0, 0, 12, stage, light, null);


		}
	}

}