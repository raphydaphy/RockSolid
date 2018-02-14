package com.raphydaphy.rocksolid.gui;

import java.util.List;

import com.google.common.base.Supplier;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.component.ComponentCustomText;
import com.raphydaphy.rocksolid.gui.component.ComponentCustomText.TextDirection;
import com.raphydaphy.rocksolid.network.PacketConduitUpdate;
import com.raphydaphy.rocksolid.tileentity.TileEntityConduit;
import com.raphydaphy.rocksolid.tileentity.TileEntityConduit.ConduitMode;
import com.raphydaphy.rocksolid.tileentity.TileEntityConduit.ConduitSide;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiConduit extends GuiContainer
{
	private final TileEntityConduit te;
	private final Pos2 pos;
	private ConduitSide side;
	private ConduitMode mode;

	public GuiConduit(AbstractEntityPlayer player, ConduitSide side, TileEntityConduit te)
	{
		super(player, 198, 140);
		this.te = te;
		this.side = side;
		this.pos = new Pos2(te.x, te.y);
		this.mode = te.getMode(pos, side);
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);
		
		IPacket updatePacket = new PacketConduitUpdate(te.x, te.y, mode, side);
		if (game.getWorld().isClient())
		{
			RockBottomAPI.getNet().sendToServer(updatePacket);
		}
		else
		{
			updatePacket.handle(game, null);
		}
		
		this.components.add(new ComponentCustomText(this, 65, 20, 50, 1, 0.3f, TextDirection.CENTER, "Direction"));
		this.components.add(new ComponentCustomText(this, 133, 20, 50, 1, 0.3f, TextDirection.CENTER,
				side == null ? "NONE" : side.toString()));

		this.components.add(new ComponentButton(this, 154, 15, 10, 10, supplierTrue, ">"));
		this.components.add(new ComponentButton(this, 103, 15, 10, 10, supplierTrue, "<"));

		this.components.add(new ComponentCustomText(this, 65, 35, 50, 1, 0.3f, TextDirection.CENTER, "Mode"));
		this.components.add(new ComponentCustomText(this, 133, 35, 50, 1, 0.3f, TextDirection.CENTER,
				mode == null ? "NULL" : RockBottomAPI.getGame().getAssetManager().localize(mode.name)));

		this.components.add(new ComponentButton(this, 154, 30, 10, 10, new Supplier<Boolean>()
		{
			@Override
			public Boolean get()
			{
				if (mode != null)
				{
					List<ConduitMode> all;
					if (mode.isConduit())
					{
						all = ConduitMode.getConduitModes();
					} else
					{
						all = ConduitMode.getInvModes();
					}
					int index = all.indexOf(mode);
					if (index + 1 < all.size())
					{
						mode = all.get(index + 1);
					} else
					{
						mode = all.get(0);
					}
					components.clear();
					init(game);
					return true;
				}
				return false;
			}
		}, ">"));
		this.components.add(new ComponentButton(this, 103, 30, 10, 10, new Supplier<Boolean>()
		{
			@Override
			public Boolean get()
			{
				if (mode != null)
				{

					List<ConduitMode> all;
					if (mode.isConduit())
					{
						all = ConduitMode.getConduitModes();
					} else
					{
						all = ConduitMode.getInvModes();
					}
					int index = all.indexOf(mode);
					if (index - 1 >= 0)
					{
						mode = all.get(index - 1);
					} else
					{
						mode = all.get(all.size() - 1);
					}
					components.clear();
					init(game);
					return true;
				}
				return false;
			}
		}, "<"));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("gui_conduit");
	}

	Supplier<Boolean> supplierTrue = new Supplier<Boolean>()
	{
		@Override
		public Boolean get()
		{
			return true;
		}
	};

}
