package ru.otus.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import ru.otus.instance_service.*;
import ru.otus.repository.BookCommentRepository;
import ru.otus.repository.BookRepository;

import java.util.HashMap;
import java.util.Map;


@Service
public class ShellInputMatcherImpl implements ShellInputMatcher {
    private final Map<String,CreateUpdateServise> services = new HashMap<>();
    private final Map<String,MongoRepository> repostoryList = new HashMap<>();


    @Autowired
    public ShellInputMatcherImpl(BookCUService bookCUService, BookCommentCUService bookCommentCUService
            ,  BookRepository bookRepository, BookCommentRepository bookCommentRepository) {
        services.put("book",bookCUService);
        services.put("bookcomment",bookCommentCUService);
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
    public MongoRepository getRepository(String instanceType) {
        MongoRepository dao = repostoryList.get(instanceType);

        if (dao == null) {
            throw new IllegalArgumentException("Invalid instance type: "
                    + instanceType);
        }
        return dao;
    }

}

