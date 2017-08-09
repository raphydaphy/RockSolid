package com.raphydaphy.rocksolid.item;

import java.util.List;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.entity.EntityRocket;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ItemAsteroidDataChip extends ItemBase
{
	private static final String name = "asteroidDataChip";

	public ItemAsteroidDataChip()
	{
		super(RockSolidAPILib.makeInternalRes(name));
		this.maxAmount = 1;
		this.register();
	}

	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced)
	{
		super.describeItem(manager, instance, desc, isAdvanced);

		String info[] = getChipInfo(instance, false);

		desc.addAll(manager.getFont().splitTextToLength(1000, 1f, true, info));
	}

	public static String[] getChipInfo(ItemInstance card, boolean doForce)
	{
		String[] info = new String[] { "ID: No Chip Inserted", "Size: ", "Primary Resource: " };
		if (card != null)
		{
			if (card.getItem().equals(RockSolidContent.asteroidDataChip))
			{
				DataSet data = card.getAdditionalData();
				if (data != null)
				{
					if (data.getInt("asteroidID") != 0)
					{
						info[0] = "ID: " + data.getInt("asteroidID");

						if (doForce)
						{
							if (data.getInt("asteroidSize") == 0)
							{
								data.addInt("asteroidSize", Util.RANDOM.nextInt(9) + 1);
							}
						}

						if (data.getInt("asteroidSize") != 0)
						{
							info[1] = "Size: " + data.getInt("asteroidSize");
						} else
						{
							info[2] = "Size: Not Analyzed Yet!";
						}

						if (doForce)
						{
							if (data.getString("asteroidResource") == null)
							{
								Tile primaryRes = EntityRocket.ores[Util.RANDOM.nextInt(EntityRocket.ores.length - 1)
										+ 1];
								data.addString("asteroidResource", primaryRes.getName().getDomain() + "/"
										+ primaryRes.getName().getResourceName());
							}
						}

						if (data.getString("asteroidResource") != null)
						{
							IResourceName primary = RockBottomAPI.createRes(data.getString("asteroidResource"))
									.addPrefix("item.");
							info[2] = "Primary Resource: "
									+ RockBottomAPI.getGame().getAssetManager().localize(primary);
							;
						} else
						{
							info[2] = "Primary Resource: Not Analyzed Yet!";
						}
					} else
					{
						info[0] = "ID: Not Discovered Yet!";
					}

					card.setAdditionalData(data);
				} else
				{
					info[0] = "ID: Not Discovered Yet!";
				}
			}
		}
		return info;
	}

}
