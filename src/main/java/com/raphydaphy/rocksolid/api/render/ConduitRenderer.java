package com.raphydaphy.rocksolid.api.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.tileentity.TileEntityItemConduit;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class ConduitRenderer<T extends Tile> extends DefaultTileRenderer<T>
{
	public ConduitRenderer(IResourceName texture)
	{
		super(texture);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, T tile, TileState state,
			int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light)
	{

		TileEntity conduit = RockSolidAPILib.getTileFromPos(x, y, world);
		if (conduit instanceof TileEntityItemConduit && ((TileEntityItemConduit)conduit).isMaster())
		{
			manager.getTexture(this.texture.addSuffix(".MASTER")).drawWithLight(renderX, renderY, scale, scale, light);
		}
		else
		{
			manager.getTexture(this.texture).drawWithLight(renderX, renderY, scale, scale, light);
		}
		
		for (ConduitSide side : ConduitSide.values())
		{
			if (RockSolidAPILib.getTileFromConduitSide(new Pos2(x, y), side, world) != null)
			{
				if (((IConduit) conduit).canConnectTo(
						new Pos2(x + side.getOffset().getX(), y + side.getOffset().getY()),
						RockSolidAPILib.getTileFromPos(x + side.getOffset().getX(), y + side.getOffset().getY(), world))
						&& ((IConduit) conduit).getSideMode(side) != ConduitMode.DISABLED)
				{
					manager.getTexture(this.texture.addSuffix("." + side.toString())).drawWithLight(renderX, renderY,
							scale, scale, light);
				}
			}
		}

	}

}
