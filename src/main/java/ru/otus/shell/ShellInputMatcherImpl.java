package ru.otus.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenericDao;
import ru.otus.dao.GenreDao;
import ru.otus.instance_service.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShellInputMatcherImpl implements ShellInputMatcher {
    private final Map<String,CreateUpdateServise> services = new HashMap<>();
    private final Map<String,GenericDao> daoList = new HashMap<>();

    @Autowired
    public ShellInputMatcherImpl(AuthorCUService authorCUService, GenreCUService genreCUService
            , BookCUService bookCUService, AuthorDao authorDao, GenreDao genreDao, BookDao bookDao) {
        services.put("author",authorCUService);
        services.put("genre",genreCUService);
        services.put("book",bookCUService);
        daoList.put("author",authorDao);
        daoList.put("genre", genreDao);
        daoList.put("book", bookDao);
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
    public GenericDao getDao(String instanceType) {
        GenericDao dao = daoList.get(instanceType);

        if (dao == null) {
            throw new IllegalArgumentException("Invalid instance type: "
                    + instanceType);
        }
        return dao;
    }
}

