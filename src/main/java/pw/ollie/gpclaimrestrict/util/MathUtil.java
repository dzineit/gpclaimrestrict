package pw.ollie.gpclaimrestrict.util;

import org.bukkit.Location;

public final class MathUtil {
    public static Vector3i add(Location one, Vector3i two) {
        return new Vector3i(one.getBlockX() + two.getX(), one.getBlockY() + two.getY(), one.getBlockZ() + two.getZ());
    }

    private MathUtil() {
        throw new UnsupportedOperationException();
    }
}
