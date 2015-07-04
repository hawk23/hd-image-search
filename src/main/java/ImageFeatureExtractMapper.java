import net.semanticmetadata.lire.DocumentBuilderFactory;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by mario on 03.07.15.
 */
public class ImageFeatureExtractMapper extends Mapper<NullWritable, BytesWritable, Text, Text>
{
    @Override
    protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException
    {
        // System.out.println("Map: key: " + value.toString());
        context.write(new Text("Key"), new Text("Value"));
    }
}
