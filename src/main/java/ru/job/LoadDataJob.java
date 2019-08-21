package ru.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.service.DataService;

import static ru.job.StepExecutionDecider.EXCLUDED_STEPS_PARAM;
import static ru.job.StepExecutionDecider.INCLUDED_STEPS_PARAM;

@DisallowConcurrentExecution
public class LoadDataJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(LoadDataJob.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private org.springframework.batch.core.Job loadDataJob;

    private static final String JOB_NAME = "LoadDataJob";

    @Autowired
    DataService dataService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        LOG.info("Started");

        StopWatch watch = new StopWatch();
        watch.start();

        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString(INCLUDED_STEPS_PARAM, context.getMergedJobDataMap().getString(INCLUDED_STEPS_PARAM))
                .addString(EXCLUDED_STEPS_PARAM, context.getMergedJobDataMap().getString(EXCLUDED_STEPS_PARAM))
                .toJobParameters();
        try {
            jobLauncher.run(loadDataJob, params);
        } catch (JobExecutionAlreadyRunningException e) {
            LOG.error("Job already in running state", e);
        } catch (JobRestartException e) {
            LOG.error("Error in restarting job", e);
        } catch (JobInstanceAlreadyCompleteException e) {
            LOG.error("Job already complete", e);
        } catch (JobParametersInvalidException e) {
            LOG.error("Job parameters invalid", e);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            watch.stop();
            LOG.info("Finished in {}ms", watch.getTotalTimeMillis());
        }
    }
}
