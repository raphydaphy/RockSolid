package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.api.energy.IItemWithPower;
import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;
import com.raphydaphy.rocksolid.api.util.IHasInventory;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.network.PacketChargerItem;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityCharger extends TileEntityPowered implements IHasInventory
{

    public static final int INPUT = 0;
    public final ContainerInventory inventory;
    protected int powerStored;
    private boolean shouldSync = false;
    
    public TileEntityCharger(final IWorld world, final int x, final int y) 
    {
        super(world, x, y, 100000, 0);
        this.inventory = new ContainerInventory(this, 1);
    }
    
    @Override
    protected boolean needsSync() 
    {
        return super.needsSync() || shouldSync;
    }
    
    @Override
    protected void onSync()
    {
    	super.onSync();
    	shouldSync = false;
    }
    
    @Override
    protected boolean tryTickAction() 
    {
		if (this.powerStored > 0) 
        {
			ItemInstance input = this.inventory.get(0);
			if (input != null)
			{
				if (IItemWithPower.class.isAssignableFrom(input.getItem().getClass()))
				{
					DataSet itemData;
					int itemPowerStored = 0;
					int itemMaxPower = ((IItemWithPower)input.getItem()).getMaxPower();
					int itemMaxTransfer = ((IItemWithPower)input.getItem()).getMaxTransfer();
					
					if (input.getAdditionalData() != null)
					{
						itemData = input.getAdditionalData();
						itemPowerStored = itemData.getInt("itemPowerStored");
					}
					else
					{
						itemData = new DataSet();
						input.setAdditionalData(itemData);
					}
					
					if (itemPowerStored < itemMaxPower)
					{
						if (itemPowerStored <= (itemMaxPower - itemMaxTransfer))
						{
							if (this.powerStored >= itemMaxTransfer)
							{
								
								if (RockBottomAPI.getNet().isClient() == false)
								{
									itemData.addInt("itemPowerStored", itemPowerStored + itemMaxTransfer);
									this.powerStored -= itemMaxTransfer;
									shouldSync = true;
									
									if (RockBottomAPI.getNet().isServer())
									{
										ItemInstance itemToSend = this.inventory.get(0).copy();
										itemToSend.setAdditionalData(itemData);
										DataSet itemToSendSet = new DataSet();
										itemToSend.save(itemToSendSet);
										RockBottomAPI.getNet().sendToAllPlayers(world, new PacketChargerItem(x, y, itemToSendSet));
									}
								}
								return true;
							}
							else
							{
								
								if (RockBottomAPI.getNet().isClient() == false)
								{
									itemData.addInt("itemPowerStored", itemPowerStored + this.powerStored);
									this.powerStored = 0;
									shouldSync = true;
									
									if (RockBottomAPI.getNet().isServer())
									{
										ItemInstance itemToSend = this.inventory.get(0).copy();
										itemToSend.setAdditionalData(itemData);
										DataSet itemToSendSet = new DataSet();
										itemToSend.save(itemToSendSet);
										RockBottomAPI.getNet().sendToAllPlayers(world, new PacketChargerItem(x, y, itemToSendSet));
									}
								}
								return true;
								
							}
						}
						else if (this.powerStored > 100)
						{
							if (itemMaxPower - itemPowerStored >= 100)
							{
								
								if (RockBottomAPI.getNet().isClient() == false)
								{
									itemData.addInt("itemPowerStored", itemPowerStored + 100);
									this.powerStored -= 100;
									shouldSync = true;
									
									if (RockBottomAPI.getNet().isServer())
									{
										ItemInstance itemToSend = this.inventory.get(0).copy();
										itemToSend.setAdditionalData(itemData);
										DataSet itemToSendSet = new DataSet();
										itemToSend.save(itemToSendSet);
										RockBottomAPI.getNet().sendToAllPlayers(world, new PacketChargerItem(x, y, itemToSendSet));
									}
								}
								
								return true;
							}
							else if (itemMaxPower - itemPowerStored >= 50)
							{
								
								if (RockBottomAPI.getNet().isClient() == false)
								{
									itemData.addInt("itemPowerStored", itemPowerStored + 50);
									this.powerStored -= 50;
									shouldSync = true;
									
									if (RockBottomAPI.getNet().isServer())
									{
										ItemInstance itemToSend = this.inventory.get(0).copy();
										itemToSend.setAdditionalData(itemData);
										DataSet itemToSendSet = new DataSet();
										itemToSend.save(itemToSendSet);
										RockBottomAPI.getNet().sendToAllPlayers(world, new PacketChargerItem(x, y, itemToSendSet));
									}
								}
								return true;
							}
							else
							{
								
								if (RockBottomAPI.getNet().isClient() == false)
								{
									itemData.addInt("itemPowerStored", itemPowerStored + 1);
									this.powerStored -= 1;
									shouldSync = true;
									
									if (RockBottomAPI.getNet().isServer())
									{
										ItemInstance itemToSend = this.inventory.get(0).copy();
										itemToSend.setAdditionalData(itemData);
										DataSet itemToSendSet = new DataSet();
										itemToSend.save(itemToSendSet);
										RockBottomAPI.getNet().sendToAllPlayers(world, new PacketChargerItem(x, y, itemToSendSet));
									}
								}
								return true;
							}
						}
						else if (this.powerStored <= (itemMaxPower - itemPowerStored))
						{
							
							if (RockBottomAPI.getNet().isClient() == false)
							{
								itemData.addInt("itemPowerStored", itemPowerStored + this.powerStored);
								this.powerStored = 0;
								shouldSync = true;
								
								if (RockBottomAPI.getNet().isServer())
								{
									ItemInstance itemToSend = this.inventory.get(0).copy();
									itemToSend.setAdditionalData(itemData);
									DataSet itemToSendSet = new DataSet();
									itemToSend.save(itemToSendSet);
									RockBottomAPI.getNet().sendToAllPlayers(world, new PacketChargerItem(x, y, itemToSendSet));
								}
							}
							return true;
						}
					}
					
				}
			}
        }
        return false;
    }
    
    @Override
    public void save(final DataSet set, final boolean forSync) 
    {
        super.save(set, forSync);
        if (!forSync) {
            this.inventory.save(set);
        }
        set.addInt("powerStored", this.powerStored);
        set.addBoolean("shouldSync", this.shouldSync);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) 
    {
        super.load(set, forSync);
        if (!forSync) {
            this.inventory.load(set);
        }
        this.powerStored = set.getInt("powerStored");
        this.shouldSync = set.getBoolean("shouldSync");
    }

	@Override
	public Inventory getInventory() 
	{
		return this.inventory;
	}
	
	public void setItem(ItemInstance item)
	{
		this.inventory.set(0, item);
	}

	@Override
	public List<Integer> getInputs() 
	{
		List<Integer> insertSlots = new ArrayList<Integer>();
		insertSlots.add(0);
		return insertSlots;
	}

	@Override
	public List<Integer> getOutputs() 
	{
		return null;
	}

	@Override
	protected void setPower(int power) 
	{
		this.powerStored = power;
	}

	@Override
	protected int getPower() 
	{
		return this.powerStored;
	}

	@Override
	protected void onActiveChange(boolean active) {
		this.world.causeLightUpdate(this.x, this.y);
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
