package dartcraftReloaded.networking;

import dartcraftReloaded.handlers.SoundHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class SoundMessage extends MessageBase<SoundMessage> {

    public SoundMessage(){}

    private int id;

    public SoundMessage(int id) {
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
    }

    @Override
    public void handleClientSide(SoundMessage message, EntityPlayer player) {
        switch (message.id) {
            case 0:
                Minecraft.getMinecraft().player.playSound(SoundHandler.SPARKLE, 1.0f, 1.0f);
            case 1:
                break;
        }
    }

    @Override
    public void handleServerSide(SoundMessage message, EntityPlayerMP player) {

    }


}
