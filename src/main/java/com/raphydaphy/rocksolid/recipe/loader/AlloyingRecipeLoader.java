package com.raphydaphy.rocksolid.recipe.loader;

import com.google.common.base.Charsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.recipe.AlloyingRecipe;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.Registries;
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

public class AlloyingRecipeLoader implements IContentLoader {
    public static final ResourceName ID = RockSolid.createRes("alloying");
    private final Set<ResourceName> disabled = new HashSet<>();

    public AlloyingRecipeLoader() {
    }

    @Override
    public ResourceName getContentIdentifier() {
        return ID;
    }

    @Override
    public void loadContent(IGameInstance game, ResourceName resourceName, String path, JsonElement element, String elementName, IMod loadingMod, ContentPack pack) throws Exception {
        if (!this.disabled.contains(resourceName)) {
            if (AlloyingRecipe.REGISTRY.get(resourceName) != null) {
                RockSolid.getLogger().info("Alloying recipe with name " + resourceName + " already exists, not adding recipe for mod " + loadingMod.getDisplayName() + " with content pack " + pack.getName());
            } else {
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

                IUseInfo input1 = getInput(curObject, 1);
                IUseInfo input2 = getInput(curObject, 2);

                new AlloyingRecipe(resourceName, input1, input2, outputInstance, recipeTime).register();
                RockSolid.getLogger().config("Loaded alloying recipe " + resourceName + " for mod " + loadingMod.getDisplayName() + " with time " + recipeTime + ", input (" + input1 + " and " + input2 + ") and output " + outputInstance + " with content pack " + pack.getName());
            }
        } else {
            RockSolid.getLogger().info("Alloying recipe " + resourceName + " will not be loaded for mod " + loadingMod.getDisplayName() + " with content pack " + pack.getName() + " because it was disabled by another content pack!");
        }
    }

    private IUseInfo getInput(JsonObject recipe, int input) {
        String name = (recipe = recipe.get("input_" + input).getAsJsonObject()).get("name").getAsString();
        int amount = recipe.has("amount") ? recipe.get("amount").getAsInt() : 1;

        if (Util.isResourceName(name)) {
            int var12 = recipe.has("meta") ? recipe.get("meta").getAsInt() : 0;
            return new ItemUseInfo(Registries.ITEM_REGISTRY.get(new ResourceName(name)), amount, var12);
        } else {
            return new ResUseInfo(name, amount);
        }
    }

    @Override
    public void disableContent(IGameInstance game, ResourceName resourceName) {
        disabled.add(resourceName);
    }
}
