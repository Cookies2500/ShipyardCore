package cookies250.shipyardcore.World;

import org.bukkit.*;

import static cookies250.shipyardcore.ShipyardCore.print;

public class ShipyardWorld {

    public static void initialize() {
        WorldCreator worldCreator = new WorldCreator("shipyard");
        worldCreator.type(WorldType.FLAT);
        worldCreator.generatorSettings(" {\"layers\": [{\"block\": \"air\", \"height\": 1}], \"biome\":\"void\"}");
        worldCreator.generateStructures(false);
        World world = worldCreator.createWorld();
        print("[WorldLoad] Loaded world " + world.getName());
    }

    public static Location getTransform(int shipId, int x, int y, int z) {
        return new Location(ShipyardWorld.getWorld(), x + shipId * 10, y, z);
    }

    public static World getWorld() {
        return Bukkit.getServer().getWorld("shipyard");
    }
}
