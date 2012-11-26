package codechicken.core.asm;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import codechicken.core.ReflectionManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.asm.ASMTransformer;

public class DevTransfomer extends ASMTransformer
{
	private boolean disabled = true;
	
	public DevTransfomer()
	{
		if(ObfuscationManager.obfuscated)
			disabled = true;
		try
		{
			if(ReflectionManager.callMethod(ReflectionManager.findClass("net.minecraftforge.common.ForgeVersion"), Integer.class, "getBuildVersion") != 0)
				disabled = true;
		}
		catch(Exception e)
		{
			disabled = true;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public byte[] transform(String name, byte[] bytes)
	{
		if(disabled)
			return bytes;		

		return bytes;
		
		/*ClassReader cr = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        cr.accept(classNode, 0);
        
        AnnotationNode modann = null;
        if(classNode.visibleAnnotations != null)
        {
	        for(AnnotationNode ann : (List<AnnotationNode>)classNode.visibleAnnotations)
	        {
	        	if(ann.desc.equals(modanndesc))
	        	{
	        		modann = ann;
	        		break;
	        	}
	        }
        }
        if(modann == null)
        	return bytes;
        
        String dependancies = null;
        int i;
        for(i = 0; i < modann.values.size(); i+=2)
        {
        	if(modann.values.get(i).equals("dependencies"))
        	{
        		dependancies = (String) modann.values.get(i+1);
        		break;
        	}
        }
        if(dependancies == null)
        	return bytes;
        
        Pattern forgeRev = Pattern.compile("Forge@\\[[0-9\\.]+");
        Matcher matcher = forgeRev.matcher(dependancies);
        if(matcher.find())
        {
        	String forgedep = matcher.group();
        	forgedep = forgedep.substring(0, forgedep.lastIndexOf('.'))+0;
        	modann.values.set(i+1, dependancies.substring(0, matcher.start())+forgedep+dependancies.substring(matcher.end()));
        }
        else
        	return bytes;
        
        ClassWriter writer = new ClassWriter(0);
        classNode.accept(writer);
        return writer.toByteArray();*/
	}
}
