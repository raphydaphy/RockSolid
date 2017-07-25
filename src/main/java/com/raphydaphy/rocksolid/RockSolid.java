package com.raphydaphy.rocksolid;

import org.newdawn.slick.util.Log;

import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.init.ModEvents;
import com.raphydaphy.rocksolid.init.ModFluids;
import com.raphydaphy.rocksolid.init.ModGenerators;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.init.ModKeybinds;
import com.raphydaphy.rocksolid.init.ModPackets;
import com.raphydaphy.rocksolid.init.ModRecipies;

import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.mod.IMod;

public class RockSolid implements IMod{
	public static RockSolid INSTANCE;
	public RockSolid(){
		INSTANCE = this;
		
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
        return "@VERSION@";
    }

    @Override
    public String getResourceLocation()
    {
        return "/assets/rocksolid";
    }

    @Override
    public String getDescription()
    {
        return "Adds more machines and ore!";
    }

    @Override
    public void initAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler)
    {
    	ModKeybinds.init();
    }
    
    public void preInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler)
    {
    	
    }
    
    @Override
    public void init(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler)
    {
    	Log.info("Starting RockSolid");
    	
    	ModItems.init();
    	ModTiles.init();
    	ModGenerators.init();
    	ModEvents.init(eventHandler);
    	ModPackets.init();
    	ModFluids.init();
    }
    
    @Override
    public void postInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler)
    {
    	ModRecipies.init();
    	
    }
}
