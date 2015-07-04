import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Created by mario on 04.07.15.
 */
public class ImageSearch
{
    public static String SAMPLE_HISTOGRAM_KEY   = "sampleHistogram";
    public static String SAMPLE_FILEPATH_KEY    = "sampleFilepath";

    public static void main(String[] args) throws Exception
    {
        String              sampleFile  = "/images/png2/1/1_i110.png"; // TODO: read from argmuments
        Path                samplePath  = new Path(sampleFile);
        Configuration       conf        = new Configuration();

        byte[]              sampleBytes = loadImage(samplePath, conf);

        // create histogram of sample image
        HistogramGenerator  histogramGenerator  = new HistogramGenerator();
        ImageFeature        sampleFeature       = new ImageFeature(sampleFile, histogramGenerator.generate(sampleBytes));

        conf.set(SAMPLE_HISTOGRAM_KEY, sampleFeature.getHistogramString());
        conf.set(SAMPLE_FILEPATH_KEY, sampleFeature.getFilePath());

        Job             job     = Job.getInstance(conf, "extractFeatures");
        job.setJarByClass(ImageSearch.class);

        job.setMapperClass(ImageSearchMapper.class);
        job.setCombinerClass(ImageSearchReducer.class);
        job.setReducerClass(ImageSearchReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path("/index"));
        FileOutputFormat.setOutputPath(job, new Path("/searchResult"));

        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }

    private static byte[] loadImage (Path path, Configuration conf) throws Exception
    {
        //get the FileSystem, you will need to initialize it properly
        FileSystem          fs          = FileSystem.get(conf);
        FSDataInputStream   in          = null;

        int                 fileLength  = (int) fs.getFileStatus(path).getLen();
        byte[]              sampleBytes = new byte[fileLength];

        try
        {
            in              = fs.open(path);
            IOUtils.readFully(in, sampleBytes, 0, fileLength);
        }
        finally
        {
            IOUtils.closeStream(in);
        }

        return sampleBytes;
    }
}
