package com.raphydaphy.rocksolid.render;

import java.util.HashMap;
import java.util.Map;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tile.TileRefinery;
import com.raphydaphy.rocksolid.tileentity.TileEntityEnergyConduit;
import com.raphydaphy.rocksolid.tileentity.TileEntityFluidConduit;
import com.raphydaphy.rocksolid.tileentity.TileEntityRefinery;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class RefineryRenderer extends MultiTileRenderer<TileRefinery>
{
	protected final Map<Pos2, IResourceName> texturesActive;

	public RefineryRenderer(final IResourceName texture, final MultiTile tile)
	{
		super(texture, tile);
		this.texturesActive = new HashMap<Pos2, IResourceName>();
		for (int x = 0; x < tile.getWidth(); ++x)
		{
			for (int y = 0; y < tile.getHeight(); ++y)
			{
				if (tile.isStructurePart(x, y))
				{
					this.texturesActive.put(new Pos2(x, y), this.texture.addSuffix(".active." + x + "." + y));
				}
			}
		}
	}

	private enum RefinerySide
	{
		UP(1, 1, 1, 2), LEFT_INPUT(0, 0, -1, 0), RIGHT_OUTPUT(2, 0, 3, 0), RIGHT_UP(2, 1, 2, 2), RIGHT_SIDE(2, 1, 3,
				1), LEFT_SIDE(0, 1, -1, 1), LEFT_UP(0, 1, 0, 2);

		private Pos2 innerCoord;
		private Pos2 offset;

		RefinerySide(int innerX, int innerY, int offsetX, int offsetY)
		{
			this.innerCoord = new Pos2(innerX, innerY);
			this.offset = new Pos2(offsetX, offsetY);
		}

		public Pos2 getOffset()
		{
			return this.offset;
		}

		public Pos2 getInnerCoord()
		{
			return this.innerCoord;
		}
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, TileRefinery tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		final Pos2 innerCoord = tile.getInnerCoord(state);
		final Pos2 mainPos = tile.getMainPos(x, y, state);
		TileEntity refinery = RockSolidAPILib.getTileFromPos(x, y, world);

		if (refinery != null && refinery instanceof TileEntityRefinery)
		{
			IResourceName tex;
			if (((TileEntityRefinery) refinery).isActive())
			{
				tex = this.texturesActive.get(innerCoord);
			} else
			{
				tex = this.textures.get(innerCoord);
			}
			manager.getTexture(tex).draw(renderX, renderY, scale, scale, light);

			for (RefinerySide side : RefinerySide.values())
			{
				if (innerCoord.equals(side.getInnerCoord()))
				{
					TileEntity adjacentTile = world.getTileEntity(mainPos.getX() + side.getOffset().getX(),
							mainPos.getY() + side.getOffset().getY());
					if (adjacentTile != null && (adjacentTile instanceof TileEntityFluidConduit
							|| adjacentTile instanceof TileEntityEnergyConduit))
					{
						manager.getTexture(this.texture
								.addSuffix("." + side.toString() + "." + innerCoord.getX() + "." + innerCoord.getY()))
								.draw(renderX, renderY, scale, scale, light);
					}
				}
			}
		}
	}
}
