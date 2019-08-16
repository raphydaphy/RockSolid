package com.raphydaphy.rocksolid.particle;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.particle.Particle;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class RocketParticle extends Particle {
    private static final ResourceName fire = RockSolid.createRes("particles.fire");
    private static final ResourceName smoke = RockSolid.createRes("particles.smoke");

    private final float scale;

    public RocketParticle(IWorld world, double x, double y, double motionX, double motionY, int maxLife, float scale) {
        super(world, x, y, motionX, motionY, Util.RANDOM.nextInt((int) (maxLife * 2.5)) + maxLife);
        this.scale = scale;
    }

    @Override
    protected void applyMotion() {
        //this.motionY += 0.003D;
        this.motionX *= 0.95D;
        this.motionY *= 0.95D;
    }

    @Override
    public void render(IGameInstance var1, IAssetManager var2, IRenderer var3, float var4, float var5, int var6) {
        float var7 = this.scale * (1.0F - (float) this.life / (float) this.maxLife);
        ResourceName tex = fire;
        if (this.life / (float) this.maxLife > 0.3) {
            tex = smoke;
        }
        var2.getTexture(tex).draw(var4 - var7 / 2.0F, var5 - var7 / 2.0F, var7, var7);
    }
}
