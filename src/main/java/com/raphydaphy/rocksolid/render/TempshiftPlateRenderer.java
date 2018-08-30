package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tile.machine.TileNuclearReactor;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TempshiftPlateRenderer<TileTempshiftPlate> extends DefaultTileRenderer
{

	public TempshiftPlateRenderer(ResourceName texture)
	{
		super(texture);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, Tile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		manager.getTexture(texture).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

		TileState main = world.getState(x, y);
		if (main.getTile() != ModTiles.NUCLEAR_REACTOR)
		{
			return;
		}
		Pos2 inner = ((TileNuclearReactor)main.getTile()).getInnerCoord(main);

		for (Direction dir : Direction.ADJACENT)
		{
			if ((dir == Direction.RIGHT && inner.getX() == 1 && inner.getY() != 3) || (dir == Direction.UP && inner.getX() == 2) || (dir == Direction.LEFT && inner.getX() == 3 && inner.getY() != 3))
			{
				continue;
			}

			int extendX = x + dir.x;
			int extendY = y + dir.getOpposite().y;
			Tile realWorld = world.getState(extendX, extendY).getTile();

			if ((realWorld == ModTiles.NUCLEAR_REACTOR || !realWorld.isFullTile()) && world.getState(ModMisc.TEMPSHIFT_LAYER, extendX, extendY).getTile() != ModTiles.TEMPSHIFT_PLATE)
			{
				float pixel = scale / 12f;

				float rX = renderX + scale * dir.x;
				float rY = renderY + scale * dir.y;
				float x2 = rX + scale;
				float y2 = rY + scale;

				int srcX = 0;
				int srcY = 0;

				int srcX2 = 12;
				int srcY2 = 12;

				if ((dir == Direction.UP && inner.getX() == 3 && inner.getY() != 0) || (dir == Direction.DOWN && inner.getX() == 3 && inner.getY() != 3))
				{
					srcX = 6;
					rX += pixel * 6;
				}
				else if ((dir == Direction.LEFT && inner.getY() == 3 && inner.getX() == 3) || (dir == Direction.RIGHT && inner.getY() == 3 && inner.getX() == 1))
				{
					srcY2 = 6;
					y2 -= pixel * 6;
				}
				else if ((dir == Direction.UP && inner.getX() == 1 && inner.getY() != 0) || (dir == Direction.DOWN && inner.getX() == 1 && inner.getY() != 3))
				{
					srcX2 = 6;
					x2 -= pixel * 6;
				}
				manager.getTexture(this.texture.addSuffix("." + dir.toString().toLowerCase())).getPositionalVariation(x + dir.x, y + dir.y)
						.draw(rX, rY, x2, y2, srcX, srcY, srcX2, srcY2, light);
			}
		}
	}

	@Override
	public void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, Tile tile, ItemInstance instance, float x, float y, float scale, int filter)
	{
		manager.getTexture(this.texture.addSuffix(".item")).draw(x, y, scale, scale, filter);
	}

}
