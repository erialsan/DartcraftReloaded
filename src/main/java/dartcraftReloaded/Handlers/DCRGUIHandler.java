package dartcraftReloaded.Handlers;

import dartcraftReloaded.client.gui.belt.GUIForceBelt;
import dartcraftReloaded.client.gui.fortune.GUIFortune;
import dartcraftReloaded.client.gui.furnace.GUIFurnace;
import dartcraftReloaded.client.gui.infuser.GUIInfuser;
import dartcraftReloaded.client.gui.pack.GUIForcePack;
import dartcraftReloaded.container.ContainerBlockFurnace;
import dartcraftReloaded.container.ContainerBlockInfuser;
import dartcraftReloaded.container.ContainerItemForceBelt;
import dartcraftReloaded.container.ContainerItemForcePack;
import dartcraftReloaded.tileEntity.TileEntityForceFurnace;
import dartcraftReloaded.tileEntity.TileEntityInfuser;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import static dartcraftReloaded.Items.ItemFortune.addMessage;

/**
 * Created by BURN447 on 3/31/2018.
 */
public class DCRGUIHandler implements IGuiHandler {

    public static final int INFUSER = 0;
    public static final int FURNACE = 1;
    public static final int PACK = 2;
    public static final int BELT = 3;
    public static final int FORTUNE = 4;


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == INFUSER){
            return new GUIInfuser(player.inventory, (TileEntityInfuser)world.getTileEntity(new BlockPos(x, y, z)));
        }
        else if(ID == FURNACE) {
            return new GUIFurnace(player.inventory, (TileEntityForceFurnace)world.getTileEntity(new BlockPos(x, y, z)));
        }
        else if (ID == PACK) {
            return new GUIForcePack(player.inventory, player.getHeldItemMainhand());
        }
        else if(ID == BELT) {
            return new GUIForceBelt(player.inventory, player.getHeldItemMainhand());
        }
        else if (ID == FORTUNE) {
            NBTTagCompound nbt;
            ItemStack stack = player.getHeldItemMainhand();
            if(stack.hasTagCompound()) {
                nbt = stack.getTagCompound();
            }
            else {
                nbt = new NBTTagCompound();
            }

            if(!nbt.hasKey("message")) {
                addMessage(stack, nbt);
            }


            return new GUIFortune(I18n.format(nbt.getString("message")));
        }
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == INFUSER) {
            return new ContainerBlockInfuser(player.inventory, (TileEntityInfuser) world.getTileEntity(new BlockPos(x, y, z)));
        } else if (ID == FURNACE) {
            return new ContainerBlockFurnace(player.inventory, (TileEntityForceFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        } else if (ID == PACK) {
            return new ContainerItemForcePack(player.inventory, player.getHeldItemMainhand());
        } else if (ID == BELT) {
            return new ContainerItemForceBelt(player.inventory, player.getHeldItemMainhand());
        }
        return null;
    }
}
