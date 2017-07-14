package com.raphydaphy.rocksolid.init;

import org.newdawn.slick.Input;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.settings.Settings.Keybind;

public class ModKeybinds {
	public static Keybind keyJetpackEngine;
	public static Keybind keyJetpackHover;
	public static Keybind keyWrenchMode;
	
	public static void init()
	{
		keyJetpackEngine = RockBottomAPI.getGame().getSettings().new Keybind("jetpackEngine", Input.KEY_Q);
		keyJetpackHover = RockBottomAPI.getGame().getSettings().new Keybind("jetpackHover", Input.KEY_R);
		keyWrenchMode = RockBottomAPI.getGame().getSettings().new Keybind("wrenchMode", Input.KEY_LSHIFT);
	}
}
	