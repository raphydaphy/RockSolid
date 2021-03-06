package com.raphydaphy.rocksolid.recipe.category;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.recipe.SeparatingRecipe;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.construction.compendium.CompendiumCategory;
import de.ellpeck.rockbottom.api.construction.compendium.construction.ConstructionRecipe;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentIngredient;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;
import java.util.Set;

public class SeparatingCategory extends CompendiumCategory {
    public SeparatingCategory() {
        super(RockSolid.createRes("separating"));
    }

    @Override
    public final ResourceName getIcon(IGameInstance var1, IAssetManager var2, IRenderer var3) {
        return RockSolid.createRes("gui.separating_icon");
    }

    @Override
    public final ResourceName getBackgroundPicture(Gui var1, IAssetManager var2) {
        return RockSolid.createRes("gui.separating_page");
    }

    @Override
    public final int getMaxIngredientAmount(Gui var1, List var2) {
        return 1;
    }

    @Override
    public final Pos2 getIngredientPosition(Gui var1, ComponentIngredient var2, int var3) {
        return new Pos2(102, 64);
    }

    @Override
    public final Set<SeparatingRecipe> getRecipes() {
        return SeparatingRecipe.REGISTRY.values();
    }

    @Override
    public final boolean shouldDisplay(AbstractEntityPlayer player) {
        return ConstructionRecipe.forName(RockSolid.createRes("separator")).isKnown(player);
    }
}
