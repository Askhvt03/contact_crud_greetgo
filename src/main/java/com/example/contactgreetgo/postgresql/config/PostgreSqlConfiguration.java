package com.example.contactgreetgo.postgresql.config;

import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.contactgreetgo.postgresql.repository",
        entityManagerFactoryRef = "postgreSqlEntityManagerFactory",
        transactionManagerRef = "postgreSqlTransactionManager"
)
public class PostgreSqlConfiguration {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "postgreSqlDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();

        dataSource.setUrl(env.getProperty("spring.datasource.postgresql.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.postgresql.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.postgresql.password"));

        return dataSource;
    }

    @Primary
    @Bean(name = "postgreSqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgreSqlDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.example.contactgreetgo.postgresql.entity")
                .build();
    }

    @Primary
    @Bean(name = "postgreSqlTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("postgreSqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "postgreSqlLiquibaseProperties")
    @ConfigurationProperties(prefix = "datasource.postgresql.liquibase")
    public LiquibaseProperties liquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Primary
    @Bean(name = "postgreSqlLiquibase")
    public SpringLiquibase liquibase(
            @Qualifier("postgreSqlDataSource") DataSource dataSource,
            @Qualifier("postgreSqlLiquibaseProperties") LiquibaseProperties properties) {
        return setSpringLiquibase(dataSource, properties);
    }

    private SpringLiquibase setSpringLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabelFilter(properties.getLabelFilter());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());

        return liquibase;
    }
}
