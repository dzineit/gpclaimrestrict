package pw.ollie.gpclaimrestrict.command;

import pw.ollie.gpclaimrestrict.GPCRPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class UntrustnearCommand implements CommandExecutor {
    private final GPCRPlugin plugin;

    public UntrustnearCommand(GPCRPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do that.");
            return true;
        }

        return true;
    }
}
