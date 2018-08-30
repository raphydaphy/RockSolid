package com.raphydaphy.rocksolid.tile.liquid;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Iterator;
import java.util.List;

public class TileOil extends TileLiquid
{
	private final BoundBox[] a = new BoundBox[this.getLevels()];

	public TileOil() {
		super(RockSolid.createRes("oil"));

		for(int var1 = 0; var1 < this.a.length; ++var1) {
			this.a[var1] = new BoundBox(0.0D, 0.0D, 1.0D, (double)(var1 + 1) / (double)this.getLevels());
		}
		register();

	}

	public final BoundBox getBoundBox(IWorld var1, int var2, int var3, TileLayer var4) {
		return this.a[var1.getState(var4, var2, var3).get(this.level)];
	}

	public final int getLevels() {
		return 12;
	}

	public final boolean doesFlow() {
		return true;
	}

	public final int getFlowSpeed() {
		return 5;
	}

	public final void onIntersectWithEntity(IWorld var1, int var2, int var3, TileLayer var4, TileState var5, BoundBox var6, BoundBox var7, List<BoundBox> var8, Entity var9) {
		super.onIntersectWithEntity(var1, var2, var3, var4, var5, var6, var7, var8, var9);
		Iterator var10 = var8.iterator();

		do {
			if (!var10.hasNext()) {
				return;
			}
		} while(!((BoundBox)var10.next()).contains(var9.getX(), var9.getOriginY()));

		var9.motionX *= 0.65D;
		if (var9.motionY < 0.0D) {
			var9.motionY *= 0.65D;
		}

		var9.fallStartY = var9.getY();
	}

	public final void updateRandomly(IWorld var1, int var2, int var3, TileLayer var4) {
		TileState var5;
		int var6;
		if (Util.RANDOM.nextFloat() >= 0.95F && (var6 = (var5 = var1.getState(var4, var2, var3)).get(this.level)) <= 3) {
			Direction[] var7 = Direction.ADJACENT;
			int var8 = Direction.ADJACENT.length;

			for(int var9 = 0; var9 < var8; ++var9) {
				Direction var10 = var7[var9];
				if (!var1.isPosLoaded((double)(var2 + var10.x), (double)(var3 + var10.y))) {
					return;
				}

				TileState var11;
				if ((var11 = var1.getState(var4, var2 + var10.x, var3 + var10.y)).getTile() == this && var11.get(this.level) > 3) {
					return;
				}
			}

			if (var6 <= 0) {
				var1.setState(var4, var2, var3, GameContent.TILE_AIR.getDefState());
				return;
			}

			var1.setState(var4, var2, var3, var5.prop(this.level, var6 - 1));
		}

	}
}