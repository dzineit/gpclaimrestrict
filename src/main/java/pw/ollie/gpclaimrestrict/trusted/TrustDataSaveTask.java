package pw.ollie.gpclaimrestrict.trusted;

import pw.ollie.gpclaimrestrict.GPCRPlugin;

import org.bukkit.scheduler.BukkitRunnable;

public final class TrustDataSaveTask extends BukkitRunnable {
    private final GPCRPlugin plugin;

    public TrustDataSaveTask(GPCRPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getTrustManager().saveData();
    }
}
