package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.component.ComponentAssemblyIngredient;
import com.raphydaphy.rocksolid.gui.component.ComponentAssemblyPolaroid;
import com.raphydaphy.rocksolid.init.ModRecipes;
import com.raphydaphy.rocksolid.network.PacketAssemblyConstruct;
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
import de.ellpeck.rockbottom.api.gui.component.ComponentMenu;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.gui.component.MenuComponent;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.inventory.Inventory;
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
	private final List<ComponentAssemblyPolaroid> recipeList = new ArrayList<>();
	private final List<ComponentAssemblyIngredient> ingredients = new ArrayList<>();
	private IRecipe recipe;
	private ComponentMenu componentMenu;
	private ComponentConstruct componentConstruct;

	private int offset = 0;
	private final BiConsumer<IInventory, Integer> m = (IInventory var1x, Integer var2x) -> this.a();

	public GuiAssemblyStation(AbstractEntityPlayer var1)
	{
		super(var1, 136, 169);
		ShiftClickBehavior var2 = new ShiftClickBehavior(0, 7, 8, var1.getInv().getSlotAmount() - 1);
		this.shiftClickBehaviors.add(var2);
		this.shiftClickBehaviors.add(var2.reversed());
	}

	@Override
	public final void init(IGameInstance game)
	{
		super.init(game);
		this.componentMenu = new ComponentMenu(this, -12 + offset, 2, 12, 90, 1, 4, 6, 0, (new BoundBox(0.0D + offset, 0.0D, 22.0D + offset, 94.0D)).add((double) this.x, (double) this.y), ResourceName.intern("gui.construction.scroll_bar"));
		this.components.add(this.componentMenu);

		this.components.add(new ComponentProgressBar(this, 100 + offset, 19, 33, 8, Color.DARK_GRAY.getRGB(), false, this::getDurability));
		this.components.add(new ComponentProgressBar(this, 100 + offset, 34, 33, 8, Color.DARK_GRAY.getRGB(), false, this::getDurability));
		this.components.add(new ComponentProgressBar(this, 100 + offset, 49, 33, 8, Color.DARK_GRAY.getRGB(), false, this::getDurability));
		this.components.add(new ComponentProgressBar(this, 100 + offset, 64, 33, 8, Color.DARK_GRAY.getRGB(), false, this::getDurability));
		this.components.add(new ComponentProgressBar(this, 100 + offset, 79, 33, 8, Color.DARK_GRAY.getRGB(), false, this::getDurability));

		this.a();

		selectRecipe(0);
	}

	private void selectRecipe(int id)
	{
		if (this.recipeList.size() > id)
		{
			ComponentAssemblyPolaroid firstRecipe = this.recipeList.get(id);

			this.recipe = firstRecipe.recipe;
			firstRecipe.isSelected = true;
			this.a(firstRecipe.recipe);
			this.a(ComponentAssemblyIngredient.getIngredientButtons(firstRecipe.recipe, this, this.player));
		} else
		{
			selectRecipe(0);
		}
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
		Iterator iter = ModRecipes.ASSEMBLY_STATION_RECIPES.values().iterator();

		do
		{
			BasicRecipe var3;
			uselessLoop:
			do
			{
				while (iter.hasNext())
				{
					if ((var3 = (BasicRecipe) iter.next()).isKnown(this.player))
					{
						break uselessLoop;
					}

					this.recipeList.add(new ComponentAssemblyPolaroid(this, null, false));

				}

				if (!var1)
				{
					this.recipe = null;
				}

				this.recipeList.sort((var0, var1x) -> Integer.compare(Boolean.compare(var0.recipe == null, var1x.recipe == null) << 1, Boolean.compare(var0.canConstruct, var1x.canConstruct)));

				iter = this.recipeList.iterator();

				while (iter.hasNext())
				{
					ComponentAssemblyPolaroid var7 = (ComponentAssemblyPolaroid) iter.next();
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
		} while (true);
	}

	private void a(List<ComponentAssemblyIngredient> var1)
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

		for (ComponentAssemblyIngredient ingredient : this.ingredients)
		{
			(ingredient).setPos(var6 + 29 + offset, var2 + 51);
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
			this.componentConstruct.setPos(45 + offset, 17);
			this.components.add(this.componentConstruct);
		}

	}

	@Override
	public final void render(IGameInstance game, IAssetManager assetManager, IRenderer renderer)
	{
		assetManager.getTexture(background).draw((float) this.x + offset, (float) this.y, 135.0F, 94.0F);

		float x1 = (float) this.x + offset + 27;
		float y1 = (float) this.y + 27;

		boolean overArrow = overArrow(game, true);
		assetManager.getTexture(arrows).draw(x1, y1, x1 + 6, y1 + 10, overArrow ? 6 : 0, 0, overArrow ? 12.0F : 6, 10.0F);
		overArrow = overArrow(game, false);
		x1 += 60;
		assetManager.getTexture(arrows).draw(x1, y1, x1 + 6, y1 + 10, overArrow ? 6 : 0, 10, overArrow ? 12.0F : 6, 20.0F);


		if (this.recipe != null)
		{
			String recipeName = (this.recipe.getOutputs().get(0)).getDisplayName();
			assetManager.getFont().drawAutoScaledString((float) (this.x + 60 + offset), (float) (this.y + 6), recipeName, 0.25F, 70, -16777216, 2147483647, true, false);
		}

		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + offset), (float) (this.y + 3), "Stats", 0.25F, 22, -16777216, 2147483647, true, false);

		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + offset), (float) (this.y + 14), "Energy Storage", 0.15F, 70, -16777216, 2147483647, true, false);
		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + offset), (float) (this.y + 14 + 15), "Efficiency", 0.15F, 70, -16777216, 2147483647, true, false);
		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + offset), (float) (this.y + 14 + 15 * 2), "Speed", 0.15F, 70, -16777216, 2147483647, true, false);
		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + offset), (float) (this.y + 14 + 15 * 3), "Bonus Yield", 0.15F, 70, -16777216, 2147483647, true, false);
		assetManager.getFont().drawAutoScaledString((float) (this.x + 116 + offset), (float) (this.y + 14 + 15 * 4), "Throughput", 0.15F, 70, -16777216, 2147483647, true, false);

		super.render(game, assetManager, renderer);
	}

	@Override
	public final ResourceName getName()
	{
		return RockSolid.createRes("gui_assembly_station");
	}

	@Override
	public final void onOpened(IGameInstance var1)
	{
		super.onOpened(var1);
		this.player.getInv().addChangeCallback(this.m);
	}

	@Override
	public final void onClosed(IGameInstance var1)
	{
		super.onClosed(var1);
		this.player.getInv().removeChangeCallback(this.m);
	}

	private boolean overArrow(IGameInstance game, boolean left)
	{
		float x1 = (float) this.x + offset + 27;
		float y1 = (float) this.y + 27;

		int mouseX = (int) game.getRenderer().getMouseInGuiX();
		int mouseY = (int) game.getRenderer().getMouseInGuiY();

		if (!left)
		{
			x1 += 60;
		}

		return mouseX >= x1 && mouseX < x1 + 6 && mouseY >= y1 && mouseY < y1 + 10;
	}

	private boolean nextRecipe(boolean left)
	{
		if (recipe == null)
		{
			selectRecipe(0);
			return true;
		} else
		{
			int id = -1;
			for (int i = 0; i < recipeList.size(); i++)
			{
				ComponentAssemblyPolaroid cap = recipeList.get(i);
				if (cap.recipe == this.recipe)
				{
					cap.isSelected = false;
					id = i;
					break;
				}
			}

			int newID = id + (left ? -1 : 1);
			if (newID > -1 && newID < recipeList.size())
			{
				selectRecipe(newID);
				return true;
			} else
			{
				selectRecipe(id);
				return false;
			}
		}
	}

	@Override
	public final boolean onMouseAction(IGameInstance game, int var2, float var3, float var4)
	{
		if (super.onMouseAction(game, var2, var3, var4))
		{
			return true;
		} else if (!Settings.KEY_GUI_ACTION_1.isKey(var2))
		{
			return false;
		} else if (overArrow(game, true))
		{
			return nextRecipe(true);
		} else if (overArrow(game, false))
		{
			return nextRecipe(false);
		} else
		{
			boolean selectedRecipe;
			if (this.componentConstruct != null && this.componentConstruct.isMouseOver(game))
			{
				if (RockBottomAPI.getNet().isClient())
				{
					RockBottomAPI.getNet().sendToServer(new PacketAssemblyConstruct(game.getPlayer().getUniqueId(), Registries.ALL_CONSTRUCTION_RECIPES.getId(this.recipe), 1));
				} else if (this.recipe.isKnown(this.player))
				{
					this.recipe.playerConstruct(this.player, 1);
				}

				return true;
			} else
			{
				selectedRecipe = false;
				Iterator<ComponentAssemblyPolaroid> recipeListIterator = this.recipeList.iterator();

				ComponentAssemblyPolaroid curRecipe = null;

				while (recipeListIterator.hasNext())
				{
					ComponentAssemblyPolaroid recipeIcon;
					if ((recipeIcon = recipeListIterator.next()).recipe != null && recipeIcon.isMouseOverPrioritized(game))
					{
						if (this.recipe != recipeIcon.recipe)
						{
							this.recipe = recipeIcon.recipe;
							recipeIcon.isSelected = true;
							this.a(recipeIcon.recipe);
							this.a(ComponentAssemblyIngredient.getIngredientButtons(recipeIcon.recipe, this, this.player));
						}

						selectedRecipe = true;
					} else if (recipeIcon.isSelected)
					{
						curRecipe = recipeIcon;
					}
				}

				if (selectedRecipe && curRecipe != null)
				{
					curRecipe.isSelected = false;
				}

				return selectedRecipe;
			}
		}
	}

	@Override
	public final int getSlotOffsetY()
	{
		return 99;
	}
}
