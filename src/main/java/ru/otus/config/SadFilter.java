package ru.otus.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.domain.Book;

import java.util.List;

    @MessagingGateway
    public interface SadFilter {
        @Gateway(requestChannel = "bookDramaChannel")
        void bookDrama(List<Book> books);
    }

