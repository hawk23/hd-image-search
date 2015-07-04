import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

/**
 * Created by mario on 04.07.15.
 */
class ImageInputFormat extends TextInputFormat {

    @Override
    protected boolean isSplitable(JobContext context, Path file) {
        return false;
    }
}
