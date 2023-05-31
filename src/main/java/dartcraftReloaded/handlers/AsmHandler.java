package dartcraftReloaded.handlers;

import dartcraftReloaded.Constants;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AsmHandler {

	static PlayerInteractionManager interactionManager;

	public static void preHarvest(PlayerInteractionManager manager) {
		ItemStack tool = manager.player.getHeldItemMainhand();
		if (tool.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
			if (tool.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.DIRECT)) {
				startCatching();
				interactionManager = manager;
			}
		}
	}

	public static void postHarvest() {
		if (isCatching() && interactionManager != null) {
			EntityPlayer player = interactionManager.player;
			for (ItemStack is : stopCatching()) {
				ItemStack stack = is.copy();
				EntityItem fakeEntity = new EntityItem(player.world, player.posX, player.posY, player.posZ);
				fakeEntity.setItem(stack);

				EntityItemPickupEvent event = new EntityItemPickupEvent(player, fakeEntity);

				if (!MinecraftForge.EVENT_BUS.post(event)) {
					giveItemToPlayerSilent(player, stack);
				}
			}

			interactionManager = null;
		}
	}



	static boolean catchingDrops = false;
	static List<ItemStack> catchedDrops = new ArrayList<>();

	@SubscribeEvent
	public void entityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.getWorld().isRemote && event.getEntity() instanceof EntityItem) {
			if (catchingDrops && !event.isCanceled()) {
				EntityItem ei = (EntityItem) event.getEntity();
				ei.setPickupDelay(50000);
				catchedDrops.add(ei.getItem());
				event.setCanceled(true);
			}
		}
	}

	public static void startCatching() {
		catchingDrops = true;
	}

	public static List<ItemStack> stopCatching() {
		ArrayList<ItemStack> copy = new ArrayList<>(catchedDrops);
		catchedDrops.clear();

		catchingDrops = false;
		return copy;
	}

	public static boolean isCatching()
	{
		return catchingDrops;
	}


	public static void giveItemToPlayerSilent(EntityPlayer player, @Nonnull ItemStack stack) {
		IItemHandler inventory = new PlayerMainInvWrapper(player.inventory);
		World world = player.world;

		ItemStack remainder = stack;
		if(!remainder.isEmpty()) {
			remainder = ItemHandlerHelper.insertItemStacked(inventory, remainder, false);
		}

		if (!(player instanceof FakePlayer) && (remainder.isEmpty() || remainder.getCount() != stack.getCount())) {
			world.playSound(null, player.posX, player.posY + 0.5, player.posZ,
					SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
		}

		if (!remainder.isEmpty() && !world.isRemote) {
			EntityItem entityitem = new EntityItem(world, player.posX, player.posY + 0.5, player.posZ, stack);
			entityitem.setPickupDelay(40);
			entityitem.motionX = 0;
			entityitem.motionZ = 0;

			world.spawnEntity(entityitem);
		}
	}

}
