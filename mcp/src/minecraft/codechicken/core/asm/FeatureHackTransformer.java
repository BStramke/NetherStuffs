package codechicken.core.asm;

import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.cert.Certificate;
import java.util.Hashtable;
import java.util.List;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import codechicken.core.asm.ObfuscationMappings.DescriptorMapping;

import cpw.mods.fml.relauncher.IClassTransformer;

public class FeatureHackTransformer implements Opcodes, IClassTransformer
{    
    public FeatureHackTransformer()
    {
    }
    
    /**
     * Allow GameData to hide some items.
     */
    DescriptorMapping m_newItemAdded = new DescriptorMapping("cpw/mods/fml/common/registry/GameData", "newItemAdded", "(Lnet/minecraft/item/Item;)V");
    DescriptorMapping f_lastBrightness = new DescriptorMapping("net/minecraft/client/renderer/OpenGlHelper", "lastBrightness", "I");
    DescriptorMapping m_startGame = new DescriptorMapping("net/minecraft/client/Minecraft", "startGame", "()V");
    DescriptorMapping m_findClass = new DescriptorMapping("cpw/mods/fml/relauncher/RelaunchClassLoader", "findClass", "(Ljava/lang/String;)Ljava/lang/Class;");
	DescriptorMapping m_canPlaceBlockAt = new DescriptorMapping("net/minecraft/block/Block", "canPlaceBlockAt", "(Lnet/minecraft/world/World;III)Z");
	DescriptorMapping m_isBlockReplaceable = new DescriptorMapping("net/minecraft/block/Block", "isBlockReplaceable", "(Lnet/minecraft/world/World;III)Z");
	
    private byte[] transformer001(String name, byte[] bytes)
    {
        ClassNode cnode = ASMHelper.createClassNode(bytes);
        MethodNode mnode = ASMHelper.findMethod(m_newItemAdded, cnode);
        
        InsnList overrideList = new InsnList();
        LabelNode label = new LabelNode();
        overrideList.add(new MethodInsnNode(INVOKESTATIC, "codechicken/core/featurehack/GameDataManipulator", "override", "()Z"));
        overrideList.add(new JumpInsnNode(IFEQ, label));
        overrideList.add(new InsnNode(RETURN));
        overrideList.add(label);
        mnode.instructions.insert(mnode.instructions.get(1), overrideList);
        
        bytes = ASMHelper.createBytes(cnode, ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
        return bytes;
    }
    
    @Override
    public byte[] transform(String name, String tname, byte[] bytes)
    {
        if(m_newItemAdded.isClass(name))
            bytes = transformer001(name, bytes);
        if(f_lastBrightness.isClass(name))
            bytes = transformer002(name, bytes);
        if(m_startGame.isClass(name))
            bytes = transformer003(name, bytes);
        if(name.startsWith("net.minecraftforge"))
            usp(name);
        if(m_canPlaceBlockAt.isClass(name))
            bytes = transformer004(name, bytes);
		return bytes;
	}

    private byte[] transformer004(String name, byte[] bytes)
    {
        ClassNode cnode = ASMHelper.createClassNode(bytes);
        MethodNode method1 = ASMHelper.findMethod(m_isBlockReplaceable, cnode);
        InsnList replacement1 = new InsnList();
        replacement1.add(new VarInsnNode(ALOAD, 0));
        replacement1.add(new FieldInsnNode(GETFIELD, "net/minecraft/block/Block", "blockMaterial", "Lnet/minecraft/block/material/Material;"));
        replacement1.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/block/material/Material", "isReplaceable", "()Z"));
        replacement1.add(new InsnNode(IRETURN));
        method1.instructions = replacement1;
        
        MethodNode method2 = ASMHelper.findMethod(m_canPlaceBlockAt, cnode);
        InsnList replacement2 = new InsnList();
        replacement2.add(new VarInsnNode(ALOAD, 1));
        replacement2.add(new VarInsnNode(ILOAD, 2));
        replacement2.add(new VarInsnNode(ILOAD, 3));
        replacement2.add(new VarInsnNode(ILOAD, 4));
        replacement2.add(new MethodInsnNode(INVOKESTATIC, "codechicken/core/featurehack/TweakTransformerHelper", "canPlaceBlockAt", "(Lnet/minecraft/world/World;III)Z"));
        replacement2.add(new InsnNode(IRETURN));
        method2.instructions = replacement2;
        
        return ASMHelper.createBytes(cnode, 3);
    }

    private byte[] transformer002(String name, byte[] bytes)
    {
        ClassNode cnode = ASMHelper.createClassNode(bytes);
        FieldNode fnode = ASMHelper.findField(f_lastBrightness, cnode);
        if(fnode == null)
        {
            cnode.fields.add(new FieldNode(ACC_PUBLIC|ACC_STATIC, f_lastBrightness.s_name, f_lastBrightness.s_desc, null, null));
            MethodNode mlightmap = ASMHelper.findMethod(new DescriptorMapping("net/minecraft/client/renderer/OpenGlHelper", "setLightmapTextureCoords", "(IFF)V"), cnode);
            
            InsnList hook = new InsnList();
            LabelNode lend = new LabelNode();
            hook.add(new VarInsnNode(ILOAD, 0));
            hook.add(new FieldInsnNode(GETSTATIC, "net/minecraft/client/renderer/OpenGlHelper", "lightmapTexUnit", "I"));
            hook.add(new JumpInsnNode(IF_ICMPNE, lend));
            hook.add(new VarInsnNode(FLOAD, 2));
            hook.add(new InsnNode(F2I));
            hook.add(new IntInsnNode(BIPUSH, 16));
            hook.add(new InsnNode(ISHL));
            hook.add(new VarInsnNode(FLOAD, 1));
            hook.add(new InsnNode(F2I));
            hook.add(new InsnNode(IOR));
            hook.add(f_lastBrightness.toFieldInsn(PUTSTATIC));
            hook.add(lend);
            
            InsnList needle = new InsnList();
            needle.add(new InsnNode(RETURN));
            List<AbstractInsnNode> ret = InstructionComparator.insnListFindEnd(mlightmap.instructions, needle);
            if(ret.size() != 1)
                throw new RuntimeException("Needle not found in Haystack: " + ASMHelper.printInsnList(mlightmap.instructions)+"\n" + ASMHelper.printInsnList(needle));
            
            mlightmap.instructions.insertBefore(ret.get(0), hook);
            bytes = ASMHelper.createBytes(cnode, 3);
            System.out.println("Brightness hook injected");
        }
        return bytes;
    }
    
    /**
     * Stencil buffer
     * TODO: forge PR
     */
    private byte[] transformer003(String name, byte[] bytes)
    {
        ClassNode cnode = ASMHelper.createClassNode(bytes);
        MethodNode mnode = ASMHelper.findMethod(m_startGame, cnode);
        
        InsnList needle = new InsnList();
        needle.add(new IntInsnNode(BIPUSH, 24));
        needle.add(new MethodInsnNode(INVOKEVIRTUAL, "org/lwjgl/opengl/PixelFormat", "withDepthBits", "(I)Lorg/lwjgl/opengl/PixelFormat;"));
        
        InsnList call = new InsnList();
        call.add(new InsnNode(ICONST_1));
        call.add(new MethodInsnNode(INVOKEVIRTUAL, "org/lwjgl/opengl/PixelFormat", "withStencilBits", "(I)Lorg/lwjgl/opengl/PixelFormat;"));
        
        List<AbstractInsnNode> ret = InstructionComparator.insnListFindEnd(mnode.instructions, needle);
        if(ret.size() != 1)
            throw new RuntimeException("Needle not found in Haystack: " + ASMHelper.printInsnList(mnode.instructions)+"\n" + ASMHelper.printInsnList(needle));
        
        mnode.instructions.insert(ret.get(0), call);
        bytes = ASMHelper.createBytes(cnode, 3);
        System.out.println("1 bit stencil buffer added");
        
        return bytes;
    }
    
    @SuppressWarnings("unchecked")
    public static void usp(String name)
    {
        int ld = name.lastIndexOf('.');
        String pkg = ld == -1 ? "" : name.substring(0, ld);
        String rname = name.replace('.', '/')+".class";
        URL res = CodeChickenCorePlugin.cl.findResource(rname);
        try
        {
            Field f = ClassLoader.class.getDeclaredField("package2certs");
            f.setAccessible(true);
            Hashtable<String, Certificate[]> cmap = (Hashtable<String, Certificate[]>) f.get(CodeChickenCorePlugin.cl);

            CodeSigner[] cs = null;
            URLConnection urlconn = res.openConnection();
            if(urlconn instanceof JarURLConnection && ld >= 0)
            {
                JarFile jf = ((JarURLConnection)urlconn).getJarFile();
                if (jf != null && jf.getManifest() != null)
                    cs = jf.getJarEntry(rname).getCodeSigners();
            }
            
            Certificate[] certs = new CodeSource(res, cs).getCertificates();
            cmap.put(pkg, certs == null ? new Certificate[0] : certs);
        }
        catch (Exception e)
        {
            throw new RuntimeException("qw");
        }
    }
}
