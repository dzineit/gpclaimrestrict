package pw.ollie.gpclaimrestrict;

import pw.ollie.gpclaimrestrict.listener.ClaimCreateListener;
import pw.ollie.gpclaimrestrict.trusted.TrustDataSaveTask;
import pw.ollie.gpclaimrestrict.trusted.TrustManager;

import org.bukkit.plugin.java.JavaPlugin;

public final class GPCRPlugin extends JavaPlugin {
    private GPCRConfig config;
    private TrustManager trustManager;
    private TrustDataSaveTask saveTask;

    @Override
    public void onEnable() {
        this.config = new GPCRConfig(this);
        this.config.loadConfig();

        this.trustManager = new TrustManager(this);
        this.trustManager.loadData();

        this.saveTask = new TrustDataSaveTask(this);
        this.saveTask.runTaskTimerAsynchronously(this, 20L * 60 * 10, 20L * 60 * 10);

        this.getServer().getPluginManager().registerEvents(new ClaimCreateListener(this), this);
    }

    @Override
    public void onDisable() {
        this.saveTask.cancel();
        this.trustManager.saveData();
    }

    public GPCRConfig getConfiguration() {
        return config;
    }

    public TrustManager getTrustManager() {
        return trustManager;
    }
}
