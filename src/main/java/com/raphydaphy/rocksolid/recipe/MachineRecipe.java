package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.construction.compendium.BasicCompendiumRecipe;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public abstract class MachineRecipe extends BasicCompendiumRecipe
{
	public static final NameRegistry<MachineRecipe> MACHINE_RECIPES = new NameRegistry<>(RockSolid.createRes("machine_recipe_registry"), true).register();
	protected final ResourceName infoName;

	public MachineRecipe(ResourceName name)
	{
		super(name);
		this.infoName = name.addPrefix("recipe_");
	}

	public ResourceName getKnowledgeInformationName()
	{
		return this.infoName;
	}

	@Override
	public boolean isKnown(AbstractEntityPlayer player)
	{
		return !player.world.isStoryMode() || player.getKnowledge().knowsInformation(getKnowledgeInformationName());
	}

	public final void teach(AbstractEntityPlayer player, boolean announce)
	{
		if (!isKnown(player))
		{
			MachineRecipeInformation info = new MachineRecipeInformation(this);
			player.getKnowledge().teachInformation(info, announce);
		}
	}

}
