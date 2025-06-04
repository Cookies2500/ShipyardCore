package cookies250.shipyardcore.Ships;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import cookies250.shipyardcore.World.ShipyardWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ship {

    private String shipName;

    private final int shipID;

    private final UUID ownerUUID;

    private Location shipLocation;

    private BoundingBox boundingBox;

    private List<ShipBlock> shipBlocks = new ArrayList<>();


    public Ship(World world, Region region, String shipName, Player owner) {

        ShipManager.addShip(this);

        this.shipName = shipName;
        shipID = ShipManager.getNextShipID();
        ownerUUID = owner.getUniqueId();

        double x = region.getMinimumPoint().x() + (double) region.getWidth() / 2;
        double y = region.getMinimumPoint().y();
        double z = region.getMinimumPoint().z() + (double) region.getLength() / 2;

        this.shipLocation = new Location(world, x, y, z);

        for (BlockVector3 blockPos : region) {
            Block block = world.getBlockAt(blockPos.x(), blockPos.y(), blockPos.z());

            if (block.getType() == Material.AIR) {
                continue;
            }

            int relativeX = blockPos.x() - shipLocation.getBlockX();
            int relativeY = blockPos.y() - shipLocation.getBlockY();
            int relativeZ = blockPos.z() - shipLocation.getBlockZ();

            BlockVector relativeLocation = new BlockVector(relativeX, relativeY, relativeZ);

            addBlock(block, relativeLocation);

            ShipyardWorld.getWorld().setBlockData(ShipyardWorld.getTransform(shipID, relativeX, relativeY, relativeZ), block.getBlockData());

            block.setType(Material.AIR);
        }
        Bukkit.getServer().broadcastMessage(owner.getName() + " spawned a new boat named " + shipName + " (id " + shipID + ") at location X: " + shipLocation.x() + " Y: " + shipLocation.y() + " Z: " + shipLocation.z());
    }


    public void addBlock(Block block, BlockVector relativeLocation) {
        shipBlocks.add(new ShipBlock(this, block, relativeLocation));
    }

    public void move(Vector movement) {

        shipLocation.add(movement);

        for (ShipBlock shipBlock : shipBlocks) {
            shipBlock.teleport(shipBlock.getRelativeVectorLocation().clone().add(shipLocation.toVector()));
        }
    }

    public void destroy() {

        for (ShipBlock shipBlock : shipBlocks) {
            shipBlock.destroy();
        }

        ShipManager.removeShip(this);
    }


    public void removeBlock() {

    }

    public String getShipName() {
        return shipName;
    }

    public int getShipID() {
        return shipID;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public World getWorld() {
        return shipLocation.getWorld();
    }

}
