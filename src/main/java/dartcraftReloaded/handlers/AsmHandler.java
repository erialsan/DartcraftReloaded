package dartcraftReloaded.handlers;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
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

// Hooks injected in asm/ClassTransformer
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

	public static float patchArmorVisibility(EntityPlayer player) {
		int i = 0;
		int j = 0;

		for (ItemStack itemstack : player.inventory.armorInventory) {
			if (!itemstack.isEmpty()) {
				++i;
				if (itemstack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
					if (itemstack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.CAMO)) j++;
				}
			}
		}
		if (j >= 4) {
			i = 0;
		}

		return (float)i / (float)player.inventory.armorInventory.size();
	}

	public static float patchCooldown(EntityPlayer player) {
		int speedLevel = -1;
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
			IModifiable cap = stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
			if (cap.hasModifier(Constants.SPEED)) speedLevel = cap.getLevel(Constants.SPEED);
		}
		if (speedLevel > -1) {
			return (float)(1.0D / (player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * speedLevel * 1.5) * 20.0D);
		}
		return (float)(1.0D / player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * 20.0D);
	}

	public static EntityLivingBase patchAttackTarget(EntityLiving attacker, EntityLivingBase target) {
		int j = 0;
		if (target instanceof EntityPlayer) {
			for (ItemStack i : ((EntityPlayer) target).inventory.armorInventory) {
				if (i.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
					if (i.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.CAMO)) j++;
				}
			}
		}
		if (j >= 4) {
			return null;
		}
		net.minecraftforge.common.ForgeHooks.onLivingSetAttackTarget(attacker, target);
		return target;
	}

	public static boolean patchTeleport(EntityEnderman enderman, double x, double y, double z) {
		if (!enderman.getCapability(CapabilityHandler.CAPABILITY_BANE, null).canDoAbility()) return false;
		net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(enderman, x, y, z, 0);
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
		boolean flag = enderman.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

		if (flag) {
			enderman.world.playSound(null, enderman.prevPosX, enderman.prevPosY, enderman.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, enderman.getSoundCategory(), 1.0F, 1.0F);
			enderman.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
		}

		return flag;
	}

	public static void patchIgnite(EntityCreeper creeper) {
		if (creeper.getCapability(CapabilityHandler.CAPABILITY_BANE, null).canDoAbility()) {
			creeper.getDataManager().set(EntityCreeper.IGNITED, Boolean.TRUE);
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
