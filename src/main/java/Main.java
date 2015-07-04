import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.MultiFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import org.apache.commons.logging.LogFactory;

/**
 * Created by mario on 03.07.15.
 */

public class Main
{
    public static void main(String[] args) throws Exception
    {


        Configuration   conf    = new Configuration();
        Job             job     = new Job(conf, "extractFeatures");

        job.setJarByClass(Main.class);

        job.setMapperClass(ImageFeatureExtractMapper.class);
        job.setReducerClass(ImageFeatureExtractReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(ImageInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        //get the FileSystem, you will need to initialize it properly
        FileSystem fs= FileSystem.get(conf);

        //get the FileStatus list from given dir
        FileStatus[] statusList = fs.listStatus(new Path("/images/png2"));
        if(statusList != null)
        {
            for(FileStatus status : statusList)
            {
                if (status.isDir())
                {
                    //add each dir to the list of inputs for the map-reduce job
                    FileInputFormat.addInputPath(job, status.getPath());

                    // HACK --> just process first folder to prevent errors.
                    break;
                }
            }
        }

        FileOutputFormat.setOutputPath(job, new Path("/index"));

        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}
