package dev.hytalemodding;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import dev.hytalemodding.events.PlayerDamageSystem;

import javax.annotation.Nonnull;

public class MagicHitParticle extends JavaPlugin {

    public MagicHitParticle(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        this.getEntityStoreRegistry().registerSystem(new PlayerDamageSystem());
    }
}