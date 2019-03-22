package ru.otus.shell;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.dao.GenericDao;
import ru.otus.instance_service.CreateUpdateServise;
import ru.otus.ioservice.IOService;

@ShellComponent
public class AppCommands {
    private final ShellInputMatcher shellInputMatcher;
    private final IOService ioService;

    @Autowired
    public AppCommands(ShellInputMatcher shellInputMatcher, IOService ioService) {
        this.shellInputMatcher = shellInputMatcher;
        this.ioService = ioService;
    }


    @ShellMethod("create and save instance")
    public void create(String entityName){
        CreateUpdateServise cuService =shellInputMatcher.getServise(entityName);
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        ioService.showText(genericDao.save(cuService.create())+"\n");
    }

    @ShellMethod("update instance")
    public void update(String entityName){
        CreateUpdateServise cuService =shellInputMatcher.getServise(entityName);
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        genericDao.update(cuService.update());
        ioService.showText("Instance updated."+"\n");
    }

    @ShellMethod("delete instance")
    public void delete(String entityName){
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        try {
            genericDao.delete(genericDao.findById(Long.parseLong(ioService.userInput("Enter ID: "))));
            ioService.showText("Instance deleted."+"\n");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @ShellMethod("show instance")
    public void show(String entityName){
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        ioService.showText(genericDao.findById(Long.parseLong(ioService.userInput("Enter ID: ")))+"\n");
    }

    @ShellMethod("show all instances")
    public void showAll(String entityName){
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        ioService.showText(genericDao.findAll()+ "\n");
    }

    @ShellMethod("count total entities")
    public void count(String entityName){
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        ioService.showText("Total count of '" + entityName +"' : " + genericDao.getCount()+ "\n");
    }
}

