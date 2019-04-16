package pw.ollie.gpclaimrestrict;

import pw.ollie.gpclaimrestrict.command.TrustnearCommand;
import pw.ollie.gpclaimrestrict.command.UntrustnearCommand;
import pw.ollie.gpclaimrestrict.listener.ClaimCreateListener;
import pw.ollie.gpclaimrestrict.trust.TrustDataSaveTask;
import pw.ollie.gpclaimrestrict.trust.TrustManager;

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

        this.getCommand("trustnear").setExecutor(new TrustnearCommand(this));
        this.getCommand("untrustnear").setExecutor(new UntrustnearCommand(this));
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
