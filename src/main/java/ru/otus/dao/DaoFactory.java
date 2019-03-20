package ru.otus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DaoFactory {
    private final Map<String, GenericDao> daoMap;

    @Autowired
    public DaoFactory(Map<String, GenericDao> daoMap) {
        this.daoMap = daoMap;
    }

    public GenericDao getAuthorDao(){return daoMap.get("authorDaoJDBCImpl");}

    public GenericDao getGenreDao(){return daoMap.get("genreDaoJDBCImpl");}

    public GenericDao getBookDao(){return daoMap.get("bookDaoJDBCImpl");}

    public GenericDao getDaoByEntityName(String entityName){
        return daoMap.entrySet()
                .stream()
                .filter(e -> e.getKey().contains(entityName.toLowerCase()))
                .findFirst()
                .get()
                .getValue();


    }


}
