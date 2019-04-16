package pw.ollie.gpclaimrestrict.command;

import pw.ollie.gpclaimrestrict.GPCRPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class TrustnearCommand implements CommandExecutor {
    private final GPCRPlugin plugin;

    public TrustnearCommand(GPCRPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return true;
    }
}
