package ru.job;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class StepExecutionDecider implements JobExecutionDecider {

    public static final String INCLUDED_STEPS_PARAM = "includedSteps";
    public static final String EXCLUDED_STEPS_PARAM = "excludedSteps";

    public static final String SKIP_STATUS = "SKIP";
    public static final String CONTINUE_STATUS = "CONTINUE";

    private static final FlowExecutionStatus SKIP = new FlowExecutionStatus(SKIP_STATUS);
    private static final FlowExecutionStatus CONTINUE = new FlowExecutionStatus(CONTINUE_STATUS);

    private final String stepName;

    public StepExecutionDecider(String stepName) {
        this.stepName = stepName;
    }

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        JobParameters jobParameters = jobExecution.getJobParameters();
        if (!needExecute(jobParameters)) {
            return SKIP;
        }
        return CONTINUE;
    }

    private boolean needExecute(JobParameters jobParameters) {
        String includedStepsParam = jobParameters.getString(INCLUDED_STEPS_PARAM);
        String excludedStepsParam = jobParameters.getString(EXCLUDED_STEPS_PARAM);

        if (StringUtils.isNotEmpty(includedStepsParam)) {
            String[] includedStepNames = includedStepsParam.split(",");
            return ArrayUtils.contains(includedStepNames, stepName);
        }

        if (StringUtils.isNotEmpty(excludedStepsParam)) {
            String[] excludedStepNames = excludedStepsParam.split(",");
            return !ArrayUtils.contains(excludedStepNames, stepName);
        }

        return true;
    }
}
