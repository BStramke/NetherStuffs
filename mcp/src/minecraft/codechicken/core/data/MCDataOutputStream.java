package codechicken.core.data;

import java.io.IOException;
import java.io.OutputStream;

public class MCDataOutputStream extends OutputStream
{
    private MCDataOutput out;
    
    public MCDataOutputStream(MCDataOutput out)
    {
        this.out = out;
    }
    
    @Override
    public void write(int b) throws IOException
    {
        out.writeByte(b);
    }
}
