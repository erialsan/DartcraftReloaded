package dartcraftReloaded.asm;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import net.minecraft.launchwrapper.IClassTransformer;

import static org.objectweb.asm.Opcodes.*;

public class ClassTransformer implements IClassTransformer {
	public static Logger logger = LogManager.getLogger("DCRCore");

	final String asmHandler = "dartcraftReloaded/handlers/AsmHandler";

	public static int transformations = 0;

	public ClassTransformer() {
		logger.log(Level.DEBUG, "Starting Class Transformation");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (transformedName.equals("net.minecraft.server.management.PlayerInteractionManager")) {
			transformations++;
			return patchPlayerInteractionManager(basicClass);
		} else if (transformedName.equals("net.minecraft.entity.player.EntityPlayer")) {
			transformations++;
			return patchEntityPlayer(basicClass);
		} else if (transformedName.equals("net.minecraft.entity.EntityLiving")) {
			transformations++;
			return patchEntityLiving(basicClass);
		} else if (transformedName.equals("net.minecraft.entity.monster.EntityCreeper")) {
			transformations++;
			return patchCreeper(basicClass);
		} else if (transformedName.equals("net.minecraft.entity.monster.EntityEnderman")) {
			transformations++;
			return patchTeleport(basicClass);
		}

		return basicClass;
	}

	private byte[] patchCreeper(byte[] basicClass) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found EntityCreeper Class: " + classNode.name);

		MethodNode ignite = null;

		for (MethodNode mn : classNode.methods) {
			if (mn.name.equals(MCPNames.method("func_146079_cb"))) {
				ignite = mn;
			}
		}

		if (ignite != null) {
			AbstractInsnNode ain = ignite.instructions.get(0);
			logger.log(Level.DEBUG, " - Found ignite");

			InsnList toInsert = new InsnList();
			toInsert.add(new VarInsnNode(ALOAD, 0));
			toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "patchIgnite", "(Lnet/minecraft/entity/monster/EntityCreeper;)V", false));
			toInsert.add(new InsnNode(RETURN));
			ignite.instructions.insertBefore(ain, toInsert);
		}

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}


	private byte[] patchEntityPlayer(byte[] basicClass) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found EntityPlayer Class: " + classNode.name);

		MethodNode getArmorVisibility = null;
		MethodNode cooldown = null;

		for (MethodNode mn : classNode.methods) {
			if (mn.name.equals(MCPNames.method("func_82243_bO"))) {
				getArmorVisibility = mn;
			}
			if (mn.name.equals(MCPNames.method("func_184818_cX"))) {
				cooldown = mn;
			}
		}

		if (getArmorVisibility != null) {
			AbstractInsnNode ain = null;
			logger.log(Level.DEBUG, " - Found getArmorVisibility");
			for (int i = 0; i < getArmorVisibility.instructions.size(); i++) {
				if (getArmorVisibility.instructions.get(i).getOpcode() == FRETURN) ain = getArmorVisibility.instructions.get(i);
			}
			if (ain != null) {
				InsnList toInsert = new InsnList();
				toInsert.add(new VarInsnNode(ALOAD, 0));
				toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "patchArmorVisibility", "(Lnet/minecraft/entity/player/EntityPlayer;)F", false));
				getArmorVisibility.instructions.insertBefore(ain, toInsert);
			}
		}

		if (cooldown != null) {
			AbstractInsnNode ain = cooldown.instructions.get(0);
			logger.log(Level.DEBUG, " - Found getCooldownPeriod");
			InsnList toInsert = new InsnList();
			toInsert.add(new VarInsnNode(ALOAD, 0));
			toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "patchCooldown", "(Lnet/minecraft/entity/player/EntityPlayer;)F", false));
			toInsert.add(new InsnNode(FRETURN));
			cooldown.instructions.insertBefore(ain, toInsert);
		}

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}


	private byte[] patchTeleport(byte[] basicClass) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found EntityEnderman Class: " + classNode.name);

		MethodNode method = null;

		for (MethodNode mn : classNode.methods) {
			if (mn.name.equals(MCPNames.method("func_70825_j"))) {
				method = mn;
			}
		}

		if (method != null) {
			AbstractInsnNode ain = method.instructions.get(0);
			logger.log(Level.DEBUG, " - Found teleport");
			InsnList toInsert = new InsnList();
			toInsert.add(new VarInsnNode(ALOAD, 0));
			toInsert.add(new VarInsnNode(DLOAD, 1));
			toInsert.add(new VarInsnNode(DLOAD, 3));
			toInsert.add(new VarInsnNode(DLOAD, 5));

			toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "patchTeleport", "(Lnet/minecraft/entity/monster/EntityEnderman;DDD)Z", false));
			toInsert.add(new InsnNode(IRETURN));
			method.instructions.insertBefore(ain, toInsert);
		}

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}

	private byte[] patchEntityLiving(byte[] basicClass) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found EntityLiving Class: " + classNode.name);

		MethodNode method = null;

		for (MethodNode mn : classNode.methods) {
			if (mn.name.equals(MCPNames.method("func_70624_b"))) {
				method = mn;
			}
		}

		if (method != null) {
			AbstractInsnNode ain = method.instructions.get(0);
			logger.log(Level.DEBUG, " - Found setAttackTarget");
			InsnList toInsert = new InsnList();
			toInsert.add(new VarInsnNode(ALOAD, 0));
			toInsert.add(new VarInsnNode(ALOAD, 1));
			toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "patchAttackTarget", "(Lnet/minecraft/entity/EntityLiving;Lnet/minecraft/entity/EntityLivingBase;)V", false));
			toInsert.add(new InsnNode(RETURN));
			method.instructions.insertBefore(ain, toInsert);
		}

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}


	private byte[] patchPlayerInteractionManager(byte[] basicClass) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found PlayerInteractionManager Class: " + classNode.name);

		MethodNode tryHarvestBlock = null;

		for (MethodNode mn : classNode.methods) {
			if (mn.name.equals(MCPNames.method("func_180237_b"))) {
				tryHarvestBlock = mn;
			}
		}

		if (tryHarvestBlock != null) {
			logger.log(Level.DEBUG, " - Found tryHarvestBlock");

			InsnList startInsert = new InsnList();
			startInsert.add(new VarInsnNode(ALOAD, 0));
			startInsert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, asmHandler, "preHarvest", "(Lnet/minecraft/server/management/PlayerInteractionManager;)V", false));

			tryHarvestBlock.instructions.insert(startInsert);

			for (int i = 0; i < tryHarvestBlock.instructions.size(); i++) {
				AbstractInsnNode ain = tryHarvestBlock.instructions.get(i);

				if (ain.getOpcode() == Opcodes.IRETURN) {
					InsnList endInsert = new InsnList();
					endInsert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, asmHandler, "postHarvest", "()V", false));

					tryHarvestBlock.instructions.insertBefore(ain, endInsert);
					i += 1;
				}
			}
		}

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}

}
