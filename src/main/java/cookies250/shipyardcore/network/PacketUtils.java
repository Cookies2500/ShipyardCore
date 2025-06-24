package cookies250.shipyardcore.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

import java.util.List;

public class PacketUtils {
    public static void addVelocityToPlayer(Player bukkitPlayer, Vector velocity) {

        ServerPlayer nmsPlayer = ((CraftPlayer) bukkitPlayer).getHandle();

        double x = bukkitPlayer.getLocation().getX();
        double y = bukkitPlayer.getLocation().getY();
        double z = bukkitPlayer.getLocation().getZ();
        float radius = 4.0F;

        List<BlockPos> affectedBlocks = List.of();

        Vec3 knockback = new Vec3(velocity.getX(), velocity.getY(), velocity.getZ());

        Explosion.BlockInteraction destructionType = Explosion.BlockInteraction.DESTROY;

        ParticleOptions invisibleParticle = new DustParticleOptions(
                new Vector3f(1.0f, 0.0f, 0.0f),0.0f
        );

        Holder<SoundEvent> soundHolder = BuiltInRegistries.SOUND_EVENT.getHolder(BuiltInRegistries.SOUND_EVENT.getResourceKey(SoundEvents.EMPTY).orElseThrow()).orElseThrow();

        ClientboundExplodePacket packet = new ClientboundExplodePacket(
                x, y, z,
                radius,
                affectedBlocks,
                knockback,
                destructionType,
                invisibleParticle,
                invisibleParticle,
                soundHolder
        );

        nmsPlayer.connection.send(packet);
    }
}
