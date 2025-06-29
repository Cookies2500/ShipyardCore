package cookies250.shipyardcore;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import cookies250.shipyardcore.commands.*;
import cookies250.shipyardcore.events.Events;
import cookies250.shipyardcore.world.ShipyardWorld;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

// TODO Drag player along with the ship - Use velocity - https://docs.papermc.io/paper/dev/entity-teleport/#:~:text=the%20TeleportFlag%20class.-,Relative%20teleportation,Section%20titled%20“Relative%20teleportation”,-Teleport%20a%20player

// TODO Set up shipyard
    // TODO Block update processing
    // TODO Block entity interaction``

// TODO Physics! - Velocity, Interpolation
    // TODO Rotation / Rotational velocity / Moments


public final class ShipyardCore extends JavaPlugin {

    public static ShipyardCore shipyardPlugin;

    private static ProtocolManager protocolManager;

    @Override
    public void onEnable() {

        ShipyardCore.shipyardPlugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        getServer().getPluginManager().registerEvents(new Events(), this);

        registerTabCompleter("assembleship", new AssembleShipCommand());
        registerTabCompleter("destroyship", new DestroyShipCommand());
        registerTabCompleter("listships", new ListShipsCommand());
        registerTabCompleter("moveship", new MoveShipCommand());
        registerTabCompleter("setshipvelocity", new SetShipVelocityCommand());
        registerTabCompleter("addplayervelocity", new AddPlayerVelocityCommand());

        ShipyardWorld.initialize();

        print("Hello world");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static ProtocolManager getProtocolManager() {
        return protocolManager;
    }


    public static void print(String msg) {
        shipyardPlugin.getLogger().info(msg);
        Bukkit.getServer().broadcastMessage(msg);
    }


    private <T extends CommandExecutor> void registerTabCompleter(String commandName, T classRef) {
        this.getCommand(commandName).setExecutor(classRef);
        this.getCommand(commandName).setTabCompleter((TabCompleter) classRef);
    }
}
