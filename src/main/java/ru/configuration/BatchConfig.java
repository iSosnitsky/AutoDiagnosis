package ru.configuration;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.dao.entity.Pathology;
import ru.dao.entity.Patient;
import ru.dao.repository.CityRepository;
import ru.dao.repository.PathologyRepository;
import ru.dao.repository.PatientRepository;
import ru.dao.repository.SymptomRepository;
import ru.job.CustomFlowBuilder;
import ru.job.LoadDataJob;
import ru.job.step.CityPartitioner;
import ru.job.step.city.CityReader;
import ru.job.step.city.CityWriter;
import ru.job.step.pathology.PathologyProcessor;
import ru.job.step.pathology.PathologyReader;
import ru.job.step.pathology.PathologyWriter;
import ru.job.step.patient.PatientProcessor;
import ru.job.step.patient.PatientReader;
import ru.job.step.patient.PatientWriter;
import ru.job.step.patient.TriageTasklet;
import ru.service.DataService;
import ru.service.TriageService;

import java.util.Map;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {
    private static final Logger LOG = LoggerFactory.getLogger(LoadDataJob.class);
    private static final String JOB_NAME = "LoadDataJob";

    //Step names;
    private final static String CITY_STEP = "loadCityStep";
    private final static String PATIENTS_STEP = "loadPatientsStep";
    private final static String TRIAGE_STEP = "triageStep";
    private final static String PATIENTS_PARTITION_STEP = "loadPatientsPartitionStep";
    private final static String TRIAGE_PARTITION_STEP = "triagePartitionStep";
    private final static String PATHOLOGIES_STEP = "loadPathologiesStep";

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;

    private final CityRepository cityRepository;
    private final PatientRepository patientRepository;
    private final DataService dataService;
    private final SymptomRepository symptomRepository;
    private final PathologyRepository pathologyRepository;
    private final ObjectProvider<TriageService> triageService;

    @Bean
    @StepScope
    public PathologyReader pathologyReader(){
        return new PathologyReader(dataService);
    }

    @Bean
    public PathologyProcessor pathologyProcessor(){
        return new PathologyProcessor(symptomRepository,pathologyRepository,dataService);
    }

    @Bean
    public PathologyWriter pathologyWriter(){
        return new PathologyWriter(pathologyRepository);
    }

    @Bean
    public Step loadPathologiesStep(){
        return steps.get(PATHOLOGIES_STEP).<String, Pathology>chunk(20)
                .reader(pathologyReader())
                .processor(pathologyProcessor())
                .writer(pathologyWriter())
                .build();
    }

    @Bean
    public CityPartitioner cityPartitioner(){
        return new CityPartitioner(dataService);
    }

    @Bean
    @StepScope
    public PatientReader patientReader(@Value("#{stepExecutionContext[city]}") String city){
        return new PatientReader(cityRepository,patientRepository, dataService, city);
    }

    @Bean
    @StepScope
    public TriageTasklet triageTasklet(@Value("#{stepExecutionContext[city]}") String city) {
        return new TriageTasklet(triageService.getIfAvailable(), city);
    }

    @Bean
    public PatientProcessor patientProcessor(){
        return new PatientProcessor(dataService,symptomRepository,pathologyRepository);
    }

    @Bean
    public PatientWriter patientWriter(){
        return new PatientWriter(patientRepository);
    }

    private TaskExecutor cityTaskExecutor(String prefix){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setThreadNamePrefix(prefix);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Bean
    public Step loadPatientsPartitionStep(){
        return steps.get(PATIENTS_PARTITION_STEP)
                .partitioner(PATIENTS_STEP,cityPartitioner())
                .step(loadPatientsStep())
                .taskExecutor(cityTaskExecutor("city-executor"))
                .build();
    }

    @Bean
    public Step triagePartitionStep(){
        return steps.get(TRIAGE_PARTITION_STEP)
                .partitioner(TRIAGE_STEP, cityPartitioner())
                .step(triageStep())
                .taskExecutor(cityTaskExecutor("triage-executor"))
                .build();
    }

    @Bean
    public Step loadPatientsStep(){
        return steps.get(PATIENTS_STEP).<Patient, Patient>chunk(20)
                .reader(patientReader(null))
                .processor(patientProcessor())
                .writer(patientWriter())
                .build();
    }

    @Bean
    public Step triageStep() {
        return steps.get(TRIAGE_STEP)
                .tasklet(triageTasklet(null))
                .build();
    }

    @Bean
    @StepScope
    public CityReader cityReader(){
        return new CityReader(dataService,cityRepository);
    }

    @Bean
    public CityWriter cityWriter(){
        return new CityWriter(cityRepository);
    }

    @Bean
    public Step loadCitiesStep(){
        return steps.get(CITY_STEP).<Map<String,String>, Map<String,String>>chunk(20)
                .reader(cityReader())
                .writer(cityWriter())
                .build();
    }

    @Bean
    public Job loadDataJob() {
        Flow flow = new CustomFlowBuilder("dataFlow")
                .addStep(CITY_STEP, loadCitiesStep())
                .addStep(PATHOLOGIES_STEP, loadPathologiesStep())
                .addStep(PATIENTS_STEP, loadPatientsPartitionStep())
                .addStep(TRIAGE_STEP, triagePartitionStep())
                .build();
        return jobs.get(JOB_NAME)
                .start(flow)
                .end()
                .build();
    }
}
