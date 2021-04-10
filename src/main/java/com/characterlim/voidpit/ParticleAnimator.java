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
    private BukkitRunnable task = null;

    public ParticleAnimator(World world, VoidPitPlugin instance) {
        this.world = world;
        this.plugin = instance;
    }

    public void animateItemDestroy(Location location) {
        this.world.spawnParticle(Particle.SMOKE_LARGE, location, ITEM_DESTROY_PARTICLE_COUNT);
    }

    public void animateAscend(Location startLocation, int height) {
        Location pos = startLocation;
        final int[] trackedHeight = {0};

        task = new BukkitRunnable() {
            @Override
            public void run() {
                world.spawnParticle(Particle.END_ROD, pos, 1);
                Vector dPos = new Vector(0, 1, 0);
                pos.add(dPos);
                trackedHeight[0] += dPos.getY();
                if(trackedHeight[0] >= height) task.cancel();
            }
        };

        task.runTaskTimer(plugin, 1, 2);
    }
}
