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
    public static void main(String[] args) throws Exception
    {
        Configuration   conf    = new Configuration();
        Job             job     = Job.getInstance(conf, "extractFeatures");

        // this should be like defined in your mapred-site.xml
        conf.set("mapreduce.jobtracker.address", "hdfs://localhost:54311");
        // like defined in core-site.xml
        conf.set("fs.defaultFS", "hdfs://localhost:54310");

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
        FileStatus[] statusList = fs.listStatus(new Path("hdfs://localhost:54310/images/png2"));
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

        Path out = new Path("hdfs://localhost:54310/index");
        FileOutputFormat.setOutputPath(job, out);
        fs.delete(out, true);

        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}
