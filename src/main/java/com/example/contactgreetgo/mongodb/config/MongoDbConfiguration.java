package com.example.contactgreetgo.mongodb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Objects;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableMongoRepositories(
        basePackages = "com.example.contactgreetgo.mongodb.repository",
        mongoTemplateRef = "mongoDbTemplate"
)
public class MongoDbConfiguration {

    @Autowired
    private Environment env;

    @Bean(name = "mongoDbFactory")
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(
                Objects.requireNonNull(env.getProperty("spring.datasource.mongodb.uri")));
    }

    @Bean(name = "mongoDbTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("mongoDbFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
