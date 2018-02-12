package com.raphydaphy.rocksolid.gui;

import com.google.common.base.Supplier;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.component.ComponentCustomText;
import com.raphydaphy.rocksolid.gui.component.ComponentCustomText.TextDirection;
import com.raphydaphy.rocksolid.tileentity.TileEntityConduit;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiConduit extends GuiContainer
{
	private final TileEntityConduit te;
	private Direction dir;

	public GuiConduit(AbstractEntityPlayer player, Direction dir, TileEntityConduit te)
	{
		super(player, 198, 140);
		this.te = te;
		this.dir = dir;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);
		
		this.components.add(new ComponentCustomText(this, 65, 20, 50, 1, 0.3f, TextDirection.CENTER, "Direction"));
		this.components.add(new ComponentCustomText(this, 133, 20, 50, 1, 0.3f, TextDirection.CENTER, dir.toString()));
		
		this.components.add(new ComponentButton(this, 154, 15, 10, 10, supplierTrue, ">"));
		this.components.add(new ComponentButton(this, 103, 15, 10, 10, supplierTrue, "<"));
		
		this.components.add(new ComponentCustomText(this, 65, 35, 50, 1, 0.3f, TextDirection.CENTER, "Mode"));
		this.components.add(new ComponentCustomText(this, 133, 35, 50, 1, 0.3f, TextDirection.CENTER, "In / Out"));

		this.components.add(new ComponentButton(this, 154, 30, 10, 10, supplierTrue, ">"));
		this.components.add(new ComponentButton(this, 103, 30, 10, 10, supplierTrue, "<"));
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
