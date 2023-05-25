package dartcraftReloaded.client.gui.fortune;

import dartcraftReloaded.Constants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GUIFortune extends GuiScreen {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/fortune.png");

    private int guiLeft;
    private int guiTop;
    private final String text;
    private final int xSize = 235;
    private final int ySize = 73;

    public GUIFortune(String text) {
        this.text = text;
    }

    private void init() {
        this.guiLeft = (this.width - xSize) / 2;
        this.guiTop = (this.height - ySize) / 2;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        init();
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        fontRenderer.drawSplitString(text, guiLeft+16, guiTop+16, 200, 0xFF000000);
    }

    @Override
    public void drawDefaultBackground() {
        super.drawDefaultBackground();
    }
}
