package ru;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.SQLException;

@SpringBootApplication
@EnableJpaRepositories(
        basePackages = {"ru"}, repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class
)
@EnableScheduling
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

//        AbstractApplicationContext context = new ClassPathXmlApplicationContext("job.xml");
        SpringApplication.run(Application.class);

        //Don't remove, otherwise Machine spirit might get angry
        logger.info("Machine spirit appeased");
    }

    @Bean(initMethod = "start",destroyMethod = "stop")
    public Server h2server() throws SQLException{
        return Server.createTcpServer("-tcp","-tcpAllowOthers","-tcpPort","9092");
    }

}

