package hdimagesearch.core;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

        TreeMap<Double, List<String>> bestImages = new TreeMap<Double, List<String>>();

        // iterate over all
        for (Text value : values) {

            // value contains filePath and distance
            String v[] = value.toString().split("\t");
            String filePath = v[0];
            Double distance = Double.parseDouble(v[1]);

            if (bestImages.containsKey(distance)) {
                bestImages.get(distance).add(filePath);
            } else {
                List<String> filePathValues = new ArrayList<String>();
                filePathValues.add(filePath);
                bestImages.put(distance, filePathValues);
            }

            int currentResultSize = 0;
            for (List<String> filePathValues : bestImages.values()) {
                currentResultSize += filePathValues.size();
            }

            if (currentResultSize > numResults) {
                // remove last key entry if only one filePathValue else remove one of the filePathValues for the last key
                if (bestImages.lastEntry().getValue().size() == 1) {
                    bestImages.remove(bestImages.lastKey());
                } else {
                    bestImages.get(bestImages.lastKey()).remove(bestImages.get(bestImages.lastKey()).size() - 1);
                }
            }
        }

        // write only the ten best as output
        for (Map.Entry<Double, List<String>> entry : bestImages.entrySet()) {
            for (String filePath : entry.getValue()) {
                context.write(new Text(entry.getKey().toString()), new Text(filePath));
            }
        }
    }
}
