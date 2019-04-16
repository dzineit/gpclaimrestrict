package pw.ollie.gpclaimrestrict;

import pw.ollie.gpclaimrestrict.listener.ClaimCreateListener;

import org.bukkit.plugin.java.JavaPlugin;

public final class GPCRPlugin extends JavaPlugin {
    private GPCRConfig config;

    @Override
    public void onEnable() {
        this.config = new GPCRConfig(this);
        this.config.loadConfig();

        this.getServer().getPluginManager().registerEvents(new ClaimCreateListener(this), this);
    }

    public GPCRConfig getConfiguration() {
        return config;
    }
}
