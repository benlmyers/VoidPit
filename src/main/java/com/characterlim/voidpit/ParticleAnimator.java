package com.characterlim.voidpit;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class ParticleAnimator {

    private World world;

    public ParticleAnimator(World world) {
        this.world = world;
    }

    public void animateItemDestroy(Location location) {
        this.world.spawnParticle(Particle.SMOKE_LARGE, location, 25);
    }
}
