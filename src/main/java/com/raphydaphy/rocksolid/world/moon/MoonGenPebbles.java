package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Random;

public class MoonGenPebbles implements IMoonGenerator
{
	private final Random a = new Random();

	public MoonGenPebbles() {
	}

	public boolean shouldGenerate(IWorld world, IChunk var2) {
		return world.getSubName() != null && world.getSubName().equals(ModMisc.MOON_WORLD);
	}

	public void generate(IWorld var1, IChunk var2) {
		for(int var3 = 0; var3 < 32; ++var3) {
			int var4 = var2.getHeightInner(TileLayer.MAIN, var3);
			if (var2.getY() + var4 >= var1.getExpectedSurfaceHeight(TileLayer.MAIN, var2.getX() + var3) && var4 < 32 && var2.getStateInner(var3, var4).getTile().canReplace(var1, var2.getX() + var3, var2.getY() + var4, TileLayer.MAIN)) {
				float var5 = var2.getBiomeInner(var3, var4).getPebbleChance();
				this.a.setSeed(Util.scrambleSeed(var3, var4, var1.getSeed()));
				Tile var6;
				if (var5 > 0.0F && this.a.nextFloat() <= var5 && (var6 = GameContent.TILE_PEBBLES).canPlace(var1, var2.getX() + var3, var2.getY() + var4, TileLayer.MAIN, (AbstractEntityPlayer)null)) {
					var2.setStateInner(var3, var4, var6.getDefState());
				}
			}
		}

	}

	public int getPriority() {
		return -90;
	}
}
