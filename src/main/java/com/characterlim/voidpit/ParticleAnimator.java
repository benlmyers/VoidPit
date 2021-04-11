package com.characterlim.voidpit;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleAnimator {

    private World world;
    private VoidPitPlugin plugin;
    private static final int ITEM_DESTROY_PARTICLE_COUNT = 25;
    private static final int TPS = 20;
    private static final int ASCEND_ANIMATION_TICKS = TPS * 3;
    private BukkitRunnable task = null;

    public ParticleAnimator(World world, VoidPitPlugin instance) {
        this.world = world;
        this.plugin = instance;
    }

    public void animateItemDestroy(Location location) {
        this.world.spawnParticle(Particle.SMOKE_LARGE, location, ITEM_DESTROY_PARTICLE_COUNT, 0, 1, 0, 0);
    }

    public void animateAscend(Location startLocation) {

        Location pos = startLocation;
        Location endLocation = Config.Hologram.pos;

        Vector dr = endLocation.subtract(startLocation).toVector().multiply(0.01 /*1.0 / ASCEND_ANIMATION_TICKS*/);
        final int[] tickCount = {0};

        task = new BukkitRunnable() {
            @Override
            public void run() {
                world.spawnParticle(Particle.END_ROD, pos, 1, 0, 0, 0, 0);
                Vector dPos = dr;
                pos.add(dPos);
                tickCount[0] += 1;
                if(tickCount[0] >= ASCEND_ANIMATION_TICKS) task.cancel();
            }
        };

        task.runTaskTimer(plugin, 1, 1);
    }
}
