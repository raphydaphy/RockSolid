package com.raphydaphy.rocksolid.util;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.init.ModMisc;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ConduitTileLayer extends TileLayer
{

	public ConduitTileLayer()
	{
		super(RockSolid.createRes("conduit_layer"), 5);
		this.register();
	}

	@Override
	public boolean canHoldTileEntities()
	{
		return true;
	}

	@Override
	public boolean canTileBeInLayer(IWorld world, int x, int y, Tile tile)
	{
		return tile != null && (tile instanceof IConduit || tile.isAir());
	}

	@Override
	public boolean forceForegroundRender()
	{
		return true;
	}

	@Override
	public boolean canCollide(MovableWorldObject object)
	{
		return false;
	}

	@Override
	public boolean isVisible(IGameInstance game, AbstractEntityPlayer player, IChunk chunk, int x, int y,
			boolean isRenderingForeground)
	{
		// TODO: wrench mode disables render
		return true;
	}

	@Override
	public boolean canEditLayer(IGameInstance game, AbstractEntityPlayer player)
	{
		ItemInstance selected = player.getInv().get(player.getSelectedSlot());
		return ModMisc.KEY_CONDUIT_LAYER.isDown() || (selected != null && selected.getItem().equals(ModItems.WRENCH));
	}

}
