package dartcraftReloaded.entity;

import dartcraftReloaded.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBottle extends RenderSnowball<EntityBottle> {
    public RenderBottle(RenderManager renderManagerIn) {
        super(renderManagerIn, ModItems.filledJar, Minecraft.getMinecraft().getRenderItem());
    }
    public ItemStack getStackToRender(EntityBottle entityIn) {
        return entityIn.getBottle();
    }
}