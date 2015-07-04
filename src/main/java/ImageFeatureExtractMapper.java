import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by mario on 03.07.15.
 */
public class ImageFeatureExtractMapper extends Mapper<LongWritable, Text, Text, Text>
{
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {
        super.map(key, value, context);

        System.out.println("Map: key: " + key.toString());
    }
}
