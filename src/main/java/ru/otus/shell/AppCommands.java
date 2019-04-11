package ru.otus.shell;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.Author;
import ru.otus.exception.NoEntityException;
import ru.otus.instance_service.CreateUpdateServise;
import ru.otus.ioservice.IOService;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String create(String entityName) {
        CreateUpdateServise cuService = shellInputMatcher.getServise(entityName);
        JpaRepository jpaRepository = shellInputMatcher.getRepository(entityName);
        return jpaRepository.save(cuService.create()).toString();
    }

    @ShellMethod("update instance")
    public String update(String entityName) {
        CreateUpdateServise cuService = shellInputMatcher.getServise(entityName);
        JpaRepository jpaRepository = shellInputMatcher.getRepository(entityName);
        jpaRepository.save(cuService.update());
        return "Instance updated.";
    }

    @ShellMethod("delete instance")
    public String delete(String entityName) {
        JpaRepository jpaRepository = shellInputMatcher.getRepository(entityName);
        try {
            jpaRepository.delete(jpaRepository.findById(Long.parseLong(ioService.userInput("Enter ID: ")))
                    .orElseThrow(NullPointerException::new));
            return "Instance deleted.";
        } catch (NullPointerException | NumberFormatException e) {
            ioService.showText(String.format("There is no %s with such ID" + "\n", entityName));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "Instance deletion failed.";
    }

    @ShellMethod("show instance")
    public String show(String entityName) {
        JpaRepository jpaRepository = shellInputMatcher.getRepository(entityName);
        try {
            return jpaRepository.findById(Long.parseLong(ioService.userInput("Enter ID: ")))
                    .orElseThrow(NullPointerException::new).toString();
        } catch (NullPointerException | NumberFormatException e) {
            ioService.showText(String.format("There is no %s with such ID" + "\n", entityName));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @ShellMethod("show all instances")
    public String showAll(String entityName, @ShellOption(defaultValue = "") String option) {
        JpaRepository entityRepository = shellInputMatcher.getRepository(entityName);
        if (!option.equals("")) {
            List result = showWithOption(entityRepository, option);
            return result != null ? result.toString() : "";
        }
        return entityRepository.findAll().toString();
    }

    @ShellMethod("count total entities")
    public String count(String entityName) {
        JpaRepository jpaRepository = shellInputMatcher.getRepository(entityName);
        return "Total count of '" + entityName + "' : " + jpaRepository.count();
    }

    private List showWithOption(JpaRepository entityRepo, String option) {
        List<Method> findByOptionMethod  = Arrays.stream(entityRepo.getClass().getDeclaredMethods())
                    .filter(method -> method.getName().toLowerCase().contains(option.toLowerCase()))
                    .collect(Collectors.toList());

        if (findByOptionMethod.size() > 0) {
            JpaRepository optionRepo = shellInputMatcher.getRepository(option);
            try {
                return (List) findByOptionMethod.get(0).invoke(entityRepo
                        , optionRepo.findById(Long.parseLong(ioService.userInput(String.format("Enter %s's ID: ", option))))
                                .orElseThrow(NoEntityException::new));

            } catch (NullPointerException | NumberFormatException | NoEntityException e) {
                ioService.showText(String.format("There is no %s with such ID" + "\n", option));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }else{
            ioService.showText(String.format("There is no such option for this entity: -%s", option));
        }
        return null;
    }
}

