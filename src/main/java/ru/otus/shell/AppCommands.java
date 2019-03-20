package ru.otus.shell;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.dao.DaoFactory;
import ru.otus.dao.GenericDao;

import java.util.Map;

@ShellComponent
public class AppCommands {
    //private final Map<String, GenericDao> daoMap;

    @Autowired
    public AppCommands(DaoFactory daoFactory) {
      //  this.daoMap = daoMap;
    }


    @ShellMethod("print dao beans")
    public void create(){
//        long i = 0;
//        for (Map.Entry<String, GenericDao> pair : daoMap.entrySet()) {
//            System.out.println(pair.getKey()+" - " +pair.getValue());
//        }

    }
    @ShellMethod("print dao by entityname")
    private GenericDao getDao(String entityName){

    }
}
