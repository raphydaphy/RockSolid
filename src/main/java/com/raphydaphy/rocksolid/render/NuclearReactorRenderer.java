package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.machine.TileElectricBlastFurnace;
import com.raphydaphy.rocksolid.tile.machine.TileNuclearReactor;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricBlastFurnace;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class NuclearReactorRenderer extends MultiTileRenderer<TileNuclearReactor>
{
	public NuclearReactorRenderer(ResourceName texture, TileNuclearReactor tile)
	{
		super(texture, tile);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileNuclearReactor tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);

		Pos2 main = tile.getMainPos(x, y, state);
		TileEntityNuclearReactor te = world.getTileEntity(main.getX(), main.getY(), TileEntityNuclearReactor.class);

		ResourceName activeFull = this.texture.addSuffix(".active." + innerCoord.getX() + "." + innerCoord.getY());

		manager.getTexture(textures.get(innerCoord)).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

		float pixel = scale / 12f;

		if (te.isActive() && innerCoord.getX() > 0 && innerCoord.getX() < 4 && (innerCoord.getY() == 1 || innerCoord.getY() == 2))
		{
			if (innerCoord.getY() == 1)
			{
				manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX, renderY, renderX + scale, renderY + pixel * 11, 0, 0, 12, 11, light);
			}
			else
			{
				manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
			}
		}
		// draw energy bar
		if ((innerCoord.getY() == 0 || innerCoord.getY() == 1) && innerCoord.getX() > 0 && innerCoord.getX() < 4 && te.getEnergyFullness() > 0)
		{
			int energy = (int) Math.min((te.getEnergyFullness() * te.getEnergyCapacity(null, null)) / (te.getEnergyCapacity(null, null) / 16d), 16);

			float xStart = 0;

			if (innerCoord.getX() == 1)
			{
				xStart = 10;
				energy = Math.min(energy, 2);
			}
			else if (innerCoord.getX() == 3)
			{
				energy -= 14;
			}
			else
			{
				energy -= 2;
				energy = Math.min(energy, 12);
			}

			if (energy > 0)
			{
				float x1 = renderX + pixel * xStart;
				float x2 = x1 + pixel * energy;

				float srcX2 = xStart + energy;

				manager.getTexture(activeFull).getPositionalVariation(x, y).draw(x1, renderY + (innerCoord.getY() == 1 ? pixel * 9 : 0), x2, renderY + pixel * 3 + (innerCoord.getY() == 1 ? pixel * 9 : 0), xStart, innerCoord.getY() == 1 ? 9 : 0, srcX2, innerCoord.getY() == 1 ? 12 : 3, light);
			}
		}
	}

}
