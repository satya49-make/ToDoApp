package com.sagacious.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class MongoConfig {


    @Bean
    public MongoClient mongoClient() {
        // Replace <username>, <password>, and <cluster-url> with your Atlas details
        ConnectionString connectionString = new ConnectionString(
                "mongodb://localhost:27017"
        );
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

}