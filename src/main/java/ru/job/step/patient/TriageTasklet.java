package ru.job.step.patient;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import ru.service.TriageService;

import java.util.Set;

public class TriageTasklet implements Tasklet {
    private final TriageService triageService;
    private final String cityName;

    public TriageTasklet(TriageService triageService, String cityName) {
        this.triageService = triageService;
        this.cityName = cityName;
    }


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try{
            if (triageService!=null){
                triageService.triageByCity(cityName);
            }
        } catch (Exception e) {
            System.out.println("Exception during triage: "+e.getMessage());
//            e.printStackTrace();
        }

        return RepeatStatus.FINISHED;
    }
}
