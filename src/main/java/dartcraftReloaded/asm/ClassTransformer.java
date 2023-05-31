package dartcraftReloaded.asm;

import static org.objectweb.asm.Opcodes.ALOAD;

import java.util.Iterator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

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
		}

		return basicClass;
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


	public int getNextIndex(MethodNode mn) {
		Iterator it = mn.localVariables.iterator();
		int max = 0;
		int next = 0;
		while (it.hasNext()) {
			LocalVariableNode var = (LocalVariableNode) it.next();
			int index = var.index;
			if (index >= max) {
				max = index;
				next = max + Type.getType(var.desc).getSize();
			}
		}
		return next;
	}
}
