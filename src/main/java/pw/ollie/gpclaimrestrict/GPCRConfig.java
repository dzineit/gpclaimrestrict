package pw.ollie.gpclaimrestrict;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public final class GPCRConfig {
    private final GPCRPlugin plugin;

    private boolean restrictNearClaims = true;
    private int nearbyRestrictionRadius = 10;

    public GPCRConfig(GPCRPlugin plugin) {
        this.plugin = plugin;
    }

    public GPCRPlugin getPlugin() {
        return plugin;
    }

    public boolean isRestrictNearClaims() {
        return restrictNearClaims;
    }

    public int getNearbyRestrictionRadius() {
        return nearbyRestrictionRadius;
    }

    public void loadConfig() {
        plugin.saveResource("config.yml", false);

        File configFile = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        // restriction nearby claims settings
        restrictNearClaims = config.getBoolean("Nearby-Claim-Restriction.Enabled", restrictNearClaims);
        nearbyRestrictionRadius = config.getInt("Nearby-Claim-Restriction.Radius", nearbyRestrictionRadius);
    }
}
