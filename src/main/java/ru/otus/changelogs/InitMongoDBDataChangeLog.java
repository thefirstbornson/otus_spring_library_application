package ru.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.BookComment;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author author;
    private Book book;
    private BookComment bookComment;

    @ChangeSet(order = "000", id = "dropDB", author = "artem", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "artem", runAlways = true)
    public void initAuthors(MongoTemplate template){
        template.save(new Author("Leo","Tolstoy","1828-1910"));
        template.save(new Author("Fedor","Dostoevsky","1821-1881"));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "artem", runAlways = true)
    public void initBooks(MongoTemplate template){
        book = new Book("5cb7550147765935d8ff403f","Childhood"
                ,"Leo Tolstoy","Drama","Novel");
        template.save(  book);
        template.save( new Book("5cb7550147767895d8ff40f4","War & Peace"
                ,"Leo Tolstoy","Drama","Novel")
        );
        template.save( new Book("5c27550d47767395d8ff4d33","Anna Karenina"
                ,"Leo Tolstoy","Drama","Novel")
        );
        template.save( new Book("5c27550d47267895d8ff4d33","Sevastopol Sketches"
                ,"Leo Tolstoy","Drama","Novel")
        );
        template.save( new Book("5c27551d47767895d8ff4d33","The Death of Ivan Ilyich"
                ,"Leo Tolstoy","Drama","Novel")
        );
        template.save( new Book("5c27551d4776789588ff4d33","Crime and Punishment"
                ,"Fedor Dostoevsky","Drama","Novel")
        );
        template.save( new Book("5c27551d47767895d8ff4d33","Demons"
                ,"Fedor Dostoevsky","Drama","Novel")
        );
        template.save( new Book("5c27551d47767845d8ff4d34","The Idiot"
                ,"Fedor Dostoevsky","Drama","Novel")
        );
        template.save( new Book("5c27851d47767895d8f74d33","The Brothers Karamazov"
                ,"Fedor Dostoevsky","Drama","Novel")
        );
    }

    @ChangeSet(order = "003", id = "initComments", author = "artem", runAlways = true)
    public void initComments(MongoTemplate template){
        template.save(new BookComment("Great novel!",book));
    }
}
