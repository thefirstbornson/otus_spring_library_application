package ru.otus.shell;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.dao.DaoFactory;
import ru.otus.dao.GenericDao;

@ShellComponent
public class AppCommands {
    private final DaoFactory daoFactory;

    @Autowired
    public AppCommands(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @ShellMethod("print dao beans")
    public void create(String entityName){
        System.out.println(getDao(entityName));
//        long i = 0;
//        for (Map.Entry<String, GenericDao> pair : daoMap.entrySet()) {
//            System.out.println(pair.getKey()+" - " +pair.getValue());
//        }

    }

    private GenericDao getDao(String entityName){
       return daoFactory.getDaoByEntityName(entityName);

    }
}
