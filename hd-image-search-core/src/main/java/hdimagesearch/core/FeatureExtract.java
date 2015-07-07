package hdimagesearch.core;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Created by mario on 03.07.15.
 */

public class FeatureExtract
{

    private static final String IMAGES_PATH = "/images/png2";
    private static final String OUTPUT_PATH = "/index";

    /**
     *
     * @param args hdimagesearch.core.FeatureExtract /imagesFolderPath /outputFolderPath
     *             hdimagesearch.core.FeatureExtract
     * @throws Exception
     */
    public static int main(String[] args) throws Exception
    {
        String imagesPath = IMAGES_PATH;
        String outputPath = OUTPUT_PATH;

        if (args.length == 2) {
            imagesPath = args[0];
            outputPath = args[1];
        }

        if (args.length != 0 && args.length != 2) {
            System.out.println("Wrong parameter numbers: " + args.length);
            System.out.println("Called with 'hadoop hdimagesearch.core.FeatureExtract imagesFolderPath outputFolderPath'");
            System.exit(1);
        }

        Job job = createJob(imagesPath, outputPath);

        boolean result = job.waitForCompletion(true);

        return result ? 0 : 1;
    }

    private static Job createJob (String imagesPath, String outputPath) throws Exception
    {
        Configuration   conf    = new Configuration();
        conf.set("fs.default.name",     "hdfs://localhost:54310");
        conf.set("mapred.job.tracker",  "localhost:54311");
        conf.set("fs.hdfs.impl",        org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl",        org.apache.hadoop.fs.LocalFileSystem.class.getName());

        Job             job     = Job.getInstance(conf, "extractFeatures");

        job.setJarByClass(FeatureExtract.class);

        job.setMapperClass(ImageFeatureExtractMapper.class);
        job.setCombinerClass(ImageFeatureExtractReducer.class);
        job.setReducerClass(ImageFeatureExtractReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setInputFormatClass(WholeFileInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        //get the FileSystem, you will need to initialize it properly
        FileSystem fs= FileSystem.get(conf);

        //get the FileStatus list from given dir
        FileStatus[] statusList = fs.listStatus(new Path(imagesPath));
        if(statusList != null)
        {
            for(FileStatus status : statusList)
            {
                //add each dir to the list of inputs for the map-reduce job
                FileInputFormat.addInputPath(job, status.getPath());

                // HACK --> just process first folder to prevent errors.
                // break;
            }
        }

        Path out = new Path(outputPath);
        FileOutputFormat.setOutputPath(job, out);
        fs.delete(out, true);

        return job;
    }
}
