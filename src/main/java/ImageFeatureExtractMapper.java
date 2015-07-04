import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * Created by mario on 03.07.15.
 */
public class ImageFeatureExtractMapper extends Mapper<NullWritable, BytesWritable, Text, Text> {
    @Override
    protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
        byte[] imageByte = value.getBytes();

        // get filepath of current file
        FileSplit fileSplit = (FileSplit)context.getInputSplit();
        String filePath = fileSplit.getPath().toString();

        // generate histogram
        HistogramGenerator histogramGenerator = new HistogramGenerator();
        double[] histogram = histogramGenerator.generate(imageByte);

        // generate ImageFeature
        ImageFeature feature = new ImageFeature(filePath, histogram);

        System.out.println(feature.getHistogramString());

        // write filePath as key and double values of histogram as values
        context.write(new Text(feature.getFilePath()), new Text(feature.getHistogramString()));
    }
}
