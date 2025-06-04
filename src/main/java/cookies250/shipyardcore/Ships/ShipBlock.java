package cookies250.shipyardcore.Ships;

import io.papermc.paper.entity.TeleportFlag;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Shulker;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;
import org.joml.Matrix4f;

import static cookies250.shipyardcore.ShipyardCore.print;

public class ShipBlock {

    private final Ship parent;

    private final BlockVector relativeLocation;

    private BlockDisplay blockDisplay;
    private Shulker shulker;

    public ShipBlock(Ship parent, Block block, BlockVector relativeLocation) {
        this.parent = parent;
        Shulker shulker = spawnShulker(block.getLocation());
        this.shulker = shulker;
        this.blockDisplay = spawnBlockDisplay(block, shulker);
        this.relativeLocation = relativeLocation;
    }

    public void destroy() {
        shulker.remove();
        blockDisplay.remove();
    }

    public void teleport(Vector vector) {
        print("Teleporting ship block to X: " + vector.getX() + " Y: " + vector.getY() + " Z: " + vector.getZ());
        print(parent.getWorld().getName());
        boolean a = blockDisplay.teleport(new Location(parent.getWorld(), vector.getX(), vector.getY(), vector.getZ()), TeleportFlag.EntityState.RETAIN_PASSENGERS);
        print("" + a);
    }

    public BlockVector getRelativeBlockLocation() {
        return relativeLocation;
    }

    public Vector getRelativeVectorLocation() {
        return new Vector(relativeLocation.getX(), relativeLocation.getY(), relativeLocation.getZ());
    }

    private Shulker spawnShulker(Location location) {
        Shulker shulker = location.getWorld().spawn(location, Shulker.class);
        shulker.setAI(false);
        shulker.setSilent(true);
        shulker.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                -1,
                1,
                true,
                false)
        );
        shulker.addScoreboardTag("ship_shulkers");
        shulker.addScoreboardTag("kill_all");
        shulker.setInvulnerable(true);
        shulker.clearLootTable();
        return shulker;
    }


    private BlockDisplay spawnBlockDisplay(Block block, Shulker shulker) {
        BlockDisplay blockDisplay = block.getWorld().spawn(block.getLocation(), BlockDisplay.class);
        blockDisplay.addPassenger(shulker);
        blockDisplay.setBlock(block.getBlockData().clone());
        blockDisplay.addScoreboardTag("kill_all");
        blockDisplay.addScoreboardTag("blocks");
        blockDisplay.setTransformationMatrix(new Matrix4f().translate(-0.5f, 0.0f, -0.5f));
        Location blockLocation = blockDisplay.getLocation();
        boolean a = blockDisplay.teleport(new Location(block.getWorld(), blockLocation.getX() + 0.5, blockLocation.getY(), blockLocation.getZ() + 0.5), TeleportFlag.EntityState.RETAIN_PASSENGERS);
        print("" + a);
        return blockDisplay;
    }

}