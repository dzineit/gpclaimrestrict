package pw.ollie.gpclaimrestrict.util;

import org.bukkit.Location;

public final class MathUtil {
    public static Vector3i add(Location one, Vector3i two) {
        return new Vector3i(one.getBlockX() + two.getX(), one.getBlockY() + two.getY(), one.getBlockZ() + two.getZ());
    }

    public static boolean containsAny(Cuboid cuboid, Location... locations) {
        for (Location location : locations) {
            if (cuboid.contains(location)) {
                return true;
            }
        }

        return false;
    }

    private MathUtil() {
        throw new UnsupportedOperationException();
    }
}
