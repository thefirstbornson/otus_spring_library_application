package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.domain.Book;

@Configuration
public class IntegrationConfig {

//    @Bean
//    @ServiceActivator(inputChannel = "bookDramaChannel", outputChannel = "outputChannel")
//    public String getDramaBooks(String name) {
//        return String.format("Hello, %s!", name);
//    }

    @Bean
    public QueueChannel bookDramaChannel() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow bookDramaFlow() {
        return flow -> flow.split()
                .filter((Book book )-> book.getId()==1)
                .channel("bookDramaChannel");
    }
}
