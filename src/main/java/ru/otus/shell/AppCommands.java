package ru.otus.shell;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.instance_service.CreateUpdateServise;
import ru.otus.ioservice.IOService;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

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
        MongoRepository mongoRepository = shellInputMatcher.getRepository(entityName);
        return mongoRepository.save(cuService.create()).toString();
    }

    @ShellMethod("update instance")
    public String update(String entityName) {
        CreateUpdateServise cuService = shellInputMatcher.getServise(entityName);
        MongoRepository mongoRepository = shellInputMatcher.getRepository(entityName);
        mongoRepository.save(cuService.update());
        return "Instance updated.";
    }

    @ShellMethod("delete instance")
    public String delete(String entityName) {
        MongoRepository mongoRepository = shellInputMatcher.getRepository(entityName);
        try {
            mongoRepository.delete(mongoRepository.findById(ioService.userInput("Enter ID: "))
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
        MongoRepository mongoRepository = shellInputMatcher.getRepository(entityName);
        try {
            return mongoRepository.findById(ioService.userInput("Enter ID: "))
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
        MongoRepository mongoRepository = shellInputMatcher.getRepository(entityName);
        return mongoRepository.findAll().toString();
    }

    @ShellMethod("count total entities")
    public String count(String entityName) {
        MongoRepository mongoRepository = shellInputMatcher.getRepository(entityName);
        return "Total count of '" + entityName + "' : " + mongoRepository.count();
    }



    @ShellMethod(value = "This command returns list of books by given author",
            group = "Reports")
    public String getBooksByAuthor(){
        BookRepository bookRepo = (BookRepository) shellInputMatcher.getRepository("book");
        AuthorRepository authorRepo = (AuthorRepository) shellInputMatcher.getRepository("author");
        return bookRepo.findBooksByAuthor(
                authorRepo.findById(ioService.userInput("Enter author's ID: ")).get()).toString();

    }

    @ShellMethod(value = "This command returns list of books by given genre",
            group = "Reports")
    public String getBooksByGenre(){
        BookRepository bookRepo = (BookRepository) shellInputMatcher.getRepository("book");
        GenreRepository genreRepo = (GenreRepository) shellInputMatcher.getRepository("genre");
        return bookRepo.findBooksByGenre(
                genreRepo.findById(ioService.userInput("Enter author's ID: ")).get()).toString();
    }

    @ShellMethod(value = "This command returns list of author by given genre name",
            group = "Reports")
    public String getAuthorsByGenreName(){
        BookRepository bookRepo = (BookRepository) shellInputMatcher.getRepository("book");
        return bookRepo.findAuthorByGenreGenreName(ioService.userInput("Enter genre's name: ")).toString();
    }

}

