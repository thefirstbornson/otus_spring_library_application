package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@EnableIntegration
@IntegrationComponentScan
@Configuration
public class IntegrationConfig {

    private final static long BOOK_GENRE_ID = 2;



    @Bean (name = PollerMetadata.DEFAULT_POLLER )
    public PollerMetadata poller () {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get() ;
    }

    @Bean
    public PublishSubscribeChannel upperCaseBookOutputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public QueueChannel inputBookChannel() {
        return MessageChannels.queue(10).get();
    }


    @Bean
    public IntegrationFlow upperBookFlow() {
        return IntegrationFlows.from("inputBookChannel")
                .split()
                .handle("upperCaseService", "upperBook")
                .aggregate()
                .channel("upperCaseBookOutputChannel")
                .get()
                ;
    }

}
