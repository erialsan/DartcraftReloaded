package dartcraftReloaded.networking;

import dartcraftReloaded.tileEntity.TileEntityInfuser;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class InfuserMessage extends MessageBase<InfuserMessage> {

    private BlockPos pos;

    public InfuserMessage(){}

    public InfuserMessage(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
    }

    @Override
    public void handleClientSide(InfuserMessage message, EntityPlayer player) {

    }

    @Override
    public void handleServerSide(InfuserMessage message, EntityPlayerMP player) {
        TileEntity te = player.getServerWorld().getTileEntity(message.pos);
        if (te instanceof TileEntityInfuser) {
            ((TileEntityInfuser) te).onButton();
        }
    }

}
