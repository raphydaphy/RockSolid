package com.raphydaphy.rocksolid.entity;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.render.RocketRenderer;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.world.IWorld;

public class EntityRocket extends Entity
{
	public EntityRocket(IWorld world)
	{
		super(world);
	}

	@Override
	public void save(DataSet set, boolean forFullSync)
	{
		super.save(set, forFullSync);
	}

	@Override
	public void load(DataSet set, boolean forFullSync)
	{
		super.load(set, forFullSync);
	}

	@Override
	public boolean onInteractWith(AbstractEntityPlayer player, double mouseX, double mouseY)
	{
		return false;
	}

	@Override
	public IEntityRenderer getRenderer() {
		return new RocketRenderer(RockSolid.createRes("entities.rocket"));
	}

	@Override
	public float getHeight() {
		return 4F;
	}

	@Override
	public int getRenderPriority() {
		return -8;
	}
}
