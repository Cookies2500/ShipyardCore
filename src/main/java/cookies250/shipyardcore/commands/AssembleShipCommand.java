package cookies250.shipyardcore.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.util.formatting.text.TextComponent;
import cookies250.shipyardcore.ShipyardCore;
import cookies250.shipyardcore.ships.Ship;
import cookies250.shipyardcore.ships.ShipManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AssembleShipCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        WorldEdit worldEdit = WorldEdit.getInstance();
        SessionManager manger = worldEdit.getSessionManager();

        if (!(sender instanceof Player player)) {
            ShipyardCore.print("Cannot run from console");
            return false;
        }

        com.sk89q.worldedit.entity.Player worldeditPlayer = BukkitAdapter.adapt(player);

        LocalSession session = manger.get(worldeditPlayer);
        com.sk89q.worldedit.world.World worldEditWorld = session.getSelectionWorld();

        Region region;

        try {
            if (worldEditWorld == null) throw new IncompleteRegionException();
            region = session.getSelection(worldEditWorld);
        } catch (IncompleteRegionException ex) {
            worldeditPlayer.printError(TextComponent.of("Please make a region selection first."));
            return true;
        }

        worldeditPlayer.printInfo(TextComponent.of("Width: " + region.getWidth() + " Height: " + region.getHeight() + " Length: " + region.getLength()));

        if (ShipManager.getShipNames().contains(args[0])) {
            Bukkit.getServer().broadcastMessage("Cannot create ship, name already exists");
            return true;
        }

        new Ship(player.getWorld(), region, args[0], player);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
