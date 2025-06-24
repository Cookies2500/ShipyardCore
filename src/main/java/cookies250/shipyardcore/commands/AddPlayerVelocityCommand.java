package cookies250.shipyardcore.commands;

import cookies250.shipyardcore.network.PacketUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddPlayerVelocityCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!player.getName().equals(args[0])) continue;
            PacketUtils.addVelocityToPlayer(player, new Vector(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3])));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> names = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            names.add(player.getName());
        }
        return names;
    }
}
