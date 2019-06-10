package ru.otus.config;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import ru.otus.repository.BookRepository;

@Configuration
    @EnableReactiveMongoRepositories(basePackageClasses = BookRepository.class)
    public class MongoConfig extends AbstractReactiveMongoConfiguration {


        @Override
        protected String getDatabaseName() {
            return "library";
        }

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create();
    }

    @Bean
        public ReactiveMongoTemplate reactiveMongoTemplate() {
            return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
        }
    }

