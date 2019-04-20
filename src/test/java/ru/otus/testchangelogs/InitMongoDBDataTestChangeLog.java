package ru.otus.testchangelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.BookComment;


@ChangeLog(order = "001")
public class InitMongoDBDataTestChangeLog {

    private Author author;
    private Book book;
    private BookComment bookComment;

    @ChangeSet(order = "000", id = "dropDB", author = "artem", runAlways = true)
    public static void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "artem", runAlways = true)
    public void initAuthors(MongoTemplate template){
        template.save(new Author("99","Fedor","Dostoevsky","1821-1881"));
        template.save(new Author("88","Viktor","Pelevin","1962 "));
        template.save(new Author("77","Alexander","Filipenko","1984-"));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "artem", runAlways = true)
    public void initBooks(MongoTemplate template){
        book= new Book("9","Anna Karenina"
                ,"Leo Tolstoy","Drama","Novel");
        template.save( book );
        template.save( new Book("8","Snuff"
                ,"Viktor Pelevin","Sci-fi","Novel")
        );
        template.save( new Book("7","Red Cross"
                ,"Alexander Filipenko","Historical Drama","Novel")
        );

    }

    @ChangeSet(order = "003", id = "initComments", author = "artem", runAlways = true)
    public void initComments(MongoTemplate template){
        template.save(new BookComment("Great novel!",book));

    }
}
