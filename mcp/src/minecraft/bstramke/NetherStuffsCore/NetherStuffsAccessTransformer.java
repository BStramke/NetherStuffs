package bstramke.NetherStuffsCore;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cpw.mods.fml.relauncher.IClassTransformer;

public class NetherStuffsAccessTransformer implements IClassTransformer {
	//private static NetherStuffsAccessTransformer instance;
//	private static List mapFiles = new LinkedList();

/*	public NetherStuffsAccessTransformer() throws IOException {
		super();
		instance = this;
		// add your access transformers here!
		mapFiles.add("netherstuffs_at.cfg");
		Iterator it = mapFiles.iterator();
		while (it.hasNext()) {
			String file = (String) it.next();
			this.readMapFile(file);
		}

	}

	public static void addTransformerMap(String mapFileName) {
		if (instance == null) {
			mapFiles.add(mapFileName);
		} else {
			instance.readMapFile(mapFileName);
		}
	}

	private void readMapFile(String name) {
		System.out.println("Adding transformer map: " + name);
		try {
			// get a method from AccessTransformer
			Method e = AccessTransformer.class.getDeclaredMethod("readMapFile", new Class[] { String.class });
			e.setAccessible(true);
			// run it with the file given.
			e.invoke(this, new Object[] { name });

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}*/

	public static HashMap<String, String> override = new HashMap<String, String>();

	public static void addClassOverride(String name, String description) {
		override.put(name, description);
		// System.out.println("list:"+override);
	}

	@Override
	public byte[] transform(String name, byte[] bytes) {
		//System.out.println("try override: " + name);
		if (override.containsKey(name)) {
			System.out.println("attempting override of " + name + " from " + NetherStuffsCorePlugin.myLocation);
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
				InputStream zin = zip.getInputStream(entry);
				bytes = new byte[(int) entry.getSize()];
				zin.read(bytes);
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
