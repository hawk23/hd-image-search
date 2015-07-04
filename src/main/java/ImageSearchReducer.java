import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mario on 04.07.15.
 */
public class ImageSearchReducer extends Reducer<NullWritable, Text, Text, Text>
{
    /**
     *
     * @param key NullWritable key
     * @param values filePath and euclidean distance of each image separated by tab
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(NullWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException
    {

        int numResults = Integer.valueOf(context.getConfiguration().get(ImageSearch.NUM_RESULTS_KEY));

        TreeMap<Double, String> bestImages = new TreeMap<Double, String>();

        // iterate over all
        for (Text value : values) {

            // value contains filePath and distance
            String v[] = value.toString().split("\t");
            String filePath = v[0];
            Double distance = Double.parseDouble(v[1]);

            bestImages.put(distance, filePath);

            if (bestImages.values().size() > numResults) {
                bestImages.remove(bestImages.lastKey());
            }
        }

        // write only the ten best as output
        for (Map.Entry<Double, String> image : bestImages.entrySet()) {
            context.write(new Text(image.getKey().toString()), new Text(image.getValue()));
        }
    }
}
