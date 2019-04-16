package pw.ollie.gpclaimrestrict.trust;

import pw.ollie.gpclaimrestrict.GPCRPlugin;

import org.bson.BSONDecoder;
import org.bson.BSONEncoder;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;
import org.bson.BasicBSONEncoder;
import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

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

    public boolean untrustNearby(UUID truster, UUID trustee) {
        Set<UUID> trusted = nearbyTrusted.get(truster);
        if (trusted != null) {
            return trusted.remove(trustee);
        }
        return false;
    }

    public void loadData() {
        File storageFolder = new File(plugin.getDataFolder(), "data");
        File trustedFile = new File(storageFolder, "trusted.bson");
        if (!trustedFile.exists()) {
            return;
        }

        try {
            byte[] bytes = Files.readAllBytes(trustedFile.toPath());
            BSONDecoder decoder = new BasicBSONDecoder();
            BSONObject bObj = decoder.readObject(bytes);

            if (!(bObj instanceof BasicBSONObject)) {
                plugin.getLogger().log(Level.SEVERE, "Invalid data stored... Cannot load trusted data!");
                return;
            }

            BasicBSONObject bson = (BasicBSONObject) bObj;
            for (String key : bson.keySet()) {
                UUID trusterId = UUID.fromString(key);
                BasicBSONList list = (BasicBSONList) bson.get(key);
                Set<UUID> trusted = new HashSet<>();
                for (Object value : list) {
                    trusted.add(UUID.fromString(value.toString()));
                }
                nearbyTrusted.put(trusterId, trusted);
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not load trusted player data...", e);
        }
    }

    public void saveData() {
        File storageFolder = new File(plugin.getDataFolder(), "data");
        File trustedFile = new File(storageFolder, "trusted.bson");
        File backupFile = new File(storageFolder, "trusted.bson.bck");

        if (trustedFile.exists()) {
            try {
                Files.copy(trustedFile.toPath(), backupFile.toPath());
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not backup trusted player data, not saving...", e);
                return;
            }

            trustedFile.delete();
        }

        if (!nearbyTrusted.isEmpty()) {
            BasicBSONObject storageObj = new BasicBSONObject();
            for (Map.Entry<UUID, Set<UUID>> entry : nearbyTrusted.entrySet()) {
                Set<UUID> trustedSet = entry.getValue();
                if (trustedSet == null || trustedSet.isEmpty()) {
                    continue;
                }

                BasicBSONList bsonList = new BasicBSONList();
                for (UUID uuid : trustedSet) {
                    bsonList.add(uuid.toString());
                }
                storageObj.put(entry.getKey().toString(), bsonList);
            }

            if (storageObj.isEmpty()) {
                return;
            }

            BSONEncoder encoder = new BasicBSONEncoder();
            byte[] data = encoder.encode(storageObj);

            try {
                trustedFile.createNewFile();
                Files.write(trustedFile.toPath(), data);
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not backup trusted player data, attempting to restore backup...", e);

                try {
                    Files.copy(backupFile.toPath(), trustedFile.toPath());
                } catch (IOException e1) {
                    plugin.getLogger().log(Level.SEVERE, "Could not restore backup, restore manually...", e1);
                    return;
                }
            }
        }
    }
}
