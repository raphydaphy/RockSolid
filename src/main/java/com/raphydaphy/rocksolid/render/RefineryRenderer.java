package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.machine.TileRefinery;
import com.raphydaphy.rocksolid.tileentity.TileEntityRefinery;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class RefineryRenderer extends MultiTileRenderer<TileRefinery>
{

	public RefineryRenderer(ResourceName texture, TileRefinery tile)
	{
		super(texture, tile);

	}

	@Override
	public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, TileRefinery tile, TileState state)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);
		return manager.getTexture(this.textures.get(innerCoord));
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileRefinery tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);
		TileEntityRefinery te = tile.getTE(world, state, x, y);
		if (te != null)
		{
			manager.getTexture(this.textures.get(innerCoord)).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

			ResourceName activeFull = this.texture.addSuffix(".active." + innerCoord.getX() + "." + innerCoord.getY());
			if (te.getEnergyFullness() > 0 && innerCoord.getX() == 0)
			{
				int ENERGY = (int) Math.min((te.getEnergyFullness() * te.getEnergyCapacity(null, null)) / (te.getEnergyCapacity(null, null) / 9d), 9);

				boolean render = false;

				float pixel = scale / 12f;

				int localEnergy = ENERGY;
				float yMax = 12;

				float y1;
				float y2;

				float srcY;
				float srcY2;

				if (innerCoord.getY() == 0)
				{
					localEnergy = Math.min(6, ENERGY);
					yMax = 6;
					render = true;
				} else if (innerCoord.getY() == 1 && ENERGY > 5)
				{
					localEnergy -= 5;
					render = true;
				}

				if (render)
				{
					y1 = renderY + pixel * (yMax - localEnergy);
					y2 = y1 + pixel * localEnergy;

					srcY = yMax - localEnergy;
					srcY2 = srcY + localEnergy;


					manager.getTexture(activeFull).getPositionalVariation(x, y).draw(renderX, y1, renderX + scale, y2, 0, srcY, 12, srcY2, light);
				}
			}
		}
	}

}
