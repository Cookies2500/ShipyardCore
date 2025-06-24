package cookies250.shipyardcore.commands;

import cookies250.shipyardcore.ships.Ship;
import cookies250.shipyardcore.ships.ShipManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static cookies250.shipyardcore.ShipyardCore.print;

public class SetShipVelocityCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String shipName = args[0];
        for (Ship ship : ShipManager.getShips()) {
            if (!ship.getShipName().equals(shipName)) continue;
            Vector velocity = new Vector(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
            print("Applying velocity to ship ship dVX: " + velocity.getX() + " dVY: " + velocity.getY() + " dVZ: " + velocity.getZ());
            ship.applyVelocity(velocity);
            return true;
        }
        print("complaining");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return ShipManager.getShipNames();
    }
}
