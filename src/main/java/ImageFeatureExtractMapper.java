import net.semanticmetadata.lire.DocumentBuilderFactory;
import net.semanticmetadata.lire.imageanalysis.CEDD;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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
        String fileName = fileSplit.getPath().getName();

        // generate histogram
        HistogramGenerator histogramGenerator = new HistogramGenerator();
        double[] histogram = histogramGenerator.generate(imageByte);

        // generate ImageFeature
        ImageFeature feature = new ImageFeature(filePath, fileName, histogram);

        String featureAsString = feature.toString();
        System.out.println(featureAsString);

        // write filePath as key and double values of histogram as values
        context.write(new Text(featureAsString.split(" ")[0]), new Text(featureAsString.split(" ")[1]));
    }
}
