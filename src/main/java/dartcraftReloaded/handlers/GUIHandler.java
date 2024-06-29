package dartcraftReloaded.handlers;

import dartcraftReloaded.client.gui.GUIBook;
import dartcraftReloaded.client.gui.GUIFortune;
import dartcraftReloaded.client.gui.GUIFurnace;
import dartcraftReloaded.client.gui.GUIInfuser;
import dartcraftReloaded.container.ContainerBlockFurnace;
import dartcraftReloaded.container.ContainerBlockInfuser;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.tileEntity.TileEntityForceFurnace;
import dartcraftReloaded.tileEntity.TileEntityInfuser;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import static dartcraftReloaded.items.ItemFortune.addMessage;

public class GUIHandler implements IGuiHandler {

    public static final int INFUSER = 0;
    public static final int FURNACE = 1;
    public static final int BOOK = 2;
    public static final int FORTUNE = 3;


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == INFUSER){
            return new GUIInfuser(player.inventory, (TileEntityInfuser)world.getTileEntity(new BlockPos(x, y, z)));
        }
        else if(ID == FURNACE) {
            return new GUIFurnace(player.inventory, (TileEntityForceFurnace)world.getTileEntity(new BlockPos(x, y, z)));
        }
        else if (ID == BOOK) {
            if (player.getHeldItemMainhand().getItem() == ModItems.upgradeTome) {
                return new GUIBook(player.getHeldItemMainhand());
            } else if (player.getHeldItemOffhand().getItem() == ModItems.upgradeTome) {
                return new GUIBook(player.getHeldItemOffhand());
            }
        }
        else if (ID == FORTUNE) {
            NBTTagCompound nbt;
            ItemStack stack = player.getHeldItemMainhand();
            if (stack.hasTagCompound()) {
                nbt = stack.getTagCompound();
            } else {
                nbt = new NBTTagCompound();
            }

            if (!nbt.hasKey("message")) {
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
        }
        return null;
    }
}
