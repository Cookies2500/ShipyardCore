package cookies250.shipyardcore.commands;

import cookies250.shipyardcore.ships.Ship;
import cookies250.shipyardcore.ships.ShipManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DestroyShipCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage("Please enter a ship name");
            return true;
        }

        Ship ship = ShipManager.getShipFromName(args[0]);

        if (ship == null) {
            sender.sendMessage("No ship found with that name");
            return true;
        }
        sender.sendMessage("Destroyed " + args[0]);
        ship.destroy();
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return ShipManager.getShipNames();
    }
}
