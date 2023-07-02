// 
// Decompiled by Procyon v0.5.36
// 

package fr.edminecoreteam.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Cuboid
{
    private final World world;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minZ;
    private int maxZ;
    
    public Cuboid(final Location loc1, final Location loc2) {
        this(loc1.getWorld(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
    }
    
    public Cuboid(final World world, final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        this.world = world;
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }
    
    public World getWorld() {
        return this.world;
    }
    
    public int getMinX() {
        return this.minX;
    }
    
    public int getMinY() {
        return this.minY;
    }
    
    public int getMinZ() {
        return this.minZ;
    }
    
    public int getMaxX() {
        return this.maxX;
    }
    
    public int getMaxY() {
        return this.maxY;
    }
    
    public int getMaxZ() {
        return this.maxZ;
    }
    
    public boolean contains(final Cuboid cuboid) {
        return cuboid.getWorld().equals(this.world) && cuboid.getMinX() >= this.minX && cuboid.getMaxX() <= this.maxX && cuboid.getMinY() >= this.minY && cuboid.getMaxY() <= this.maxY && cuboid.getMinZ() >= this.minZ && cuboid.getMaxZ() <= this.maxZ;
    }
    
    public boolean contains(final Location location) {
        return this.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
    
    public boolean contains(final int x, final int y, final int z) {
        return x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY && z >= this.minZ && z <= this.maxZ;
    }
    
    public boolean overlaps(final Cuboid cuboid) {
        return cuboid.getWorld().equals(this.world) && cuboid.getMinX() <= this.maxX && cuboid.getMinY() <= this.maxY && cuboid.getMinZ() <= this.maxZ && this.minZ <= cuboid.getMaxX() && this.minY <= cuboid.getMaxY() && this.minZ <= cuboid.getMaxZ();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Cuboid)) {
            return false;
        }
        final Cuboid other = (Cuboid)obj;
        return this.world.equals(other.world) && this.minX == other.minX && this.minY == other.minY && this.minZ == other.minZ && this.maxX == other.maxX && this.maxY == other.maxY && this.maxZ == other.maxZ;
    }
    
    @Override
    public String toString() {
        return "Cuboid[world:" + this.world.getName() + ", minX:" + this.minX + ", minY:" + this.minY + ", minZ:" + this.minZ + ", maxX:" + this.maxX + ", maxY:" + this.maxY + ", maxZ:" + this.maxZ + "]";
    }
    
    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }
}
