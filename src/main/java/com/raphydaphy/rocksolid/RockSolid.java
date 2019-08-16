package com.raphydaphy.rocksolid;

import com.raphydaphy.rocksolid.init.*;
import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.logging.Logger;

public class RockSolid implements IMod {

    public static RockSolid instance;

    private Logger modLogger;

    public RockSolid() {
        instance = this;
    }

    public static Logger getLogger() {
        return instance.modLogger;
    }

    public static ResourceName createRes(String name) {
        return new ResourceName(instance, name);
    }

    @Override
    public String getDisplayName() {
        return "RockSolid";
    }

    @Override
    public String getId() {
        return "rocksolid";
    }

    @Override
    public String getVersion() {
        return "3.0";
    }

    @Override
    public String getResourceLocation() {
        return "assets/rocksolid";
    }

    @Override
    public String getContentLocation() {
        return "assets/rocksolid/content";
    }

    @Override
    public String getDescription() {
        return "Solid content for a rocky universe.";
    }

    @Override
    public void prePreInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        this.modLogger = apiHandler.createLogger(this.getDisplayName());
    }

    @Override
    public void preInit(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        this.modLogger.info("Starting RockSolid for RockBottom");
    }

    @Override
    public void init(IGameInstance game, IApiHandler apiHandler, IEventHandler eventHandler) {
        ModItems.init();
        ModTiles.init();

        ModMisc.init();

        ModRecipes.init();
        ModEvents.init(eventHandler);
    }

    @Override
    public void postInitAssets(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler) {

    }
}
