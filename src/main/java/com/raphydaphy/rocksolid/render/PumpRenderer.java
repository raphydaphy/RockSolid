package com.raphydaphy.rocksolid.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.init.ModFluids;
import com.raphydaphy.rocksolid.tile.TileFluidPump;
import com.raphydaphy.rocksolid.tileentity.TileEntityFluidPump;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class PumpRenderer extends MultiTileRenderer<TileFluidPump>
{

	public PumpRenderer(final IResourceName texture, final MultiTile tile)
	{
		super(texture, tile);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, TileFluidPump tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light)
	{
		final Pos2 innerCoord = tile.getInnerCoord(state);
		final Pos2 mainPos = tile.getMainPos(x, y, state);
		final TileEntityFluidPump tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(),
				TileEntityFluidPump.class);
		IResourceName tex = this.textures.get(innerCoord);
		if (tileEntity != null && tileEntity.getCurrentFluid() >= 1000)
		{
			int fullness = Util.floor(tileEntity.getFluidTankFullness() * 10);

			if (innerCoord.getY() == 1)
			{
				String fluid = "fluidEmpty";
				if (tileEntity.getFluidType().equals(ModFluids.fluidWater.toString()))
				{
					fluid = "fluidWater";
				} else if (tileEntity.getFluidType().equals(ModFluids.fluidLava.toString()))
				{
					fluid = "fluidLava";
				}
				tex = this.texture
						.addSuffix("." + fluid + "." + fullness + "." + innerCoord.getX() + "." + innerCoord.getY());
			}

		}
		manager.getTexture(tex).drawWithLight(renderX, renderY, scale, scale, light);
	}

}
