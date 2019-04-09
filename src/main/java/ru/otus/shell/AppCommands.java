package ru.otus.shell;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.dao.GenericDao;
import ru.otus.instance_service.CreateUpdateServise;
import ru.otus.ioservice.IOService;

import javax.validation.constraints.Null;

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
        JpaRepository jpaRepository = shellInputMatcher.getDao(entityName);
        return jpaRepository.save(cuService.create()).toString();
    }

    @ShellMethod("update instance")
    public String update(String entityName){
        CreateUpdateServise cuService =shellInputMatcher.getServise(entityName);
        JpaRepository jpaRepository = shellInputMatcher.getDao(entityName);
        jpaRepository.save(cuService.update());
        return "Instance updated.";
    }

    @ShellMethod("delete instance")
    public String delete(String entityName){
        JpaRepository jpaRepository = shellInputMatcher.getDao(entityName);
        try {
            jpaRepository.delete(jpaRepository.findById(Long.parseLong(ioService.userInput("Enter ID: ")))
                    .orElseThrow(NullPointerException::new));
            return "Instance deleted.";
        } catch (NullPointerException e) {
            ioService.showText(String.format("There is no %s with such ID"+"\n",entityName));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "Instance deletion failed.";
    }

    @ShellMethod("show instance")
    public String show(String entityName){
        JpaRepository jpaRepository= shellInputMatcher.getDao(entityName);
        try {
            return jpaRepository.findById(Long.parseLong(ioService.userInput("Enter ID: ")))
                    .orElseThrow(NullPointerException::new).toString();
        }catch (NullPointerException e){
            ioService.showText(String.format("There is no %s with such ID"+"\n",entityName));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null ;
    }

    @ShellMethod("show all instances")
    public String showAll(String entityName){
        JpaRepository genericDao = shellInputMatcher.getDao(entityName);
        return genericDao.findAll().toString();
    }

    @ShellMethod("count total entities")
    public String count(String entityName){
        JpaRepository jpaRepository= shellInputMatcher.getDao(entityName);
       return "Total count of '" + entityName +"' : " + jpaRepository.count();
    }
}

