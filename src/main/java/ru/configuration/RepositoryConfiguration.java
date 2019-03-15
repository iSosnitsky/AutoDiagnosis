package ru.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import ru.dao.entity.Medicine;
import ru.dao.entity.Pathology;
import ru.dao.entity.Symptom;

@Configuration
public class RepositoryConfiguration extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Medicine.class);
        config.exposeIdsFor(Pathology.class);
        config.exposeIdsFor(Symptom.class);
        super.configureRepositoryRestConfiguration(config);
    }
}
