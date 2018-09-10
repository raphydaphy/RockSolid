package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.construction.compendium.BasicCompendiumRecipe;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.entity.player.knowledge.IKnowledgeManager;
import de.ellpeck.rockbottom.api.entity.player.knowledge.Information;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponent;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentEmpty;
import de.ellpeck.rockbottom.api.net.chat.component.ChatComponentText;
import de.ellpeck.rockbottom.api.toast.Toast;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public abstract class MachineRecipe extends BasicCompendiumRecipe
{
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

	public abstract NameRegistry<? extends MachineRecipe> getRegistry();

	public class MachineRecipeInformation extends Information
	{
		private MachineRecipe recipe;

		public MachineRecipeInformation(MachineRecipe var1)
		{
			super(var1.getKnowledgeInformationName());
			this.recipe = var1;
		}

		public MachineRecipeInformation(ResourceName var1)
		{
			super(var1);
		}

		public Toast announceForget()
		{
			return new Toast(ResourceName.intern("gui.construction.book_closed"), new ChatComponentText("Recipe forgotten"), this.a(), 200);
		}

		@Override
		public Toast announceTeach()
		{
			return new Toast(ResourceName.intern("gui.construction.book_open"), new ChatComponentText("Recipe learned"), this.a(), 200);
		}

		private ChatComponent a()
		{
			if (this.recipe != null)
			{
				ItemInstance var1 = this.recipe.getOutputs().get(0);
				return new ChatComponentText(var1.getDisplayName() + " x" + var1.getAmount());
			} else
			{
				return new ChatComponentEmpty();
			}
		}

		@Override
		public void save(DataSet var1, IKnowledgeManager var2)
		{
			if (this.recipe != null)
			{
				var1.addString("recipe_name", this.recipe.getName().toString());
			}

		}

		@Override
		public void load(DataSet var1, IKnowledgeManager var2)
		{
			ResourceName var3 = new ResourceName(var1.getString("recipe_name"));
			this.recipe = getRegistry().get(var3);
		}

		@Override
		public ResourceName getRegistryName()
		{
			return RockSolid.createRes("machine_recipe");
		}
	}

}
