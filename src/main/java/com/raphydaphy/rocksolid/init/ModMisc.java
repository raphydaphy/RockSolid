package com.raphydaphy.rocksolid.init;

import org.lwjgl.glfw.GLFW;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.util.ConduitTileLayer;
import com.raphydaphy.rocksolid.world.WorldGenLakes;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.settings.Keybind;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ModMisc
{
	public static TileLayer CONDUIT_LAYER;
	public static Keybind KEY_CONDUIT_LAYER;

	public static void initKeybinds()
	{
		KEY_CONDUIT_LAYER = new Keybind(RockSolid.createRes("conduit_layer"), GLFW.GLFW_KEY_V, false).register();
	}

	public static void init()
	{
		RockBottomAPI.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_lakes"), WorldGenLakes.class);

		CONDUIT_LAYER = new ConduitTileLayer();
	}
}
