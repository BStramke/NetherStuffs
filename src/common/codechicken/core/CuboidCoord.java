package codechicken.core;

public class CuboidCoord
{
	public BlockCoord min;
	public BlockCoord max;
	
	public CuboidCoord(BlockCoord min, BlockCoord max)
	{
		this.min = min;
		this.max = max;
	}
	
	public CuboidCoord(BlockCoord coord)
	{
		this(coord, coord);
	}

	public void expand(int side)
	{
		if(side%2 == 0)//negative side
			min = min.offset(side);
		else
			max = max.offset(side);
	}
	
	public void shrink(int side)
	{
		if(side%2 == 0)//negative side
			min = min.inset(side);
		else
			max = max.inset(side);
	}

	public int size(int s)
	{
		switch(s)
		{
			case 0:
			case 1:
				return max.y - min.y+1;
			case 2:
			case 3:
				return max.z - min.z+1;
			case 4:
			case 5:
				return max.x - min.x+1;
			default:
				return 0;
		}
	}
	
	public int getVolume()
	{
		return (max.x-min.x+1)*(max.y-min.y+1)*(max.z-min.z+1);
	}

	public Vector3 getCenterVec()
	{
		return new Vector3(min.x+(max.x-min.x+1)/2D, min.y+(max.y-min.y+1)/2D, min.z+(max.z-min.z+1)/2D);
	}

	public BlockCoord getCenter()
	{
		return new BlockCoord(min.x+(max.x-min.x)/2, min.y+(max.y-min.y)/2, min.z+(max.z-min.z)/2);
	}

    public boolean contains(BlockCoord coord)
    {
        return coord.x >= min.x && coord.x <= max.x
                && coord.y >= min.y && coord.y <= max.y
                && coord.z >= min.z && coord.z <= max.z;
    }
}
