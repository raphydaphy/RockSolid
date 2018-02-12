package com.raphydaphy.rocksolid;

import java.util.logging.Logger;

import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModRecipes;
import com.raphydaphy.rocksolid.init.ModTiles;

import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class RockSolid implements IMod
{

	public static RockSolid instance;

	private Logger modLogger;

	public RockSolid()
	{
		instance = this;
	}

	public static Logger getLogger()
	{
		return instance.modLogger;
	}

	public static IResourceName createRes(String name)
	{
		return RockBottomAPI.createRes(instance, name);
	}

	@Override
	public String getDisplayName()
	{
		return "RockSolid";
	}

	@Override
	public String getId()
	{
		return "rocksolid";
	}

	@Override
	public String getVersion()
	{
		return "0.1";
	}

	@Override
	public String getResourceLocation()
	{
		return "/assets/rocksolid";
	}

	@Override
	public String getDescription()
	{
		return "Improves your mining experience in Rock Bottom!";
	}

	@Override
	public void prePreInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler)
	{
		this.modLogger = apiHandler.createLogger(this.getDisplayName());
		
		ModMisc.initKeybinds();
	}

	@Override
	public void preInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler)
	{
		this.modLogger.info("Starting RockSolid for RockBottom");
	}

	@Override
	public void init(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler)
	{
		ModItems.init();
		ModTiles.init();
		
		ModRecipes.init();
		ModMisc.init();
		
		
	}

	@Override
	public void initAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler)
	{

	}
}
