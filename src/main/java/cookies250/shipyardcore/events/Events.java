package cookies250.shipyardcore.events;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import cookies250.shipyardcore.ships.Ship;
import cookies250.shipyardcore.ships.ShipManager;
import io.papermc.paper.event.player.PlayerFailMoveEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static cookies250.shipyardcore.ShipyardCore.print;

public class Events implements Listener {

    @EventHandler
    public void onTick(ServerTickStartEvent event) {
        for (Ship ship : ShipManager.getShips()) {
            ship.onTick();
        }
    }


    @EventHandler
    public void entityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity.getScoreboardTags().contains("ship_shulkers")) {
            event.getDrops().clear();
        }
    }


    @EventHandler
    public void playerFailMove(PlayerFailMoveEvent event) {
        print(event.getPlayer().getName() + String.valueOf(event.getFailReason()));
        event.setAllowed(true);
    }
}
