package com.raphydaphy.rocksolid.tile;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.api.gui.ContainerEmpty;
import com.raphydaphy.rocksolid.api.render.ConduitRenderer;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.gui.GuiItemConduitConfig;
import com.raphydaphy.rocksolid.init.ModKeybinds;
import com.raphydaphy.rocksolid.item.ItemWrench;
import com.raphydaphy.rocksolid.network.PacketTileDestroyed;
import com.raphydaphy.rocksolid.tileentity.TileEntityItemConduit;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileItemConduit extends TileBasic
{
	private static final String name = "itemConduit";
	private final IResourceName desc = RockBottomAPI.createRes(RockSolid.INSTANCE, "details." + name);

	public TileItemConduit()
	{
		super(RockSolidAPILib.makeInternalRes(name));
		this.setHardness((float) 20);
		this.addEffectiveTool(ToolType.PICKAXE, 1);
		this.register();
	}

	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		return layer == TileLayer.MAIN ? new TileEntityItemConduit(world, x, y, layer) : null;
	}

	@Override
	protected ITileRenderer<Tile> createRenderer(IResourceName name)
	{
		return new ConduitRenderer<Tile>(name);
	}

	@Override
	public boolean canProvideTileEntity()
	{
		return true;
	}

	@Override
	public void onDestroyed(final IWorld world, final int x, final int y, final Entity destroyer, final TileLayer layer,
			final boolean forceDrop)
	{
		super.onDestroyed(world, x, y, destroyer, layer, forceDrop);
		if (!RockBottomAPI.getNet().isClient())
		{
			final TileEntityItemConduit tile = world.getTileEntity(x, y, TileEntityItemConduit.class);
			if (tile != null)
			{
				tile.dropInventory(tile.inventory);
			}
		}
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player)
	{
		TileEntityItemConduit tile = world.getTileEntity(x, y, TileEntityItemConduit.class);

		if (tile != null)
		{
			if (RockBottomAPI.getNet().isServer() && RockBottomAPI.getGame().isDedicatedServer())
			{
				return true;
			}
			if (player.getInvContainer().getSlot(player.getSelectedSlot()).get() != null)
			{

				if (player.getInvContainer().getSlot(player.getSelectedSlot()).get().getItem() instanceof ItemWrench)
				{
					if (ModKeybinds.keyWrenchMode.isDown())
					{
						world.destroyTile(x, y, TileLayer.MAIN, player, true);
						RockBottomAPI.getNet().sendToServer(new PacketTileDestroyed(player.getUniqueId(), x, y, true));
						return true;
					} else
					{
						player.openGuiContainer(new GuiItemConduitConfig(player, tile), new ContainerEmpty(player));
						return true;
					}
				}
			}
			return false;
		} else
		{
			return false;
		}
	}

	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{
		if (!this.canPlaceInLayer(layer))
		{
			return false;
		}

		return true;
	}

	@Override
	public BoundBox getBoundBox(final IWorld world, final int x, final int y)
	{
		return null;
	}

	@Override
	public boolean isFullTile()
	{
		return false;
	}

	public void onAdded(IWorld world, int x, int y, TileLayer layer)
	{
		TileEntityItemConduit tile = world.getTileEntity(x, y, TileEntityItemConduit.class);
		if (tile != null)
		{
			tile.onAdded(world, x, y);
		}
	}

	@Override
	public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY,
			TileLayer changedLayer)
	{
		super.onChangeAround(world, x, y, layer, changedX, changedY, changedLayer);

		TileEntityItemConduit tile = world.getTileEntity(x, y, TileEntityItemConduit.class);

		if (tile != null)
		{
			tile.onChangeAround(world, x, y, layer, changedX, changedY, changedLayer);
		}
	}

	public void onRemoved(IWorld world, int x, int y, TileLayer layer)
	{
		TileEntityItemConduit tile = world.getTileEntity(x, y, TileEntityItemConduit.class);

		if (tile != null)
		{
			tile.onRemoved(world, x, y, layer);
		}
	}

	@Override
	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced)
	{
		super.describeItem(manager, instance, desc, isAdvanced);
		desc.addAll(manager.getFont().splitTextToLength(500, 1f, true, manager.localize(this.desc)));
	}

}
