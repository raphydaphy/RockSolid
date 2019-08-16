package com.raphydaphy.rocksolid.gui;

import com.google.common.base.Supplier;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.component.ComponentCustomText;
import com.raphydaphy.rocksolid.gui.component.ComponentCustomText.TextDirection;
import com.raphydaphy.rocksolid.network.PacketConduitUpdate;
import com.raphydaphy.rocksolid.tile.conduit.TileConduit;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitMode;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitSide;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class GuiConduit extends GuiContainer {
    private final TileEntityConduit te;
    private final Pos2 pos;
    private ConduitSide side;
    private ConduitMode mode;

    public GuiConduit(AbstractEntityPlayer player, TileEntityConduit te) {
        super(player, 136, 132);
        this.te = te;
        this.pos = new Pos2(te.x, te.y);
        if (!(te.world.isServer() && te.world.isDedicatedServer())) {
            this.side = ConduitSide.getByDirection(TileConduit.getMousedConduitPart(RockBottomAPI.getGame(), player.world));
        }
        this.mode = te.getMode(pos, side, false);
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);

        if (side != null && mode != null) {
            IPacket updatePacket = new PacketConduitUpdate(game.getPlayerWorld().getSubName(), te.x, te.y, mode, side);
            if (game.getWorld().isClient()) {
                RockBottomAPI.getNet().sendToServer(updatePacket);
            } else {
                updatePacket.handle(game, null);
            }
        }

        this.components.add(new ComponentCustomText(this, 35, 16, 76, 1, 0.3f, TextDirection.CENTER, "Direction"));
        this.components.add(new ComponentCustomText(this, 103, 16, 50, 1, 0.3f, TextDirection.CENTER,
                side == null ? "NONE" : side.toString()));

        this.components.add(new ComponentButton(this, 124, 11, 10, 10, () -> true, ">"));
        this.components.add(new ComponentButton(this, 73, 11, 10, 10, () -> true, "<"));

        this.components.add(new ComponentCustomText(this, 35, 31, 76, 1, 0.3f, TextDirection.CENTER, "Mode"));
        this.components.add(new ComponentCustomText(this, 103, 31, 50, 1, 0.3f, TextDirection.CENTER,
                mode == null ? "NULL" : RockBottomAPI.getGame().getAssetManager().localize(mode.name)));

        this.components.add(new ComponentButton(this, 124, 26, 10, 10, (Supplier<Boolean>) () ->
        {
            if (mode != null) {
                List<ConduitMode> all;
                if (mode.isConduit()) {
                    all = ConduitMode.getConduitModes();
                } else {
                    all = ConduitMode.getInvModes();
                }
                int index = all.indexOf(mode);
                if (index + 1 < all.size()) {
                    mode = all.get(index + 1);
                } else {
                    mode = all.get(0);
                }
                components.clear();
                init(game);
                return true;
            }
            return false;
        }, ">"));
        this.components.add(new ComponentButton(this, 73, 26, 10, 10, (Supplier<Boolean>) () ->
        {
            if (mode != null) {

                List<ConduitMode> all;
                if (mode.isConduit()) {
                    all = ConduitMode.getConduitModes();
                } else {
                    all = ConduitMode.getInvModes();
                }
                int index = all.indexOf(mode);
                if (index - 1 >= 0) {
                    mode = all.get(index - 1);
                } else {
                    mode = all.get(all.size() - 1);
                }
                components.clear();
                init(game);
                return true;
            }
            return false;
        }, "<"));
    }

    @Override
    public ResourceName getName() {
        return RockSolid.createRes("gui_conduit");
    }

}
