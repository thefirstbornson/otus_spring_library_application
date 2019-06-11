package testchangelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Book;
import ru.otus.domain.BookComment;

@ChangeLog(order = "001")
public class InitMongDBTest {
    private Book book;
    private BookComment bookComment;

    @ChangeSet(order = "000", id = "dropDB", author = "artem", runAlways = true)
    public static void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "artem", runAlways = true)
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

    @ChangeSet(order = "002", id = "initComments", author = "artem", runAlways = true)
    public void initComments(MongoTemplate template){
        template.save(new BookComment("Great novel!",book.getId()));

    }

}
