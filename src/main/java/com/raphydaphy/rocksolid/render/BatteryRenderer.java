package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.machine.TileBattery;
import com.raphydaphy.rocksolid.tileentity.TileEntityBattery;
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

public class BatteryRenderer extends MultiTileRenderer<TileBattery>
{

	public BatteryRenderer(ResourceName texture, TileBattery tile)
	{
		super(texture, tile);

	}

	@Override
	public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, TileBattery tile, TileState state)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);
		return manager.getTexture(this.textures.get(innerCoord));
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileBattery tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);
		TileEntityBattery te = tile.getTE(world, state, x, y);
		if (te != null)
		{
			manager.getTexture(this.textures.get(innerCoord)).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

			ResourceName full = this.texture.addSuffix(".full." + innerCoord.getX() + "." + innerCoord.getY());
			if (te.getEnergyFullness() > 0 && innerCoord.getX() == 1)
			{
				int ENERGY = (int) Math.min((te.getEnergyFullness() * te.getEnergyCapacity(null, null)) / (te.getEnergyCapacity(null, null) / 24d), 24);

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
				} else if (innerCoord.getY() == 1 && ENERGY > 6)
				{
					localEnergy = Math.min(18, ENERGY);
					localEnergy -= 6;
					render = true;
				} else if (innerCoord.getY() == 2 && ENERGY > 18)
				{
					localEnergy = ENERGY - 18;
					render = true;
					yMax = 12;
				}

				if (render)
				{
					y1 = renderY + pixel * ( yMax - localEnergy);
					y2 = y1 + pixel * localEnergy;

					srcY= yMax - localEnergy;
					srcY2 = srcY + localEnergy;


					manager.getTexture(full).getPositionalVariation(x, y).draw(renderX, y1, renderX + scale, y2, 0, srcY, 12, srcY2, light);
				}
			}
		}
	}

}
