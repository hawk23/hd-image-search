import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
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

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setInputFormatClass(KeyValueTextInputFormat.class);
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
                    FileStatus[] imagesStatusList = fs.listStatus(status.getPath());
                    for (FileStatus imageStatus : imagesStatusList)
                    {
                        //add each file to the list of inputs for the map-reduce job
                        FileInputFormat.addInputPath(job, status.getPath());
                    }
                }
            }
        }

        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}
