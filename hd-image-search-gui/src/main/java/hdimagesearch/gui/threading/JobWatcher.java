package hdimagesearch.gui.threading;

import hdimagesearch.gui.controller.LogController;
import org.apache.hadoop.mapreduce.Job;

/**
 * Created by mario on 07.07.15.
 */
public class JobWatcher extends Thread {

    private Job job;
    private LogController logController;
    private long executionTime;

    public JobWatcher(Job job, LogController logController)
    {
        this.job = job;
        this.logController = logController;
    }

    @Override
    public void run()
    {
        long startTime = System.currentTimeMillis();

        try {
            logController.log("Job started: " + job.getJobName());

            while (!job.isComplete()) {
                // TODO: log every 2 seconds the job state without suspending thread.
                // logController.log("Job Progress: " + job.getStatus().getMapProgress() + " Finish time: " + job.getFinishTime());
            }

            long endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
            double exec = Math.round(executionTime/1000.0 * 100) / 100.0;

            logController.log("Job finished: " + job.getJobName() +". duration: " + exec + " [sec]");
        }
        catch (Exception ex) {
            System.err.print(ex);
        }
    }
}
