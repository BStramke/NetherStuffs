package NetherStuffsCore;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

public class NetherStuffsAccessTransformer2 extends AccessTransformer {
	private static NetherStuffsAccessTransformer2 instance;
	private static List mapFiles = new LinkedList();

	public NetherStuffsAccessTransformer2() throws IOException {
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
	}
}