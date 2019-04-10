package ru.otus.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.otus.dao.*;
import ru.otus.instance_service.*;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookCommentRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class ShellInputMatcherImpl implements ShellInputMatcher {
    private final Map<String,CreateUpdateServise> services = new HashMap<>();
    private final Map<String,JpaRepository> repostoryList = new HashMap<>();


    @Autowired
    public ShellInputMatcherImpl(AuthorCUService authorCUService, GenreCUService genreCUService
            , BookCUService bookCUService, BookCommentCUService bookCommentCUService
            , AuthorRepository authorRepository, GenreRepository genreRepository
            , BookRepository bookRepository, BookCommentRepository bookCommentRepository) {
        services.put("author",authorCUService);
        services.put("genre",genreCUService);
        services.put("book",bookCUService);
        services.put("bookcomment",bookCommentCUService);
        repostoryList.put("author",authorRepository);
        repostoryList.put("genre", genreRepository);
        repostoryList.put("book", bookRepository);
        repostoryList.put("bookcomment", bookCommentRepository);
    }

    public CreateUpdateServise getServise(String instanceType) {
        CreateUpdateServise service = services.get(instanceType);

        if (service == null) {
            throw new IllegalArgumentException("Invalid instance type: "
                    + instanceType);
        }
        return service;
    }

    @Override
    public JpaRepository getRepository(String instanceType) {
        JpaRepository dao = repostoryList.get(instanceType);

        if (dao == null) {
            throw new IllegalArgumentException("Invalid instance type: "
                    + instanceType);
        }
        return dao;
    }

}

