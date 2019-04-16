package pw.ollie.gpclaimrestrict.util;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

public final class Vector3i {
    private final int x, y, z;

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long lengthSquared() {
        return x * x + y * y + z * z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Vector3i add(Vector3i v) {
        return new Vector3i(x + v.x, y + v.y, z + v.z);
    }

    public Vector3i subtract(Vector3i v) {
        return new Vector3i(x - v.x, y - v.y, z - v.z);
    }

    public Vector3i negate() {
        return new Vector3i(-x, -y, -z);
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vector3i)) {
            return false;
        }

        Vector3i other = (Vector3i) obj;
        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
