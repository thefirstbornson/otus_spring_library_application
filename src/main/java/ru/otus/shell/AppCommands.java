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
    public String create(String entityName){
        CreateUpdateServise cuService =shellInputMatcher.getServise(entityName);
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        return genericDao.save(cuService.create()).toString();
    }

    @ShellMethod("update instance")
    public String update(String entityName){
        CreateUpdateServise cuService =shellInputMatcher.getServise(entityName);
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        genericDao.update(cuService.update());
        return "Instance updated.";
    }

    @ShellMethod("delete instance")
    public String delete(String entityName){
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        try {
            genericDao.delete(genericDao.findById(Long.parseLong(ioService.userInput("Enter ID: "))));
            return "Instance deleted.";
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "Instance deletion failed.";
    }

    @ShellMethod("show instance")
    public String show(String entityName){
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        try {
            return genericDao.findById(Long.parseLong(ioService.userInput("Enter ID: "))).toString();
        }catch (NullPointerException e){
            ioService.showText(String.format("There is no %s with such ID"+"\n",entityName));
        }
        return null ;
    }

    @ShellMethod("show all instances")
    public String showAll(String entityName){
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
        return genericDao.findAll().toString();
    }

    @ShellMethod("count total entities")
    public String count(String entityName){
        GenericDao genericDao = shellInputMatcher.getDao(entityName);
       return "Total count of '" + entityName +"' : " + genericDao.getCount();
    }
}

