package com.raphydaphy.rocksolid.recipe.loader;

import com.google.common.base.Charsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.recipe.SeparatingRecipe;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.content.IContentLoader;
import de.ellpeck.rockbottom.api.content.pack.ContentPack;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class SeparatingRecipeLoader implements IContentLoader
{
	public static final ResourceName ID = RockSolid.createRes("separating");
	private final Set<ResourceName> disabled = new HashSet<>();

	public SeparatingRecipeLoader()
	{
	}

	@Override
	public ResourceName getContentIdentifier()
	{
		return ID;
	}

	@Override
	public void loadContent(IGameInstance game, ResourceName resourceName, String path, JsonElement element, String elementName, IMod loadingMod, ContentPack pack) throws Exception
	{
		if (!this.disabled.contains(resourceName))
		{
			if (IRecipe.forName(resourceName) != null)
			{
				RockSolid.getLogger().info("Separating recipe with name " + resourceName + " already exists, not adding recipe for mod " + loadingMod.getDisplayName() + " with content pack " + pack.getName());
			} else
			{
				String fileName = path + element.getAsString();
				InputStreamReader fileReader = new InputStreamReader(game.getClassLoader().getResourceAsStream(fileName), Charsets.UTF_8);
				JsonElement fileParser = Util.JSON_PARSER.parse(fileReader);
				fileReader.close();
				JsonObject curObject;

				int recipeTime = (curObject = fileParser.getAsJsonObject()).get("time").getAsInt();

				JsonObject outputObject = curObject.get("output").getAsJsonObject();

				Item outputItem = Registries.ITEM_REGISTRY.get(new ResourceName(outputObject.get("name").getAsString()));
				int outputAmount = outputObject.has("amount") ? outputObject.get("amount").getAsInt() : 1;
				int outputMeta = outputObject.has("meta") ? outputObject.get("meta").getAsInt() : 0;

				ItemInstance outputInstance = new ItemInstance(outputItem, outputAmount, outputMeta);

				int biproductChance = 3;
				ItemInstance biproductInstance = new ItemInstance(ModItems.SLAG);

				if (curObject.has("biproduct"))
				{
					JsonObject biproductObject = curObject.get("biproduct").getAsJsonObject();

					Item biproductItem = Registries.ITEM_REGISTRY.get(new ResourceName(biproductObject.get("name").getAsString()));
					int biproductAmount = biproductObject.has("amount") ? biproductObject.get("amount").getAsInt() : 1;
					int biproductMeta = biproductObject.has("meta") ? biproductObject.get("meta").getAsInt() : 0;
					biproductChance = biproductObject.has("chance") ? biproductObject.get("chance").getAsInt() : 3;

					biproductInstance = new ItemInstance(biproductItem, biproductAmount, biproductMeta);
				}


				elementName = (curObject = curObject.get("input").getAsJsonObject()).get("name").getAsString();
				outputAmount = curObject.has("amount") ? curObject.get("amount").getAsInt() : 1;
				IUseInfo inputUse;

				if (Util.isResourceName(elementName))
				{
					int var12 = curObject.has("meta") ? curObject.get("meta").getAsInt() : 0;
					inputUse = new ItemUseInfo(Registries.ITEM_REGISTRY.get(new ResourceName(elementName)), outputAmount, var12);
				} else
				{
					inputUse = new ResUseInfo(elementName, outputAmount);
				}

				new SeparatingRecipe(resourceName, inputUse, outputInstance, biproductInstance, biproductChance, recipeTime).register();
				RockSolid.getLogger().config("Loaded separating recipe " + resourceName + " for mod " + loadingMod.getDisplayName() + " with time " + recipeTime + ", input " + inputUse + " and output " + outputInstance + " with content pack " + pack.getName());
			}
		} else
		{
			RockSolid.getLogger().info("Separating recipe " + resourceName + " will not be loaded for mod " + loadingMod.getDisplayName() + " with content pack " + pack.getName() + " because it was disabled by another content pack!");
		}
	}

	@Override
	public void disableContent(IGameInstance game, ResourceName resourceName)
	{
		disabled.add(resourceName);
	}
}
