package com.raphydaphy.rocksolid.gui.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ComponentAssemblySlot extends ComponentSlot
{
	private static final ResourceName SLOT_TEXTURE = ResourceName.intern("gui.slot");

	public ComponentAssemblySlot(GuiContainer container, ContainerSlot slot, int componentId, int x, int y)
	{
		super(container, slot, componentId, x, y);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y)
	{
		ItemInstance holding = this.container.getContainer().holdingInst;
		renderSlotInGui(game, manager, g, this.slot.get(), x, y, 1F, this.isMouseOver(game), holding == null || this.slot.canPlace(holding));
	}

	private void renderSlotInGui(IGameInstance game, IAssetManager manager, IRenderer renderer, ItemInstance slot, float x, float y, float scale, boolean hovered, boolean canPlaceInto)
	{
		ITexture var9 = manager.getTexture(SLOT_TEXTURE);

		int color = 0xFF3d3d3d; // grey
		if (!canPlaceInto)
		{
			color = Colors.multiply(color, 0.5F);
		} else if (!hovered)
		{
			color = Colors.multiply(color, 0.75F);
		}

		var9.draw(x, y, (float) var9.getRenderWidth() * scale, (float) var9.getRenderHeight() * scale, color);
		if (slot != null)
		{
			renderer.renderItemInGui(game, manager, slot, x + 3.0F * scale, y + 3.0F * scale, scale, -1);
		}

	}
}
