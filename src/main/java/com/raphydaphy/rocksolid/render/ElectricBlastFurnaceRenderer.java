package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.machine.TileElectricBlastFurnace;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricBlastFurnace;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ElectricBlastFurnaceRenderer extends MultiTileRenderer<TileElectricBlastFurnace>
{
	public ElectricBlastFurnaceRenderer(ResourceName texture, TileElectricBlastFurnace tile)
	{
		super(texture, tile);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileElectricBlastFurnace tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);

		Pos2 main = tile.getMainPos(x, y, state);
		TileEntityElectricBlastFurnace te = world.getTileEntity(main.getX(), main.getY(), TileEntityElectricBlastFurnace.class);

		ResourceName activeFull = this.texture.addSuffix(".active." + innerCoord.getX() + "." + innerCoord.getY());

		manager.getTexture(textures.get(innerCoord)).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

		float pixel = scale / 12f;

		if (te.isActive())
		{
			if (innerCoord.getX() == 0)
			{
				manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX + pixel * 6, renderY, renderX + scale, renderY + scale, 6, 0, 12, 12, light);
			}
			else
			{
				manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
			}
		}
		// draw energy bar
		if (innerCoord.getX() == 0 && te.getEnergyFullness() > 0)
		{
			int energy = (int) Math.min((te.getEnergyFullness() * te.getEnergyCapacity(null, null)) / (te.getEnergyCapacity(null, null) / 8d), 8);

			float yMax = 4;

			if (innerCoord.getY() == 0)
			{
				energy = Math.min(energy, 4);
			}
			else
			{
				yMax = 12;
				energy -= 4;
			}

			if (energy > 0)
			{
				float y1 = renderY + pixel * (yMax - energy);
				float y2 = y1 + pixel * energy;

				float srcY = yMax - energy;
				float srcY2 = srcY + energy;

				manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX, y1, renderX + pixel * 6, y2, 0, srcY, 6, srcY2, light);
			}
		}
	}

}
