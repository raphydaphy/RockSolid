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
import de.ellpeck.rockbottom.api.gui.component.*;
import de.ellpeck.rockbottom.api.gui.component.construction.ComponentConstruct;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.*;
import java.util.function.BiConsumer;

public class GuiAssemblyStation extends GuiContainer
{
	private static final ResourceName itemsPanel = RockSolid.createRes("gui.assembly_items");
	private static final ResourceName recipePanel = RockSolid.createRes("gui.assembly_recipes");
	private static final ResourceName c = ResourceName.intern("gui.construction.search_bar");
	private static final ResourceName d = ResourceName.intern("gui.construction.search_bar_extended");
	private final List recipeList = new ArrayList();
	private final List ingredients = new ArrayList();
	private IRecipe recipe;
	private ComponentMenu componentMenu;
	private ComponentConstruct componentConstruct;
	private ComponentInputField searchBar;
	private BoundBox searchBarBounds;
	private String searchText = "";

	private int OFFSET = 20;
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
		this.searchBar = new ComponentInputField(this, 95 + OFFSET, 79, 70, 12, false, false, false, 64, false, (var1x) ->
		{
			if (!var1x.equals(this.searchText))
			{
				this.searchText = var1x;
				this.a();
			}

		});
		this.searchBar.setActive(false);
		this.components.add(this.searchBar);
		this.searchBarBounds = (new BoundBox(0.0D, 0.0D, 13.0D, 14.0D)).add((double) (this.x + 95 + OFFSET), (double) (this.y + 78));
		this.a();
	}

	private void a()
	{
		this.componentMenu.clear();
		this.recipeList.clear();
		boolean var1 = false;
		Iterator var2 = ModRecipes.ASSEMBLY_STATION_RECIPES.values().iterator();

		while (true)
		{
			boolean var10000;
			BasicRecipe var3;
			label67:
			do
			{
				while (var2.hasNext())
				{
					if ((var3 = (BasicRecipe) var2.next()).isKnown(this.player))
					{
						if (this.searchText.isEmpty())
						{
							break label67;
						}

						List var5 = var3.getOutputs();
						String var4 = this.searchText.toLowerCase(Locale.ROOT);
						Iterator var10 = var5.iterator();

						while (true)
						{
							if (var10.hasNext())
							{
								if (!((ItemInstance) var10.next()).getDisplayName().toLowerCase(Locale.ROOT).contains(var4))
								{
									continue;
								}

								var10000 = true;
								continue label67;
							}

							var10000 = false;
							continue label67;
						}
					}

					if (this.searchText.isEmpty())
					{
						this.recipeList.add(new ComponentAssemblyPolaroid(this, (IRecipe) null, false));
					}
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
			} while (!var10000);

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
			((ComponentAssemblyIngredient) var4.next()).setPos(var6 + 28 + OFFSET, var2 + 51);
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
			this.componentConstruct.setPos(44 + OFFSET, 17);
			this.components.add(this.componentConstruct);
		}

	}

	public final void render(IGameInstance var1, IAssetManager var2, IRenderer var3)
	{
		var2.getTexture(itemsPanel).draw((float) this.x + OFFSET, (float) this.y, 22.0F, 94.0F);
		var2.getTexture(recipePanel).draw((float) (this.x + 22 + 1 + OFFSET), (float) this.y, 72.0F, 94.0F);
		if (this.recipe != null)
		{
			String var4 = ((ItemInstance) this.recipe.getOutputs().get(0)).getDisplayName();
			var2.getFont().drawAutoScaledString((float) (this.x + 59 + OFFSET), (float) (this.y + 6), var4, 0.25F, 70, -16777216, 2147483647, true, false);
		}

		if (this.searchBar.isActive())
		{
			var2.getTexture(d).draw((float) (this.x + 95 + OFFSET), (float) (this.y + 78), 84.0F, 14.0F);
		} else
		{
			var2.getTexture(c).draw((float) (this.x + 95 + OFFSET), (float) (this.y + 78), 13.0F, 14.0F);
		}

		super.render(var1, var2, var3);
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
			if (this.searchBarBounds.contains((double) var3, (double) var4))
			{
				var5 = !this.searchBar.isActive();
				this.searchBar.setActive(var5);
				this.searchBar.setSelected(true);
				this.searchBarBounds.add((double) ((var5 ? 1 : -1) * 71), 0.0D);
				if (!this.searchText.isEmpty())
				{
					this.searchBar.setText("");
					this.searchText = "";
					this.a();
				}

				return true;
			} else if (this.componentConstruct != null && this.componentConstruct.isMouseOver(var1))
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

	public final boolean shouldDoFingerCursor(IGameInstance var1)
	{
		IRenderer var2 = var1.getRenderer();
		return this.searchBarBounds.contains((double) var2.getMouseInGuiX(), (double) var2.getMouseInGuiY()) || super.shouldDoFingerCursor(var1);
	}
}
