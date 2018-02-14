package com.raphydaphy.rocksolid.item;

import org.lwjgl.glfw.GLFW;

import com.raphydaphy.rocksolid.container.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiConduit;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.tile.conduit.TileConduit;
import com.raphydaphy.rocksolid.tileentity.TileEntityConduit;
import com.raphydaphy.rocksolid.tileentity.TileEntityConduit.ConduitSide;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ItemWrench extends ItemBase
{

	public ItemWrench()
	{
		super("wrench");
		this.maxAmount = 1;
	}

	@Override
	protected IItemRenderer<ItemWrench> createRenderer(IResourceName name)
	{
		return new DefaultItemRenderer<ItemWrench>(name)
		{
			@Override
			public void renderOnMouseCursor(IGameInstance game, IAssetManager manager, IRenderer g, ItemWrench item,
					ItemInstance instance, float x, float y, float scale, int filter, boolean isInPlayerRange)
			{
				if (isInPlayerRange)
				{
					this.render(game, manager, g, item, instance, x, y, scale, filter);
				}
			}
		};

	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player, ItemInstance instance)
	{
		if (layer == ModMisc.CONDUIT_LAYER)
		{
			if (RockBottomAPI.getGame().getInput().isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
			{
				world.destroyTile(x, y, layer, player, true);
				return true;
			} else
			{
				TileEntityConduit te = world.getTileEntity(layer, x, y, TileEntityConduit.class);
				if (te != null)
				{
					player.openGuiContainer(
							new GuiConduit(player, ConduitSide.getByDirection(TileConduit.getMousedConduitPart(RockBottomAPI.getGame())), te),
							new ContainerEmpty(player));
				}
			}
		}
		return false;
	}

}
