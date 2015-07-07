package hdimagesearch.core;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
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
    public static String SAMPLE_HISTOGRAM_KEY       = "sampleHistogram";
    public static String SAMPLE_FILEPATH_KEY        = "sampleFilepath";
    public static String NUM_RESULTS_KEY            = "numResults";


    private static final String INDEX_FOLDER_PATH   = "/index";
    private static final String OUTPUT_PATH         = "/searchResult";
    private static final int NUM_RESULTS            = 10;
    private static final String SAMPLE_FILE         = "/images/png2/1/1_i110.png";

    /**
     * search by hadoop image:  hdimagesearch.core.ImageSearch /indexFolderPath /outputFolderPath -h "/images/png2/1/1_i110.png" -n 10
     * search by feature:       hdimagesearch.core.ImageSearch /indexFolderPath /outputFolderPath -n 10 -f "1.0;3.0:1.0;0.0;....."
     * @param args
     * @throws Exception
     */
    public static int main(String[] args) throws Exception
    {
        String              indexFolderPath = INDEX_FOLDER_PATH;
        String              outputPath      = OUTPUT_PATH;
        String              sampleFile      = SAMPLE_FILE;
        Path                samplePath      = new Path(sampleFile);
        Configuration       conf            = new Configuration();
        ImageFeature        sampleFeature   = null;
        int                 numResults      = NUM_RESULTS;

        conf.set("fs.default.name",     "hdfs://localhost:54310");
        conf.set("mapred.job.tracker",  "localhost:54311");
        conf.set("fs.hdfs.impl",        org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl",        org.apache.hadoop.fs.LocalFileSystem.class.getName());

        // parse arguments
        try {
            if (args.length >= 2) {
                indexFolderPath = args[0];
                outputPath = args[1];
            }

            for (int i = 2; i < args.length; i++) {
                if (args[i].equals("-h")) {

                    sampleFile = args[i + 1];
                    samplePath = new Path(sampleFile);
                    byte[] sampleBytes = loadImage(samplePath, conf);

                    // create histogram of sample image
                    HistogramGenerator histogramGenerator = new HistogramGenerator();
                    sampleFeature = new ImageFeature(sampleFile, histogramGenerator.generate(sampleBytes));

                } else if (args[i].equals("-f")) {

                    // we do not know the filepath on the cluster as we only have the histogram values
                    sampleFeature = new ImageFeature("", args[i + 1]);

                } else if (args[i].equals("-n")) {

                    numResults = Integer.valueOf(args[i + 1]);

                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing parameter arguments:");
            e.printStackTrace();

            System.out.println("Called with 'hadoop hdimagesearch.core.ImageSearch /indexFolderPath /outputFolderPath -h /pathToImageOnCluster [-n NumResults]'");
            System.out.println("Or called with 'hadoop hdimagesearch.core.ImageSearch /indexFolderPath /outputFolderPath -f \"1.0;3.0:1.0;0.0;.....\" [-n NumResults]'");
            System.exit(1);
        }

        // used for local testing in case of no parameter arguments
        if (sampleFeature == null) {
            byte[] sampleBytes = loadImage(samplePath, conf);

            // create histogram of sample image
            HistogramGenerator histogramGenerator = new HistogramGenerator();
            sampleFeature = new ImageFeature(sampleFile, histogramGenerator.generate(sampleBytes));
        }

        conf.set(SAMPLE_HISTOGRAM_KEY, sampleFeature.getHistogramString());
        conf.set(SAMPLE_FILEPATH_KEY, sampleFeature.getFilePath());
        conf.set(NUM_RESULTS_KEY, String.valueOf(numResults));

        Job             job     = Job.getInstance(conf, "extractFeatures");
        job.setJarByClass(ImageSearch.class);

        // use only one reducer
        job.setNumReduceTasks(1);
        job.setMapperClass(ImageSearchMapper.class);
        job.setReducerClass(ImageSearchReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(indexFolderPath));

        //get the FileSystem
        FileSystem fs = FileSystem.get(conf);

        Path out = new Path(outputPath);
        FileOutputFormat.setOutputPath(job, out);
        fs.delete(out, true);

        boolean result = job.waitForCompletion(true);

        return result ? 0 : 1;
    }

    public static byte[] loadImage (Path path, Configuration conf) throws Exception
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
            fs.close();
        }

        return sampleBytes;
    }
}
