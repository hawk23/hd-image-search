import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by mario on 03.07.15.
 */
public class ImageFeatureExtractMapper extends Mapper<Text, Text, Text, Text>
{
    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException
    {
        super.map(key, value, context);
    }
}