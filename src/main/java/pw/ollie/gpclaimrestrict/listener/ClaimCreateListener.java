package pw.ollie.gpclaimrestrict.listener;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.events.ClaimCreatedEvent;

import pw.ollie.gpclaimrestrict.GPCRPlugin;
import pw.ollie.gpclaimrestrict.trust.TrustManager;
import pw.ollie.gpclaimrestrict.util.Cuboid;
import pw.ollie.gpclaimrestrict.util.MathUtil;
import pw.ollie.gpclaimrestrict.util.Vector3i;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public final class ClaimCreateListener implements Listener {
    private final GPCRPlugin plugin;

    public ClaimCreateListener(GPCRPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void restrictClaimNearOther(ClaimCreatedEvent event) {
        if (!plugin.getConfiguration().isRestrictNearClaims()) {
            return;
        }
        CommandSender creator = event.getCreator();
        if (!(creator instanceof Player) || creator.hasPermission("gpcr.bypass")) {
            return;
        }

        Claim claim = event.getClaim();
        int restrictedRadius = plugin.getConfiguration().getNearbyRestrictionRadius();
        Vector3i restrictionSize = new Vector3i(restrictedRadius, restrictedRadius, restrictedRadius);

        Location claimMin = claim.getLesserBoundaryCorner();
        Vector3i restrictedMin = MathUtil.add(claimMin, restrictionSize.negate());
        Location claimMax = claim.getGreaterBoundaryCorner();
        Vector3i restrictedMax = MathUtil.add(claimMax, restrictionSize);

        // this is the restriction area around the to-be-created new claim
        Cuboid restrictionArea = new Cuboid(restrictedMin.toLocation(claimMin.getWorld()), restrictedMax.subtract(restrictedMin));

        // now we must check whether any existing claims fall into this area - in which case we cancel the event
        Collection<Claim> claims = GriefPrevention.instance.dataStore.getClaims();
        TrustManager trustManager = plugin.getTrustManager();
        UUID claimerId = ((Player) creator).getUniqueId();

        for (Claim existingClaim : claims) {
            Location vertex1 = existingClaim.getGreaterBoundaryCorner();
            if (!Objects.equals(vertex1.getWorld(), claimMin.getWorld())) {
                continue;
            }
            if (Objects.equals(claimerId, existingClaim.ownerID) || trustManager.isTrustedNearby(existingClaim.ownerID, claimerId)) {
                continue;
            }

            Location vertex2 = existingClaim.getLesserBoundaryCorner();

            int xSize = vertex1.getBlockX() - vertex2.getBlockX();
            int ySize = vertex1.getBlockY() - vertex2.getBlockY();
            int zSize = vertex1.getBlockZ() - vertex2.getBlockZ();

            Location vertex3 = vertex1.add(xSize, 0, 0);
            Location vertex4 = vertex1.add(xSize, 0, zSize);
            Location vertex5 = vertex1.add(xSize, ySize, 0);
            Location vertex6 = vertex1.add(0, ySize, 0);
            Location vertex7 = vertex1.add(0, ySize, zSize);
            Location vertex8 = vertex1.add(0, 0, zSize);

            if (MathUtil.containsAny(restrictionArea, vertex1, vertex2, vertex3, vertex4, vertex5, vertex6, vertex7, vertex8)) {
                creator.sendMessage(ChatColor.RED + "You are too close to somebody else's claim!");
                creator.sendMessage(ChatColor.RED + "If you know the person, you can ask them to trust you to build nearby with /trustnear.");
                event.setCancelled(true);
                return;
            }
        }
    }
}
