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
        super.reduce(key, values, context);

        // TODO: find ten best images (lowest distance) and save them as result.
        TreeMap<Long, String> bestImages = new TreeMap<Long, String>();

        // iterate over all
        for (Text value : values) {

            // value contains filePath and distance
            String v[] = value.toString().split("\t");
            String filePath = v[0];
            Long distance = Long.parseLong(v[1]);

            bestImages.put(distance, filePath);

            // TODO configure as parameter
            if (bestImages.size() > 10) {
                bestImages.remove(bestImages.lastKey());
            }
        }

        // write only the ten best as output
        for (Map.Entry<Long, String> image : bestImages.entrySet()) {
            context.write(new Text(image.getKey().toString()), new Text(image.getValue()));
        }
    }
}
