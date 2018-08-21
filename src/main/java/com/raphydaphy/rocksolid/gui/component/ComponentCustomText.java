package com.raphydaphy.rocksolid.gui.component;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.font.IFont;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.GuiComponent;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class ComponentCustomText extends GuiComponent
{

	private final float scale;
	private final String[] text;
	private final TextDirection direction;

	public ComponentCustomText(Gui gui, int x, int y, int width, int height, float scale, TextDirection direction, String... text){
        super(gui, x, y, width, height);
        this.scale = scale;
        this.direction = direction;
        this.text = text;
    }

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y)
	{
		IFont font = manager.getFont();
		List<String> text = font.splitTextToLength(this.width - 2, this.scale, true, this.text);

		float height = font.getHeight(this.scale);
		int yOff = this.height / 2 - (text.size() * (int) height) / 2;

		for (String s : text)
		{
			switch(direction)
			{
			case RIGHT:
				font.drawStringFromRight(x + this.width - 1, y + yOff, s, this.scale);
				break;
			case LEFT:
				font.drawString(x + 1, y + yOff, s, this.scale);
				break;
			case CENTER:
				font.drawCenteredString(x + 1, y + yOff, s, this.scale, false);
				break;
			}

			yOff += height;
		}
	}

	@Override
	public ResourceName getName()
	{
		return ResourceName.intern("text");
	}

	@Override
	public boolean shouldDoFingerCursor(IGameInstance game)
	{
		return false;
	}

	public static enum TextDirection
	{
		LEFT, RIGHT, CENTER;
	}
}
