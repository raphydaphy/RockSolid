package com.raphydaphy.rocksolid.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ToolRenderer extends DefaultItemRenderer
{
    public ToolRenderer(final IResourceName texture) {
        super(texture);
    }
    
    @Override
    public void renderOnMouseCursor(final IGameInstance game, final IAssetManager manager, final Graphics g, final Item item, final ItemInstance instance, final float x, final float y, final float scale, final Color filter) {
        this.render(game, manager, g, item, instance, x, y, scale, filter);
    }

}
