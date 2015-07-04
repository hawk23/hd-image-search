import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by mario on 04.07.15.
 */
public class ImageSearchMapper extends Mapper<Text, Text, NullWritable, Text>
{
    /**
     * gets the image path and feature of an image as input and calculates the difference to the sample image
     * output:
     * Key: Image path
     * Value: Difference to sample image
     *
     * @param key the filepath of the current image
     * @param value the extracted image features of the current image
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException
    {
        String          sampleHistogram = context.getConfiguration().get(ImageSearch.SAMPLE_HISTOGRAM_KEY);
        String          sampleFilepath  = context.getConfiguration().get(ImageSearch.SAMPLE_FILEPATH_KEY);

        ImageFeature    sampleFeature   = new ImageFeature(sampleFilepath, sampleHistogram);
        ImageFeature    currentFeature  = new ImageFeature(key.toString(), value.toString());

        double          distance        = EuclideanDistance.calulate(sampleFeature.getHistogram(), currentFeature.getHistogram());

        // TODO maybe use own Writable
        context.write(NullWritable.get(), new Text(key + "\t" + distance));
    }
}
