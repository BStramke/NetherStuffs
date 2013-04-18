package codechicken.core.data;

import java.io.IOException;
import java.io.InputStream;

public class MCDataInputStream extends InputStream
{
    private MCDataInput in;
    
    public MCDataInputStream(MCDataInput in)
    {
        this.in = in;
    }
    
    @Override
    public int read() throws IOException
    {
        return in.readByte()&0xFF;
    }
}
