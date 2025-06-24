package cookies250.shipyardcore.ships;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import cookies250.shipyardcore.ShipyardCore;
import cookies250.shipyardcore.network.PacketUtils;
import cookies250.shipyardcore.world.ShipyardWorld;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cookies250.shipyardcore.ShipyardCore.print;

public class Ship {

    private String shipName;

    private final int shipID;

    private final UUID ownerUUID;

    private Location shipLocation;
    private Vector shipVelocity;

    private BoundingBox boundingBox;

    private List<ShipBlock> shipBlocks = new ArrayList<>();

    private int sillynum = 0;


    public Ship(World world, Region region, String shipName, Player owner) {

        ShipManager.addShip(this);

        this.shipName = shipName;
        shipID = ShipManager.getNextShipID();
        ownerUUID = owner.getUniqueId();

        BlockVector3 minPoint = region.getMinimumPoint();
        BlockVector3 maxPoint = region.getMaximumPoint();

        double x = minPoint.x() + (double) region.getWidth() / 2;
        double y = minPoint.y();
        double z = minPoint.z() + (double) region.getLength() / 2;

        boundingBox = new BoundingBox(
                minPoint.x() + 0.201,
                minPoint.y(),
                minPoint.z() + 0.201,
                maxPoint.x() + 1.799,
                maxPoint.y() + 3,
                maxPoint.z() + 1.799
        );

        this.shipLocation = new Location(world, x - 0.5, y, z - 0.5);
        this.shipVelocity = new Vector(0, 0, 0);

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
        Bukkit.getServer().broadcastMessage(owner.getName() + " spawned a new ship named " + shipName + " (id " + shipID + ") at location X: " + shipLocation.x() + " Y: " + shipLocation.y() + " Z: " + shipLocation.z());
    }


    public void onTick() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!boundingBox.contains(player.getLocation().toVector())) continue;

            double dX = shipVelocity.getX() * (player.isOnGround() ? 0.453 : 0.12);
            double dY = shipVelocity.getY() * (shipVelocity.getY() > 0 ? 0.9 : 0.453);
            double dZ = shipVelocity.getZ() * (player.isOnGround() ? 0.453 : 0.12);

            if (shipVelocity.getY() > 0) {
                dY += 0.083;
            }

            PacketUtils.addVelocityToPlayer(player, new Vector(dX, dY, dZ));
        }
        move(shipVelocity);
    }


    public void addBlock(Block block, BlockVector relativeLocation) {
        shipBlocks.add(new ShipBlock(this, block, relativeLocation));
    }


    public void move(Vector movement) {

        shipLocation.add(movement);
        boundingBox.shift(movement);

        for (ShipBlock shipBlock : shipBlocks) {
            shipBlock.teleport(shipBlock.getRelativeVectorLocation().clone().add(shipLocation.toVector()));
        }
    }


    public void applyVelocity(Vector velocity) {
        shipVelocity.add(velocity);
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
