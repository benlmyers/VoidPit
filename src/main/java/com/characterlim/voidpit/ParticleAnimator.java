package com.characterlim.voidpit;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static java.lang.Math.*;

public class ParticleAnimator {

    private final World world;
    private final VoidPitPlugin plugin;
    private static final int ITEM_DESTROY_PARTICLE_COUNT = 25;
    private static final int TPS = 20;
    private static final int ASCEND_ANIMATION_TICKS = TPS * 7;

    public ParticleAnimator(World world, VoidPitPlugin instance) {
        this.world = world;
        this.plugin = instance;
    }

    public void animateItemDestroy(Location location) {
        this.world.spawnParticle(Particle.SMOKE_LARGE, location, ITEM_DESTROY_PARTICLE_COUNT, 0, 1, 0, 0);
    }

    public void animateAscend(Location startLocation, CompletionHandler handler) {

        Location pos = startLocation;

        Location endLocation = new Location(Config.Hologram.pos.getWorld(), Config.Hologram.pos.getX(), Config.Hologram.pos.getY(), Config.Hologram.pos.getZ());

        double height = endLocation.getY() - startLocation.getY();
        double triggerHeight = startLocation.getY() + (height / 2);

        Vector dr = endLocation.subtract(startLocation).toVector().multiply(1.0 / ASCEND_ANIMATION_TICKS);
        final int[] tickCount = {0};

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                Vector spawnPos = new Vector(pos.getX(), pos.getY(), pos.getZ());
                double offset = height/2 - Math.abs(triggerHeight - pos.getY());
                spawnPos.add(new Vector(0.3 * offset * sin(tickCount[0] * 0.05), 0, 0.3 * offset * cos(tickCount[0] * 0.05)));
                Location spawnLoc = new Location(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                world.spawnParticle(Particle.END_ROD, spawnLoc, 1, 0, 0, 0, 0);
                int dt = 1 + (int)floor(tickCount[0] / 50.0);
                tickCount[0] += dt;
                Vector change = new Vector(dr.getX(), dr.getY(), dr.getZ()).multiply(dt);
                pos.add(change);
                plugin.getLogger().info("offset = " + offset);
                if(tickCount[0] >= ASCEND_ANIMATION_TICKS) {
                    world.spawnParticle(Particle.FIREWORKS_SPARK, 1, 0, 0, 0);
                    handler.onCompletion();
                    this.cancel();
                }
            }
        };

        task.runTaskTimer(plugin, 1, 1);
    }
}
