package cookies250.shipyardcore.Events;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class Events implements Listener {
    @EventHandler
    public void entityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity.getScoreboardTags().contains("ship_shulkers")) {
            event.getDrops().clear();
        }
    }
}
