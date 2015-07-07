package hdimagesearch.gui.threading;

import hdimagesearch.gui.controller.LogController;
import org.apache.hadoop.mapreduce.Job;

/**
 * Created by mario on 07.07.15.
 */
public class JobWatcher extends Thread {

    private Job job;
    private LogController logController;

    public JobWatcher(Job job, LogController logController)
    {
        this.job = job;
        this.logController = logController;
    }

    @Override
    public void run()
    {
        try {
            logController.log("Job started: " + job.getJobName());

            while (!job.isComplete()) {
                logController.log("Job Progress: " + job.getStatus().getMapProgress() + " Finish time: " + job.getFinishTime());
                sleep(5000);
            }

            logController.log("Job finished: " + job.getJobName());
        }
        catch (Exception ex) {
            System.err.print(ex);
        }
    }
}
