package com.raphydaphy.rocksolid.render;


import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.util.*;

public final class MoonSkyRenderer {
	private static final ResourceName sun = ResourceName.intern("sky.sun");
	private static final ResourceName moon = RockSolid.createRes("sky.earth");
	private final List<Pos2> stars = new ArrayList<>();
	private final Random rand = new Random();
	public int g;

	public MoonSkyRenderer() {
	}

	public final void a(IGameInstance game, IAssetManager manager, IRenderer renderer, IWorld world, AbstractEntityPlayer player)
	{
		double width = (double)renderer.getWidthInWorld();
		double height = (double)renderer.getHeightInWorld();

		renderer.backgroundColor(0);
		float var86 = renderer.getWorldScale();
		renderer.setScale(var86, var86);
		int time = world.getCurrentTime();
		float var10 = game.getSettings().renderScale;
		int var39;
		if (this.stars.isEmpty()) {
			for(var39 = 0; var39 < Util.RANDOM.nextInt(100) + 200; ++var39) {
				stars.add(new Pos2(Util.RANDOM.nextInt(101), Util.RANDOM.nextInt(101)));
			}
		}

		var39 = -2;
		for (Pos2 star : stars)
		{
			rand.setSeed(Util.scrambleSeed(star.getX(), star.getY()));
			float var42 = ((float) Math.sin((double) ((float) world.getTotalTime() + rand.nextFloat() * 500.0F) / 80.0D % 6.283185307179586D) + 1.0F) / 2.0F;
			renderer.addFilledRect((float) ((double) star.getX() / 100.0D * width), (float) ((double) star.getY() / 100.0D * height), 0.1F, 0.1F, Colors.multiplyA(var39, var42));
		}

		double var87 = 10.0D / (double)var10;
		double var88 = 7.0D / (double)var10;
		double var45 = Math.toRadians((double)time / 24000.0D * 360.0D + 180.0D);
		var10 = (float)(width / 2.0D + Math.cos(var45) * var87);
		float var79 = (float)(height + Math.sin(var45) * var88);
		manager.getTexture(sun).draw(var10 - 2.0F, var79 - 2.0F, 4.0F, 4.0F);
		double var51 = Math.toRadians((double)time / 24000.0D * 360.0D);
		var10 = (float)(width / 2.0D + Math.cos(var51) * var87);
		var79 = (float)(height + Math.sin(var51) * var88);
		float size = 4;
		manager.getTexture(moon).draw(var10 - 2.0F * size, var79 - 2.0F * size, 4.0F * size, 4.0F * size);

		renderer.setScale(1.0F, 1.0F);
	}
}
