package codechicken.core.asm;

import java.io.File;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.TraceMethodVisitor;

import codechicken.core.asm.ObfuscationManager.MethodMapping;

import static org.objectweb.asm.Opcodes.*;

public class MethodASMifier extends ClassVisitor
{
	PrintWriter printWriter;
	MethodMapping method;
	Printer asmifier;
	
	public MethodASMifier(MethodMapping method, Printer printer, PrintWriter printWriter)
	{
		super(ASM4);
		this.method = method;
		this.printWriter = printWriter;
		asmifier = printer;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
	{
		if(name.equals(method.name) && desc.equals(method.desc))
		{
			Printer localPrinter = asmifier.visitMethod(access, name, desc, signature, exceptions);
		    return new TraceMethodVisitor(null, localPrinter);
		}
		
		return null;
	}
	
	@Override
	public void visitEnd()
	{
		asmifier.visitClassEnd();
	    asmifier.print(printWriter);
		super.visitEnd();
	}
	
	public static void printMethod(MethodMapping method, Printer printer, File toFile)
	{
		try
		{
			printMethod(method, CodeChickenCorePlugin.cl.getClassBytes(method.owner), printer, toFile);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	

	public static void printMethod(MethodMapping method, byte[] bytes, Printer printer, File toFile)
	{
		try
		{
			if(!toFile.getParentFile().exists())
				toFile.getParentFile().mkdirs();		
			if(!toFile.exists())
				toFile.createNewFile();
			
			PrintWriter printWriter = new PrintWriter(toFile);
			
			ClassVisitor cv = new MethodASMifier(method, printer, printWriter);
			ClassReader cr = new ClassReader(bytes);
			cr.accept(cv, 0);
			
			printWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}
