package com.raphydaphy.rocksolid.util;

import com.raphydaphy.rocksolid.tileentity.base.TileEntityFueledBase;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.awt.*;
import java.util.Random;

public class ModUtils
{
	public static final int COAL = new Color(0.5F, 0.1F, 0.1F).getRGB();
	public static final int PROGRESS = new Color(0.1F, 0.5F, 0.1F).getRGB();
	public static final int ENERGY = new Color(148, 0, 211).getRGB();

	public static void smokeParticle(IWorld world, int x, int y, IParticleManager manager, TileEntityFueledBase te)
	{
		Random rand = new Random();

		if (te.isActive())
		{
			boolean left = rand.nextBoolean();
			double particleX = x + (left ? .05 : .74) + (rand.nextFloat() / 40);
			double particleY = y + (left ? .9 : .7);
			manager.addSmokeParticle(world, particleX, particleY, 0, 0.02, 0.2f + (rand.nextFloat() / 20));
		}
	}
}
