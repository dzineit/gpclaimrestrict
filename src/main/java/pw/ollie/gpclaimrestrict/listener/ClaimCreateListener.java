package pw.ollie.gpclaimrestrict.listener;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.events.ClaimCreatedEvent;

import pw.ollie.gpclaimrestrict.GPCRPlugin;
import pw.ollie.gpclaimrestrict.util.Cuboid;
import pw.ollie.gpclaimrestrict.util.MathUtil;
import pw.ollie.gpclaimrestrict.util.Vector3i;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

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
        if (creator.hasPermission("gpcr.bypass")) {
            return;
        }

        // todo add nearby trusted list

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
        // todo
    }
}
