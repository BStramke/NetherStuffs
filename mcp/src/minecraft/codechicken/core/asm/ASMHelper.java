package codechicken.core.asm;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import codechicken.core.asm.ObfuscationManager.MethodMapping;

public class ASMHelper
{
    @SuppressWarnings("unchecked")
    public static MethodNode findMethod(MethodMapping methodmap, ClassNode cnode)
    {
        for(MethodNode mnode : (List<MethodNode>) cnode.methods)
            if(methodmap.matches(mnode))
                return mnode;
        return null;
    }

    public static ClassNode createClassNode(byte[] bytes)
    {
        ClassNode cnode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(cnode, 0);
        return cnode;
    }

    public static byte[] createBytes(ClassNode cnode, int i)
    {
        ClassWriter cw = new ClassWriter(i);
        cnode.accept(cw);
        return cw.toByteArray();
    }
}
