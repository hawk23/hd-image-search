import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by mario on 04.07.15.
 */
public class ImageSearchReducer extends Reducer<Text, LongWritable, Text, Text>
{
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException
    {
        // TODO: find ten best images (lowest distance) and save them as result.
    }
}
