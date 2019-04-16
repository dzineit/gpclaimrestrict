package pw.ollie.gpclaimrestrict.command;

import pw.ollie.gpclaimrestrict.GPCRPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TrustnearCommand implements CommandExecutor {
    private final GPCRPlugin plugin;

    public TrustnearCommand(GPCRPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do that.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Please specify the name of the player to trust.");
            return true;
        }

        String playerName = args[0];
        Player player = plugin.getServer().getPlayer(playerName);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "That player is not online.");
            return true;
        }

        Player truster = (Player) sender;
        plugin.getTrustManager().trustNearby(truster.getUniqueId(), player.getUniqueId());
        sender.sendMessage(ChatColor.GRAY + player.getName() + " can now build near your claims.");
        return true;
    }
}
