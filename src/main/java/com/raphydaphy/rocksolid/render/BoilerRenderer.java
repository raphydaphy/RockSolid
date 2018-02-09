package com.raphydaphy.rocksolid.render;

import java.util.HashMap;
import java.util.Map;

import com.raphydaphy.rocksolid.tile.TileBoiler;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class BoilerRenderer extends MultiTileRenderer<TileBoiler>
{
	protected final Map<Pos2, IResourceName> baseTextures = new HashMap<>();

	public BoilerRenderer(IResourceName texture, TileBoiler tile)
	{
		super(texture, tile);

		for (int x = 0; x < tile.getWidth(); x++)
		{
			for (int y = 0; y < tile.getHeight(); y++)
			{
				if (tile.isStructurePart(x, y))
				{
					this.baseTextures.put(new Pos2(x, y), this.texture.addSuffix(".base." + x + "." + y));
				}
			}
		}

	}

	@Override
	public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, TileBoiler tile,
			TileState state)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);
		return manager.getTexture(this.baseTextures.get(innerCoord));
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileBoiler tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);
		TileEntityBoiler te = world.getTileEntity(x, y, TileEntityBoiler.class);
		if (te != null)
		{
			manager.getTexture(getTextureFor(innerCoord, te.getSteam(), false)).getPositionalVariation(x, y)
					.draw(renderX, renderY, scale, scale, light);
		}
	}

	private IResourceName getTextureFor(Pos2 coord, int stage, boolean active)
	{
		IResourceName tex = baseTextures.get(coord);
		stage = Math.min(stage, 7);

		if (active && coord.getY() == 0)
		{
			tex = this.texture.addSuffix(".active." + coord.getX() + "." + coord.getY());
		} else if (stage > 0 && coord.getY() != 0 && coord.getY() != 4)
		{
			tex = this.texture.addSuffix("." + stage + "." + coord.getX() + "." + coord.getY());
		}

		return tex;
	}

}
