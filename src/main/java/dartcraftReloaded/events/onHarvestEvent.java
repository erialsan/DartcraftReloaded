package dartcraftReloaded.events;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
public class onHarvestEvent {

    @SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        ItemStack heldItem;
        Random random = new Random();
        if(event.getHarvester() != null){
            heldItem = event.getHarvester().inventory.getCurrentItem();
            if (heldItem != ItemStack.EMPTY) {
                if (heldItem.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                    IModifiable cap = heldItem.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
                    if (cap.hasModifier(Constants.HEAT)) {
                        ListIterator<ItemStack> iter = event.getDrops().listIterator();
                        while (iter.hasNext()) {
                            ItemStack drop = iter.next();
                            ItemStack smelted = FurnaceRecipes.instance().getSmeltingResult(drop);
                            if (!smelted.isEmpty()) {
                                smelted = smelted.copy();
                                if(cap.hasModifier(Constants.LUCK)) {
                                    smelted.setCount(smelted.getCount() * random.nextInt(cap.getLevel(Constants.LUCK) + 1) + 1);
                                } else {
                                    smelted.setCount(drop.getCount());
                                }
                                iter.set(smelted);

                                float xp = FurnaceRecipes.instance().getSmeltingExperience(smelted);
                                if (xp < 1 && Math.random() < xp) {
                                    xp += 1F;
                                }
                                if (xp >= 1F) {
                                    event.getState().getBlock().dropXpOnBlockBreak(event.getWorld(), event.getPos(), (int) xp);
                                }
                            }
                        }
                    } else if (cap.hasModifier(Constants.LUCK)) {
                        IBlockState i = event.getWorld().getBlockState(event.getPos());
                        event.getDrops().clear();
                        NonNullList<ItemStack> j = NonNullList.create();

                        i.getBlock().getDrops(j, event.getWorld(), event.getPos(), i, cap.getLevel(Constants.LUCK));
                        for (ItemStack k : j) {
                            event.getDrops().add(k);
                        }
                    }
                }
            }
        }
    }
}
