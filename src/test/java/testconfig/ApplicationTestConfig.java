package testconfig;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationTestConfig {

    private static final String CHANGELOGS_PACKAGE = "ru.otus.testchangelogs";

    @Bean
    public Mongock mongock(MongoTestProps mongoProps, MongoClient mongoClient) {
        return new SpringMongockBuilder((com.mongodb.MongoClient) mongoClient, mongoProps.getDatabase(), CHANGELOGS_PACKAGE)
                .setLockQuickConfig()
                .build();
    }
}