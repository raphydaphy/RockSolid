package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.component.ComponentAssemblyIngredient;
import com.raphydaphy.rocksolid.gui.component.ComponentAssemblyPolaroid;
import com.raphydaphy.rocksolid.init.ModRecipes;
import com.raphydaphy.rocksolid.network.PacketAssemblyConstruct;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.*;
import de.ellpeck.rockbottom.api.gui.component.MenuComponent;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;

public class GuiAssemblyStation extends GuiContainer
{
	private static final ResourceName background = RockSolid.createRes("gui.assembly_station");
	private static final ResourceName arrows = RockSolid.createRes("gui.assembly_arrows");
	private static final ResourceName c = ResourceName.intern("gui.construction.search_bar");
	private static final ResourceName d = ResourceName.intern("gui.construction.search_bar_extended");
	private final List recipeList = new ArrayList();
	private final List ingredients = new ArrayList();
	private IRecipe recipe;
	private ComponentMenu componentMenu;
	private ComponentConstruct componentConstruct;

	private int OFFSET = 0;
	private final BiConsumer m = (Object var1x, Object var2x) ->
	{
		this.a();
	};
	private boolean isOpen;

	public GuiAssemblyStation(AbstractEntityPlayer var1)
	{
		super(var1, 136, 169);
		ShiftClickBehavior var2 = new ShiftClickBehavior(0, 7, 8, var1.getInv().getSlotAmount() - 1);
		this.shiftClickBehaviors.add(var2);
		this.shiftClickBehaviors.add(var2.reversed());
	}

	public final void init(IGameInstance game)
	{
		super.init(game);
		this.componentMenu = new ComponentMenu(this, -12 + OFFSET, 2, 12, 90, 1, 4, 6, 0, (new BoundBox(0.0D + OFFSET, 0.0D, 22.0D + OFFSET, 94.0D)).add((double) this.x, (double) this.y), ResourceName.intern("gui.construction.scroll_bar"));
		this.components.add(this.componentMenu);

		this.components.add(new ComponentProgressBar(this, 100 + OFFSET, 19, 33, 8, Color.DARK_GRAY.getRGB(), false, this::getDurability));
		this.components.add(new ComponentProgressBar(this, 100 + OFFSET, 34, 33, 8, Color.DARK_GRAY.getRGB(), false, this::getDurability));
		this.components.add(new ComponentProgressBar(this, 100 + OFFSET, 49, 33, 8, Color.DARK_GRAY.getRGB(), false, this::getDurability));
		this.components.add(new ComponentProgressBar(this, 100 + OFFSET, 64, 33, 8, Color.DARK_GRAY.getRGB(), false, this::getDurability));
		this.components.add(new ComponentProgressBar(this, 100 + OFFSET, 79, 33, 8, Color.DARK_GRAY.getRGB(), false, this::getDurability));

		this.a();
	}

	private float getDurability()
	{
		return new Random().nextInt(10) / 10f;
	}

	private void a()
	{
		this.componentMenu.clear();
		this.recipeList.clear();
		boolean var1 = false;
		Iterator var2 = ModRecipes.ASSEMBLY_STATION_RECIPES.values().iterator();

		while (true)
		{
			BasicRecipe var3;
			label67:
			do
			{
				while (var2.hasNext())
				{
					if ((var3 = (BasicRecipe) var2.next()).isKnown(this.player))
					{
						break label67;
					}

					this.recipeList.add(new ComponentAssemblyPolaroid(this, null, false));

				}

				if (!var1)
				{
					this.recipe = null;
				}

				// TODO: put constructable recipes at the top of the list
				//this.recipeList.sort((var0, var1x) ->
				//		Integer.compare(Boolean.compare(var0.recipe == null, var1x.recipe == null) << 1, Boolean.compare(var0.canConstruct, var1x.canConstruct)));

				var2 = this.recipeList.iterator();

				while (var2.hasNext())
				{
					ComponentAssemblyPolaroid var7 = (ComponentAssemblyPolaroid) var2.next();
					this.componentMenu.add((new MenuComponent(18, 20)).add(0, 2, var7));
				}

				this.componentMenu.organize();
				if (this.recipe != null)
				{
					this.a(ComponentAssemblyIngredient.getIngredientButtons(this.recipe, this, this.player));
				} else
				{
					this.a(Collections.emptyList());
				}

				this.a(this.recipe);
				return;
			} while (true);

			Inventory var8 = this.player.getInv();
			ComponentAssemblyPolaroid var9;
			(var9 = ComponentAssemblyPolaroid.getPolaroidButton(var3, this, this.player, var3.canConstruct(var8, var8))).isSelected = this.recipe == var3;
			if (var9.isSelected)
			{
				var1 = true;
			}

			this.recipeList.add(var9);
		}
	}

	public final boolean shouldCloseContainer()
	{
		return !this.isOpen;
	}

	private void a(List var1)
	{
		if (!this.ingredients.isEmpty())
		{
			this.components.removeAll(this.ingredients);
			this.ingredients.clear();
		}

		this.ingredients.addAll(var1);

		while (this.ingredients.size() < 8)
		{
			this.ingredients.add(new ComponentAssemblyIngredient(this, false, Collections.emptyList()));
		}

		this.components.addAll(this.ingredients);
		int var6 = 0;
		int var2 = 0;
		int var3 = 0;
		Iterator var4 = this.ingredients.iterator();

		while (var4.hasNext())
		{
			((ComponentAssemblyIngredient) var4.next()).setPos(var6 + 29 + OFFSET, var2 + 51);
			var6 += 16;
			++var3;
			if (var3 >= 4)
			{
				var3 = 0;
				var6 = 0;
				var2 += 19;
			}
		}

	}

	private void a(IRecipe var1)
	{
		if (this.componentConstruct != null)
		{
			this.components.remove(this.componentConstruct);
			this.componentConstruct = null;
		}

		if (var1 != null)
		{
			Inventory var2 = this.player.getInv();
			this.componentConstruct = var1.getConstructButton(this, this.player, this.recipe.canConstruct(var2, var2));
			this.componentConstruct.setPos(45 + OFFSET, 17);
			this.components.add(this.componentConstruct);
		}

	}

	public final void render(IGameInstance game, IAssetManager assetManager, IRenderer renderer)
	{
		assetManager.getTexture(background).draw((float) this.x + OFFSET, (float) this.y, 135.0F, 94.0F);

		float x1 = (float) this.x + OFFSET + 27;
		float y1 = (float) this.y + 27;

		int mouseX = (int) game.getRenderer().getMouseInGuiX();
		int mouseY = (int) game.getRenderer().getMouseInGuiY();

		boolean overArrow = mouseX >= x1 && mouseX < x1 + 6 && mouseY >= y1 && mouseY < y1 + 10;
		assetManager.getTexture(arrows).draw(x1, y1, x1 + 6, y1 + 10,overArrow ? 6 : 0, 0,overArrow ? 12.0F : 6, 10.0F);

		x1 += 60;

		overArrow = mouseX >= x1 && mouseX < x1 + 6 && mouseY >= y1 && mouseY < y1 + 10;
		assetManager.getTexture(arrows).draw(x1, y1, x1 + 6, y1 + 10,overArrow ? 6 : 0, 10,overArrow ? 12.0F : 6, 20.0F);

		if (this.recipe != null)
		{
			String recipeName = (this.recipe.getOutputs().get(0)).getDisplayName();
			assetManager.getFont().drawAutoScaledString((float) (this.x + 60 + OFFSET), (float) (this.y + 6), recipeName, 0.25F, 70, -16777216, 2147483647, true, false);
		}

		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + OFFSET), (float) (this.y + 3), "Stats", 0.25F, 22, -16777216, 2147483647, true, false);

		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + OFFSET), (float) (this.y + 14), "Energy Storage", 0.15F, 70, -16777216, 2147483647, true, false);
		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + OFFSET), (float) (this.y + 14 + 15), "Efficiency", 0.15F, 70, -16777216, 2147483647, true, false);
		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + OFFSET), (float) (this.y + 14 + 15 * 2), "Speed", 0.15F, 70, -16777216, 2147483647, true, false);
		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + OFFSET), (float) (this.y + 14 + 15 * 3), "Bonus Yield", 0.15F, 70, -16777216, 2147483647, true, false);
		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + OFFSET), (float) (this.y + 14 + 15 * 4), "Throughput", 0.15F, 70, -16777216, 2147483647, true, false);

		super.render(game, assetManager, renderer);
	}

	public final ResourceName getName()
	{
		return RockSolid.createRes("gui_assembly_station");
	}

	public final void onOpened(IGameInstance var1)
	{
		super.onOpened(var1);
		this.player.getInv().addChangeCallback(this.m);
	}

	public final void onClosed(IGameInstance var1)
	{
		super.onClosed(var1);
		this.player.getInv().removeChangeCallback(this.m);
	}

	public final boolean onMouseAction(IGameInstance var1, int var2, float var3, float var4)
	{
		if (super.onMouseAction(var1, var2, var3, var4))
		{
			return true;
		} else if (!Settings.KEY_GUI_ACTION_1.isKey(var2))
		{
			return false;
		} else
		{
			boolean var5;
			if (this.componentConstruct != null && this.componentConstruct.isMouseOver(var1))
			{
				if (RockBottomAPI.getNet().isClient())
				{
					RockBottomAPI.getNet().sendToServer(new PacketAssemblyConstruct(var1.getPlayer().getUniqueId(), Registries.ALL_CONSTRUCTION_RECIPES.getId(this.recipe), 1));
				} else if (this.recipe.isKnown(this.player))
				{
					this.recipe.playerConstruct(this.player, 1);
				}

				return true;
			} else
			{
				var5 = false;
				Iterator var6 = this.recipeList.iterator();

				while (true)
				{
					while (var6.hasNext())
					{
						ComponentAssemblyPolaroid var7;
						if ((var7 = (ComponentAssemblyPolaroid) var6.next()).recipe != null && var7.isMouseOverPrioritized(var1))
						{
							if (this.recipe != var7.recipe)
							{
								this.recipe = var7.recipe;
								var7.isSelected = true;
								this.a(var7.recipe);
								this.a(ComponentAssemblyIngredient.getIngredientButtons(var7.recipe,this, this.player));
							}

							var5 = true;
						} else
						{
							var7.isSelected = false;
						}
					}

					if (!var5 && this.recipe != null)
					{
						this.recipe = null;
						this.a((IRecipe) null);
						this.a(Collections.emptyList());
					}

					return var5;
				}
			}
		}
	}

	public final int getSlotOffsetX()
	{
		return 0;
	}

	public final int getSlotOffsetY()
	{
		return 99;
	}
}
