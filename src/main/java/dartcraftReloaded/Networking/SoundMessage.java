package dartcraftReloaded.Networking;

import dartcraftReloaded.Handlers.DCRSoundHandler;
import dartcraftReloaded.container.ContainerBlockInfuser;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SoundMessage extends MessageBase<SoundMessage> {

    public SoundMessage(){}

    private BlockPos pos;
    private int id;

    public SoundMessage(BlockPos pos, int id) {
        this.pos = pos;
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(id);
    }

    @Override
    public void handleClientSide(SoundMessage message, EntityPlayer player) {
        if (player.getPosition().distanceSq(message.pos.getX(), message.pos.getY(), message.pos.getZ()) <= 20) {
            switch (message.id) {
                case 0:
                    player.world.playSound(player, player.getPosition(), DCRSoundHandler.SPARKLE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                case 1:
                    break;
            }
        }
    }

    @Override
    public void handleServerSide(SoundMessage message, EntityPlayerMP player) {

    }


}
