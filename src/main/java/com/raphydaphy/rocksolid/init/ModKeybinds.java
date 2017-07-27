package com.raphydaphy.rocksolid.init;

import org.newdawn.slick.Input;

import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.settings.Keybind;

public class ModKeybinds {
	public static Keybind keyJetpackEngine = new Keybind(RockSolidLib.makeRes("keyJetpackEngine"), Input.KEY_Q, false);
	public static Keybind keyJetpackHover = new Keybind(RockSolidLib.makeRes("keyJetpackHover"), Input.KEY_R, false);
	public static Keybind keyWrenchMode = new Keybind(RockSolidLib.makeRes("keyWrenchMode"), Input.KEY_LSHIFT, false);
	
	public static void init()
	{
		RockBottomAPI.KEYBIND_REGISTRY.register(RockSolidLib.makeRes("keyJetpackEngine"), ModKeybinds.keyJetpackEngine);
		RockBottomAPI.KEYBIND_REGISTRY.register(RockSolidLib.makeRes("keyJetpackHover"), ModKeybinds.keyJetpackHover);
		RockBottomAPI.KEYBIND_REGISTRY.register(RockSolidLib.makeRes("keyWrenchMode"), ModKeybinds.keyWrenchMode);
	}
}
	