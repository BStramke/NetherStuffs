package codechicken.nei;

import java.util.ArrayList;

/**
 * For repositioning recipes in overlay renderers.
 * 
 */
public interface IStackPositioner
{
	public ArrayList<PositionedStack> positionStacks(ArrayList<PositionedStack> ai);
}
