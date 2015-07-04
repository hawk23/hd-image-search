import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by mario on 04.07.15.
 */
public class ImageSearchMapper extends Mapper<LongWritable, Text, Text, LongWritable>
{
    /**
     * gets the image path and feature of an image as input and calculates the difference to the sample image
     * output:
     * Key: Image path
     * Value: Difference to sample image
     *
     * @param key the line number of the image feature inside the index file
     * @param value the extracted image features and the filepath: '[filepath]:[feature]
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {
        String      histogramRaw    = context.getConfiguration().get(ImageSearch.SAMPLE_KEY);
        Double[]    histogram       = new Double[100]; // TODO parse raw

        // TODO: calculate difference

        
    }
}
