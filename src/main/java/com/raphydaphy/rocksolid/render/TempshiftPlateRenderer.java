package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Direction;
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

		for (Direction dir : Direction.ADJACENT)
		{
			int extendX = x + dir.x;
			int extendY = y + dir.getOpposite().y;
			Tile realWorld = world.getState(extendX, extendY).getTile();
			if ((realWorld == ModTiles.NUCLEAR_REACTOR || !realWorld.isFullTile()) && world.getState(ModMisc.TEMPSHIFT_LAYER, extendX, extendY).getTile() != ModTiles.TEMPSHIFT_PLATE)
			{
				manager.getTexture(this.texture.addSuffix("." + dir.toString().toLowerCase())).getPositionalVariation(x + dir.x, y + dir.y).draw(renderX + scale * dir.x, renderY + scale * dir.y, scale, scale, light);
			}
		}
	}

	@Override
	public void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, Tile tile, ItemInstance instance, float x, float y, float scale, int filter)
	{
		manager.getTexture(this.texture.addSuffix(".item")).draw(x, y, scale, scale, filter);
	}

}
