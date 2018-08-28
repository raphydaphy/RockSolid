package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.container.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiConduit;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.network.PacketConduitDestroyed;
import com.raphydaphy.rocksolid.tile.conduit.TileConduit;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitSide;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ItemWrench extends ItemDurability
{

	public ItemWrench()
	{
		super("wrench", 250);
	}

	@Override
	protected IItemRenderer createRenderer(ResourceName name)
	{
		return new DefaultItemRenderer<ItemWrench>(name)
		{
			@Override
			public void renderOnMouseCursor(IGameInstance game, IAssetManager manager, IRenderer g, ItemWrench item, ItemInstance instance, float x, float y, float scale, int filter, boolean isInPlayerRange)
			{
				if (isInPlayerRange)
				{
					this.render(game, manager, g, item, instance, x, y, scale, filter);
				}
			}
		};

	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance)
	{
		world.travelToSubWorld(player, ModMisc.MOON_WORLD, x, y);
		if ((world.isServer() && world.isDedicatedServer()))
		{
			return true;
		}
		if (layer == ModMisc.CONDUIT_LAYER)
		{
			if ( RockBottomAPI.getGame().getInput().isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
			{
				PacketConduitDestroyed packet = new PacketConduitDestroyed(x, y, player.getUniqueId());
				if (world.isClient())
				{
					RockBottomAPI.getNet().sendToServer(packet);
				}
				else
				{
					packet.handle(RockBottomAPI.getGame(), null);
				}

			} else
			{
				TileEntityConduit te = world.getTileEntity(layer, x, y, TileEntityConduit.class);
				if (te != null)
				{
					player.openGuiContainer(new GuiConduit(player, ConduitSide.getByDirection(TileConduit.getMousedConduitPart(RockBottomAPI.getGame())), te), new ContainerEmpty(player));
				}
			}
			return true;
		}

		return false;
	}
}
