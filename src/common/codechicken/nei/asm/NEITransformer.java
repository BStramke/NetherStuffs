package codechicken.nei.asm;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import codechicken.core.asm.ClassHeirachyManager;
import codechicken.core.asm.ClassOverrider;
import codechicken.core.asm.ObfuscationManager.ClassMapping;
import codechicken.core.asm.ObfuscationManager.MethodMapping;
import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.ClassWriter.*;
import static codechicken.core.asm.InstructionComparator.*;

import cpw.mods.fml.relauncher.FMLRelauncher;
import cpw.mods.fml.relauncher.IClassTransformer;

public class NEITransformer implements IClassTransformer
{	
	/**
	 * Adds super.updateScreen() to non implementing GuiContainer subclasses
	 */
	@SuppressWarnings("unchecked")
	public byte[] transformer001(String name, byte[] bytes)
	{
		ClassMapping classmap = new ClassMapping("net.minecraft.src.GuiContainer");
		if(ClassHeirachyManager.classExtends(name, classmap.classname, bytes))
		{
			ClassNode node = new ClassNode();
			ClassReader reader = new ClassReader(bytes);
			reader.accept(node, 0);
			
			MethodMapping methodmap = new MethodMapping("net.minecraft.src.GuiScreen", "updateScreen", "()V");
			MethodMapping supermap = new MethodMapping(node.superName, methodmap);
			
			InsnList supercall = new InsnList();
			supercall.add(new VarInsnNode(ALOAD, 0));
			supercall.add(supermap.toInsn(INVOKESPECIAL));
			
			for(MethodNode methodnode : (List<MethodNode>)node.methods)
			{
				if(methodmap.matches(methodnode))
				{
					InsnList importantNodeList = getImportantList(methodnode.instructions);						
					if(!insnListMatches(importantNodeList, supercall, 0))
					{							
						methodnode.instructions.insertBefore(methodnode.instructions.getFirst(), supercall);
						System.out.println("Inserted super call into "+name+"."+supermap.name);
					}
				}
			}
			
			ClassWriter writer = new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS);
			node.accept(writer);
			bytes = writer.toByteArray();
		}
		return bytes;
	}
	
	/**
	 * Generates method for setting the placed position for the mob spawner item
	 */
	public byte[] transformer002(String name, byte[] bytes)
	{
		ClassMapping classmap = new ClassMapping("net.minecraft.src.BlockMobSpawner");
		if(name.equals(classmap.classname))
		{
			ClassNode node = new ClassNode();
			ClassReader reader = new ClassReader(bytes);
			reader.accept(node, 0);
			
			MethodMapping methodmap = new MethodMapping("net.minecraft.src.Block", "onBlockPlacedBy", "(Lnet/minecraft/src/World;IIILnet/minecraft/src/EntityLiving;)V");
			MethodNode methodnode = (MethodNode) node.visitMethod(ACC_PUBLIC, methodmap.name, methodmap.desc, null, null);

			methodnode.instructions.add(new VarInsnNode(ILOAD, 2));//param2
			methodnode.instructions.add(new FieldInsnNode(PUTSTATIC, "codechicken/nei/ItemMobSpawner", "placedX", "I"));
			methodnode.instructions.add(new VarInsnNode(ILOAD, 3));//param3
			methodnode.instructions.add(new FieldInsnNode(PUTSTATIC, "codechicken/nei/ItemMobSpawner", "placedY", "I"));
			methodnode.instructions.add(new VarInsnNode(ILOAD, 4));//param4
			methodnode.instructions.add(new FieldInsnNode(PUTSTATIC, "codechicken/nei/ItemMobSpawner", "placedZ", "I"));
			methodnode.instructions.add(new InsnNode(RETURN));
			
			ClassWriter writer = new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS);
			node.accept(writer);
			bytes = writer.toByteArray();
			
			System.out.println("Generated BlockMobSpawner helper method.");
		}
		return bytes;
	}
	
	@Override
	public byte[] transform(String name, byte[] bytes)
	{
		try
		{
			if(FMLRelauncher.side().equals("CLIENT"))
			{
				bytes = transformer001(name, bytes);
				bytes = transformer002(name, bytes);
				bytes = ClassOverrider.overrideBytes(name, bytes, new ClassMapping("net.minecraft.src.GuiContainer"), NEICorePlugin.location);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		
		return bytes;
	}
}
