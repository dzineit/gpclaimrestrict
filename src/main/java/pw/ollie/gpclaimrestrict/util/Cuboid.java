package pw.ollie.gpclaimrestrict.util;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

public final class Cuboid {
    private final Location base;
    private final Vector3i size;

    private final int x;
    private final int y;
    private final int z;

    private int hash = 0;

    public Cuboid(Location base, Vector3i size) {
        this.base = base;
        this.size = size;

        this.x = (int) (base.getX() / size.getX());
        this.y = (int) (base.getY() / size.getY());
        this.z = (int) (base.getZ() / size.getZ());
    }

    public Location getBase() {
        return this.base;
    }

    public Vector3i getSize() {
        return this.size;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public boolean contains(Vector3i vec) {
        Vector3i max = MathUtil.add(base, size);
        return base.getX() <= vec.getX() && vec.getX() < max.getX()
                && base.getY() <= vec.getY() && vec.getY() < max.getY()
                && base.getZ() <= vec.getZ() && vec.getZ() < max.getZ();
    }

    public boolean contains(Location location) {
        if (!Objects.equals(location.getWorld(), base.getWorld())) {
            return false;
        }

        Vector3i max = MathUtil.add(base, size);
        return base.getX() <= location.getBlockX() && location.getBlockX() < max.getX()
                && base.getY() <= location.getBlockY() && location.getBlockY() < max.getY()
                && base.getZ() <= location.getBlockZ() && location.getBlockZ() < max.getZ();
    }

    public World getWorld() {
        return this.base.getWorld();
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = Objects.hash(base, size);
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Cuboid)) {
            return false;
        }

        Cuboid cuboid = (Cuboid) obj;
        return cuboid.size.getX() == size.getX() && cuboid.size.getY() == size.getY() && cuboid.size.getZ() == size.getZ() && cuboid.getWorld().equals(getWorld()) && cuboid.getX() == getX() && cuboid.getY() == getY() && cuboid.getZ() == getZ();
    }
}
