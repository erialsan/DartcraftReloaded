package dartcraftReloaded.client.gui;


import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.Modifier;
import dartcraftReloaded.capablilities.UpgradeTome.IUpgradeTome;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.util.DartUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class GUIBook extends GuiScreen {
    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(Constants.modId,"textures/gui/book.png");
    private final ItemStack book;
    /** Update ticks since the gui was opened */
    private int bookTotalPages = 99;
    private int currPage;
    private NextPageButton buttonNextPage;
    private NextPageButton buttonPreviousPage;

    private ArrayList<Modifier> toDisplay;
    public GUIBook(ItemStack book)
    {
        this.book = book;

    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    @Override
    public void initGui()
    {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);


        int i = (this.width - 192) / 2;
        this.buttonNextPage = this.addButton(new NextPageButton(1, i + 120, 167, true));
        this.buttonPreviousPage = this.addButton(new NextPageButton(2, i + 38, 167, false));
        IUpgradeTome cap = book.getCapability(CapabilityHandler.CAPABILITY_UPGRADETOME, null);
        int tier = 1;
        if (cap != null) {
            tier = cap.getLevel();
        }
        this.toDisplay = DartUtils.getModifiersForTier(tier);
        this.bookTotalPages = toDisplay.size();
    }


    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
            if (button.id == 1)
            {
                if (this.currPage < this.bookTotalPages - 1)
                {
                    ++this.currPage;
                }
            }
            else if (button.id == 2)
            {
                if (this.currPage > 0)
                {
                    --this.currPage;
                }
            }
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
        int i = (this.width - 192) / 2;
        this.drawTexturedModalRect(i, 13, 0, 0, 192, 192);
        Modifier mod = toDisplay.get(currPage);
        this.fontRenderer.drawString(mod.getColor()+mod.getName(), i+36, 30, mod.getColor().getColorIndex());
        this.fontRenderer.drawString(mod.getColor()+"Max Levels: "+mod.getMaxLevels(), i+36, 40, mod.getColor().getColorIndex());
        this.fontRenderer.drawString(mod.getColor()+"Tier: "+mod.getTier(), i+36, 50, mod.getColor().getColorIndex());
        this.fontRenderer.drawSplitString(mod.getColor()+I18n.format("info."+mod.getName()), i+36, 70, 116, mod.getColor().getColorIndex());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    @SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton
    {
        private final boolean isForward;

        public NextPageButton(int buttonId, int x, int y, boolean isForwardIn)
        {
            super(buttonId, x, y, 23, 13, "");
            this.isForward = isForwardIn;
            this.visible = true;
        }

        /**
         * Draws this button to the screen.
         */
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
            int i = 0;
            int j = 192;

            if (flag)
            {
                i += 23;
            }

            if (!this.isForward)
            {
                j += 13;
            }

            this.drawTexturedModalRect(this.x, this.y, i, j, 23, 13);
        }
    }
}