package codechicken.core;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;

public class RegistrationHelper
{
	@SuppressWarnings("unchecked")
	public static void registerHandledEntity(Class<? extends Entity> entityClass, String identifier)
	{
        EntityList.classToStringMapping.put(entityClass, identifier);
        EntityList.stringToClassMapping.put(identifier, entityClass);
	}
}
