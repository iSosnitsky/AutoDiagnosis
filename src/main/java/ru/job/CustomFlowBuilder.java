package ru.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;

import java.util.ArrayList;
import java.util.List;

import static ru.job.StepExecutionDecider.CONTINUE_STATUS;
import static ru.job.StepExecutionDecider.SKIP_STATUS;

public class CustomFlowBuilder {
    private final String flowName;
    private List<StepInfo> steps = new ArrayList<>();

    public CustomFlowBuilder(String flowName) {
        this.flowName = flowName;
    }

    public CustomFlowBuilder addStep(String stepName, Step step) {
        steps.add(new StepInfo(step, stepName));
        return this;
    }

    public Flow build() {
        FlowBuilder<Flow> builder = new FlowBuilder<>(flowName);
        for (int i = 0; i < steps.size() - 1; i++) {
            StepInfo current = steps.get(i);
            StepInfo next = steps.get(i + 1);
            builder = builder.from(current.getDecider()).on(CONTINUE_STATUS).to(current.getStep())
                    .from(current.getStep()).on("*").to(next.getDecider())
                    .from(current.getDecider()).on(SKIP_STATUS).to(next.getDecider());
        }
        StepInfo last = steps.get(steps.size() - 1);
        builder = builder.from(last.getDecider()).on(CONTINUE_STATUS).to(last.getStep())
                .from(last.getDecider()).on(SKIP_STATUS).end();
        return builder.build();
    }

    private static class StepInfo {
        private final Step step;
        private final StepExecutionDecider decider;

        StepInfo(Step step, String stepName) {
            this.step = step;
            this.decider = new StepExecutionDecider(stepName);
        }

        Step getStep() {
            return step;
        }

        StepExecutionDecider getDecider() {
            return decider;
        }
    }
}
