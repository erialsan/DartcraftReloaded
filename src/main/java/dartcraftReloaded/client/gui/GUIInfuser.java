package dartcraftReloaded.client.gui;

import dartcraftReloaded.capablilities.Modifiable.Modifier;
import dartcraftReloaded.fluids.ModFluids;
import dartcraftReloaded.handlers.PacketHandler;
import dartcraftReloaded.networking.InfuserMessage;
import dartcraftReloaded.container.ContainerBlockInfuser;
import dartcraftReloaded.tileEntity.TileEntityInfuser;
import dartcraftReloaded.Constants;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIInfuser extends GuiContainer {

    private TileEntityInfuser te;
    private GuiButton startButton = new GuiButton(0, 39, 101, 12, 12, "Start Button");
    private ResourceLocation TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/container/forceinfuser.png");

    public GUIInfuser(IInventory playerInv, TileEntityInfuser te) {
        super(new ContainerBlockInfuser(playerInv, te));

        this.xSize = 176;
        this.ySize = 208;

        this.te = te;

        this.addButton(startButton);

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
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0,0,this.xSize, this.ySize);

    }

    @Override
    public List<String> getItemToolTip(ItemStack stack) {
        List<String> list = super.getItemToolTip(stack);

        for (Modifier modifier : Constants.MODIFIER_REGISTRY.values()) {
            ItemStack item = modifier.getItem();
            if (item.getItem().equals(stack.getItem()) && item.getItemDamage() == stack.getItemDamage()) {
                if (item.getItem() == Items.POTIONITEM || item.getItem() == Items.ENCHANTED_BOOK) {
                    if (item.getTagCompound() != null && stack.getTagCompound() != null && item.getTagCompound().equals(stack.getTagCompound())) {
                        list.add(modifier.getColor()+modifier.getName());
                        if (te.getBookLevel() < modifier.getTier()) {
                            list.add(modifier.getColor()+"Tier "+modifier.getTier());
                        }
                    }
                } else {
                    list.add(modifier.getColor()+modifier.getName());
                }
            }
        }

        return list;
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int height = (int) ((double) te.energyStorage.getEnergyStored() / 10000D * 107);//152
        this.drawTexturedModalRect(152, 11+107-height, 176, 20+107-height, 12, height);


        int progressHeight = (int) ((double) te.processTime * 20 / te.maxProcessTime);
        this.drawTexturedModalRect(134, 93+20-progressHeight, 176, 20-progressHeight, 2, progressHeight);

        if (te.getBookLevel() < 2) {
            this.drawTexturedModalRect(104, 32, 176, 127, 16, 16);
        }
        if (te.getBookLevel() < 3) {
            this.drawTexturedModalRect(116, 57, 176, 127, 16, 16);
        }
        if (te.getBookLevel() < 4) {
            this.drawTexturedModalRect(104, 81, 176, 127, 16, 16);
        }
        if (te.getBookLevel() < 5) {
            this.drawTexturedModalRect(80, 93, 176, 127, 16, 16);
        }
        if (te.getBookLevel() < 6) {
            this.drawTexturedModalRect(56, 81, 176, 127, 16, 16);
        }
        if (te.getBookLevel() < 7) {
            this.drawTexturedModalRect(44, 57, 176, 127, 16, 16);
        }
        if (te.getBookLevel() < 8) {
            this.drawTexturedModalRect(56, 32, 176, 127, 16, 16);
        }

        if (te.processTime == -1 && te.validRecipe()) {
            this.drawTexturedModalRect(39, 101, 178, 0, 13, 13);
        }

        int actualMouseX = mouseX - ((this.width - this.xSize) / 2);
        int actualMouseY = mouseY - ((this.height - this.ySize) / 2);
        this.drawFluidBar();



        if(isPointInRegion(39, 101, 12, 12, mouseX, mouseY)){
            List<String> text = new ArrayList<>();
            if (te.processTime == -1) {
                if (te.validRecipe()) {
                    text.add(TextFormatting.WHITE + I18n.format("gui.blockInfuser.Start.tooltip"));
                } else {
                    text.add(TextFormatting.GRAY + I18n.format("gui.blockInfuser.Start.tooltip"));
                }
            }
            this.drawHoveringText(text, actualMouseX, actualMouseY);
        }

        if(isPointInRegion(10, 60, 16, 58, mouseX, mouseY)) {
            List<String> text = new ArrayList<>();
            if (te.tank.getFluid() == null) {
                text.add(I18n.format("gui.blockInfuser.Empty.tooltip"));
            } else {
                text.add(TextFormatting.YELLOW + "Liquid Force" + TextFormatting.WHITE + " (" + te.tank.getFluidAmount() + ")");
            }

            this.drawHoveringText(text, actualMouseX, actualMouseY);
        }

        if(isPointInRegion(152, 11, 12, 106, mouseX, mouseY)){
            List<String> text = new ArrayList<>();
            text.add(te.energyStorage.getEnergyStored() + " RF");

            this.drawHoveringText(text, actualMouseX, actualMouseY);
        }
        startButton.enabled = (te.processTime == -1);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(isPointInRegion(39, 101, 12, 12, mouseX, mouseY)){
            this.actionPerformed(startButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        //Start Infusion
        if(button.id == 0){
            if (button.enabled) {
                PacketHandler.sendToServer(new InfuserMessage(te.getPos()));
            }
        }
    }

    private void drawFluidBar(){
        Fluid fluid = ModFluids.fluidForce;

        TextureAtlasSprite fluidTexture = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        int fluidHeight = te.getFluidGuiHeight(58);
        drawTexturedModalRect(10, 60 + (58 - fluidHeight), fluidTexture, 16, fluidHeight);
    }

}