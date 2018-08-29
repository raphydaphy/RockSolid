package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerNuclearReactor;
import com.raphydaphy.rocksolid.gui.GuiNuclearReactor;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.render.NuclearReactorRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileNuclearReactor extends TileMachineBase<TileEntityNuclearReactor>
{
	public TileNuclearReactor()
	{
		super("nuclear_reactor", TileEntityNuclearReactor.class,35, true,new ToolInfo(ToolProperty.PICKAXE, 11));
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(5,4);
	}

	@Override
	protected ITileRenderer<TileNuclearReactor> createRenderer(ResourceName name)
	{
		return new NuclearReactorRenderer(name, this);
	}

	@Override
	public int getWidth()
	{
		return 5;
	}

	@Override
	public int getHeight()
	{
		return 4;
	}

	@Override
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityNuclearReactor(world, x, y, layer);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		ItemInstance held = player.getInv().get(player.getSelectedSlot());
		if (held != null && held.getItem() == ModTiles.TEMPSHIFT_PLATE.getItem())
		{
			return world.getState(ModMisc.TEMPSHIFT_LAYER, x, y).getTile() != ModTiles.TEMPSHIFT_PLATE;
		}
		TileEntityNuclearReactor te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiNuclearReactor(player, te), new ContainerNuclearReactor(player, te));
		return true;
	}

}

