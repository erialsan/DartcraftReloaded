package dartcraftReloaded.client.gui;

import dartcraftReloaded.Constants;
import dartcraftReloaded.container.ContainerItemForcePack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GUIForcePack extends GuiContainer {
    private ResourceLocation TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/container/forcepack1.png");
    public GUIForcePack(ContainerItemForcePack container) {
        super(container);
        if (container.slotCount == 8) {
            TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/container/forcepack1.png");
            this.ySize = 136;
        } else if (container.slotCount == 16) {
            TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/container/forcepack2.png");
            this.ySize = 154;
        } else if (container.slotCount == 24) {
            TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/container/forcepack3.png");
            this.ySize = 172;
        } else if (container.slotCount == 32) {
            TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/container/forcepack4.png");
            this.ySize = 190;
        } else if (container.slotCount == 40) {
            TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/container/forcepack5.png");
            this.ySize = 208;
        }


        this.xSize = 176;
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

}
