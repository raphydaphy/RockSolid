package com.raphydaphy.rocksolid.init;

import org.newdawn.slick.Input;

import com.raphydaphy.rocksolid.RockSolid;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.settings.Keybind;

public class ModKeybinds
{
	public static Keybind keyJetpackEngine = new Keybind(
			RockBottomAPI.createRes(RockSolid.INSTANCE, "keyJetpackEngine"), Input.KEY_Q, false);
	public static Keybind keyJetpackHover = new Keybind(RockBottomAPI.createRes(RockSolid.INSTANCE, "keyJetpackHover"),
			Input.KEY_R, false);
	public static Keybind keyWrenchMode = new Keybind(RockBottomAPI.createRes(RockSolid.INSTANCE, "keyWrenchMode"),
			Input.KEY_LSHIFT, false);

	public static void init()
	{
		RockBottomAPI.KEYBIND_REGISTRY.register(RockBottomAPI.createRes(RockSolid.INSTANCE, "keyJetpackEngine"),
				ModKeybinds.keyJetpackEngine);
		RockBottomAPI.KEYBIND_REGISTRY.register(RockBottomAPI.createRes(RockSolid.INSTANCE, "keyJetpackHover"),
				ModKeybinds.keyJetpackHover);
		RockBottomAPI.KEYBIND_REGISTRY.register(RockBottomAPI.createRes(RockSolid.INSTANCE, "keyWrenchMode"),
				ModKeybinds.keyWrenchMode);
	}
}
