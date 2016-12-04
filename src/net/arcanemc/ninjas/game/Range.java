package net.arcanemc.ninjas.game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by eric on 11/25/16.
 */
public class Range {
    Location corner1;
    Location corner2;
    World world;

    public Range(World world, Location c1, Location c2){
        this.corner1 = c1;
        this.corner2 = c2;
        this.world = world;
    }

    public Location getCornerOne(){return corner1;}

    public Location getCornerTwo(){return corner2;}

    public Location getRandomSafeLocation(){ // TODO: Fix all of this
        Location random = getRandomLocation().add(.5, 0, .5);
        Block b = world.getBlockAt(random.subtract(0, 1, 0));
        if(!b.isLiquid() && b.getType()!=Material.CACTUS) {
            Block b1 = world.getBlockAt(random.add(0, 1, 0));
            if (b1.getType() == Material.AIR) {
                return random;
            }
        }
        return getRandomLocation();
    }

    private Location getRandomLocation(){
        Random num = new Random();
        int x,z;
        x = ThreadLocalRandom.current().nextInt(Math.min(corner1.getBlockX(), corner2.getBlockX()), Math.max(corner1.getBlockX(), corner2.getBlockX())+1);
        z = ThreadLocalRandom.current().nextInt(Math.min(corner1.getBlockZ(), corner2.getBlockZ()), Math.max(corner1.getBlockZ(), corner2.getBlockZ())+1);
        return new Location(world, x, world.getHighestBlockYAt(x, z), z);
    }
}
