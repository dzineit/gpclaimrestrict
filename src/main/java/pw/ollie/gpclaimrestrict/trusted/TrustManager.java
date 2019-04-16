package pw.ollie.gpclaimrestrict.trusted;

import pw.ollie.gpclaimrestrict.GPCRPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class TrustManager {
    private final GPCRPlugin plugin;
    private final Map<UUID, Set<UUID>> nearbyTrusted;

    public TrustManager(GPCRPlugin plugin) {
        this.plugin = plugin;
        this.nearbyTrusted = new HashMap<>();
    }

    public boolean isTrustedNearby(UUID truster, UUID trustee) {
        Set<UUID> trusted = nearbyTrusted.get(truster);
        if (trusted == null || trusted.isEmpty()) {
            return false;
        }
        return trusted.contains(trustee);
    }

    public void trustNearby(UUID truster, UUID trustee) {
        nearbyTrusted.putIfAbsent(truster, new HashSet<>());
        nearbyTrusted.get(truster).add(trustee);
    }

    public void untrustNearby(UUID truster, UUID trustee) {
        Set<UUID> trusted = nearbyTrusted.get(truster);
        if (trusted != null) {
            trusted.remove(trustee);
        }
    }

    public void loadData() {
        // todo
    }

    public void saveData() {
        // todo
    }
}
