package bstramke.NetherStuffsCore;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cpw.mods.fml.relauncher.IClassTransformer;

public class NetherStuffsAccessTransformer implements IClassTransformer {

	public static HashMap<String, String> override = new HashMap<String, String>();

	public static void addClassOverride(String name, String description) {
		override.put(name, description);
	}

	@Override
	public byte[] transform(String name, byte[] bytes) {
		//System.out.println("try override: " + name);
		if (override.containsKey(name)) {
			System.out.println("attempting override of " + name + " from " + NetherStuffsCorePlugin.myLocation.getName());
			bytes = overrideBytes(name, bytes, NetherStuffsCorePlugin.myLocation);
		} 
		return bytes;
	}

	public static byte[] overrideBytes(String name, byte[] bytes, File location) {
		try {
			ZipFile zip = new ZipFile(location);
			ZipEntry entry = zip.getEntry(name.replace('.', '/') + ".class");
			if (entry == null)
				System.out.println(name + " not found in " + location.getName());
			else {
				DataInputStream zin = new DataInputStream(zip.getInputStream(entry));
				bytes = new byte[(int) entry.getSize()];
				zin.readFully(bytes);
				zin.close();
				System.out.println(name + " was overriden from " + location.getName());
			}
			zip.close();
		} catch (Exception e) {
			throw new RuntimeException("Error overriding " + name + " from " + location.getName(), e);
		}
		return bytes;
	}
}
