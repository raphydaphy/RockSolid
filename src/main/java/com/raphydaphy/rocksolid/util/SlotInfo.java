package com.raphydaphy.rocksolid.util;

import com.google.common.base.Predicate;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Direction;

import java.util.ArrayList;
import java.util.List;

public class SlotInfo
{
	private final int id;
	private final SlotType type;
	private final Predicate<ItemInstance> allowedInput;

	public SlotInfo(int id, SlotType type)
	{
		this(id, type, null);
	}

	public SlotInfo(int id, SlotType type, Predicate<ItemInstance> allowedInput)
	{
		this.id = id;
		this.type = type;
		this.allowedInput = allowedInput;

	}

	public int getID()
	{
		return this.id;
	}

	public SlotType getType()
	{
		return this.type;
	}

	public Predicate<ItemInstance> getPredicate()
	{
		if (type == SlotType.OUTPUT || type == SlotType.NONE)
		{
			return instance -> {
				return false;
			};
		} else if (allowedInput == null)
		{
			return instance ->
			{
				return true;
			};
		}
		return this.allowedInput;
	}

	public boolean accepts(ItemInstance item)
	{
		if (getType() == SlotType.INPUT || getType() == SlotType.ALL)
		{
			if (allowedInput != null)
			{
				return allowedInput.test(item);
			} else
			{
				return true;
			}
		}
		return false;
	}

	public static List<Integer> toInputList(List<SlotInfo> slots)
	{
		List<Integer> inputSlots = new ArrayList<>();
		for (SlotInfo slot : slots)
		{
			if (slot.getType() == SlotType.INPUT || slot.getType() == SlotType.ALL)
			{
				inputSlots.add(slot.getID());
			}
		}
		return inputSlots;
	}

	public static List<Integer> toInputList(List<SlotInfo> slots, ItemInstance item, Direction dir)
	{
		List<Integer> inputSlots = new ArrayList<>();
		for (SlotInfo slot : slots)
		{
			if (slot.getType() == SlotType.INPUT || slot.getType() == SlotType.ALL)
			{
				if (slot.accepts(item))
				{
					inputSlots.add(slot.getID());
				}
			}
		}
		return inputSlots;
	}

	public static List<Integer> toOutputList(List<SlotInfo> slots)
	{
		List<Integer> inputSlots = new ArrayList<>();
		for (SlotInfo slot : slots)
		{
			if (slot.getType() == SlotType.OUTPUT || slot.getType() == SlotType.ALL)
			{
				inputSlots.add(slot.getID());
			}
		}
		return inputSlots;
	}

	public static List<SlotInfo> makeList(SimpleSlotInfo... infos)
	{
		List<SlotInfo> list = new ArrayList<>();

		int id = 0;
		for (SimpleSlotInfo info : infos)
		{
			list.add(new SlotInfo(id++, info.getType(), info.getPredicate()));
			System.out.println("Added new slot with ID: " + list.get(list.size() - 1).getID());
		}

		return list;
	}

	public static class SimpleSlotInfo
	{

		private final SlotType type;
		private final Predicate<ItemInstance> accepts;

		public SimpleSlotInfo(SlotType type)
		{
			this(type, null);
		}

		public SimpleSlotInfo(SlotType type, Predicate<ItemInstance> accepts)
		{
			this.type = type;
			this.accepts = accepts;
		}

		public SlotType getType()
		{
			return this.type;
		}

		public Predicate<ItemInstance> getPredicate()
		{
			return this.accepts;
		}

	}

	public enum SlotType
	{
		INPUT, OUTPUT, ALL, NONE
	}
}
