package ru.otus.springbatchmigration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;
import ru.otus.domain.BookComment;
import ru.otus.domainMongo.BookCommentDoc;
import ru.otus.domainMongo.BookDoc;
import ru.otus.repository.BookCommentRepository;
import ru.otus.repository.BookRepository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Configuration
@EnableBatchProcessing
@EnableJpaRepositories(basePackages ={"ru.otus.repository"})
@EntityScan(basePackages = {"ru.otus.domain"})
@ComponentScan(basePackages = {"ru.otus.repository"})
public class BatchConfig extends DefaultBatchConfigurer {
    private final Logger logger = LoggerFactory.getLogger("Batch");


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BookRepository bookRepository;
    private final BookCommentRepository bookCommentRepository;

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory
            , BookRepository bookRepository, BookCommentRepository bookCommentRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.bookRepository = bookRepository;
        this.bookCommentRepository = bookCommentRepository;
    }

    @Override
    @Autowired
    public void setDataSource(@Qualifier("springBatchDataSource") DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Bean
    public RepositoryItemReader<Book> bookRepositoryItemReader() throws Exception {
        return new RepositoryItemReaderBuilder<Book>().repository(bookRepository)
                .methodName("findAll")
                .sorts(new HashMap<>())
                .name("bookreader")
                .build();
    }

    @Bean
    public RepositoryItemReader<BookComment> bookCommentRepositoryItemReader() throws Exception {
        return new RepositoryItemReaderBuilder<BookComment>().repository(bookCommentRepository)
                .methodName("findAll")
                .sorts(new HashMap<>())
                .name("bookcommentreader")
                .build();
    }

    @Bean
    public MongoItemWriter<BookDoc> bookDocWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<BookDoc>()
                .template(mongoTemplate)
                .build();
    }
    @Bean
    public MongoItemWriter<BookCommentDoc> bookCommentDocWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<BookCommentDoc>()
                .template(mongoTemplate)
                .build();
    }
    @Bean
    public ItemProcessor bookDocItemProcessor() {
        return (ItemProcessor<Book, BookDoc>) book -> {
            logger.info("Идет обработка "+book);
            return new BookDoc(book.getName()
                    ,book.getAuthor().getFirstName()+" "+book.getAuthor().getLastName()
                    ,book.getGenre().getGenreName(),"");
        };
    }
    @Bean
    @Transactional
    public ItemProcessor bookCommentDocItemProcessor() {
        return (ItemProcessor<BookComment, BookCommentDoc>) bookcomment -> {
            logger.info("Идет обработка "+bookcomment);
            return new BookCommentDoc(bookcomment.getComment(),
                    bookcomment.getBook());
        };
    }
    @Bean
    public Job LibraryJob(Step moveBooksFromPostgresToMongo, Step moveBookCommentsFromPostgresToMongo) {
        return jobBuilderFactory.get("importLibraryJob")
                .incrementer(new RunIdIncrementer())
                .flow(moveBooksFromPostgresToMongo)
                .next(moveBookCommentsFromPostgresToMongo)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step moveBooksFromPostgresToMongo(MongoItemWriter<BookDoc> writer, RepositoryItemReader<Book> reader
            , @Qualifier("bookDocItemProcessor") ItemProcessor<Book, BookDoc> itemProcessor) {
        return stepBuilderFactory.get("moveBooksFromPostgresToMongo")
                .<Book, BookDoc>chunk(5)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListener<Book>() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(Book o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemWriteListener<BookDoc>(){
                    public void beforeWrite(List list) { logger.info("Начало записи"); }
                    public void afterWrite(List list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
                })
                .listener(new ItemProcessListener<Book, BookDoc>() {
                    public void beforeProcess(Book o) {logger.info("Начало обработки");}
                    public void afterProcess(Book o, BookDoc o2) {logger.info("Конец обработки");}
                    public void onProcessError(Book o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }
    @Bean
    public Step moveBookCommentsFromPostgresToMongo(MongoItemWriter<BookCommentDoc> writer
            , RepositoryItemReader<BookComment> reader
            , @Qualifier("bookCommentDocItemProcessor") ItemProcessor<BookComment, BookCommentDoc> processor) {
        return stepBuilderFactory.get("moveBookCommentsFromPostgresToMongo")
                .<BookComment, BookCommentDoc>chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new ItemReadListener<BookComment>() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(BookComment o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemWriteListener<BookCommentDoc>() {
                    public void beforeWrite(List list) { logger.info("Начало записи"); }
                    public void afterWrite(List list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
                })
                .listener(new ItemProcessListener<BookComment, BookCommentDoc>() {
                    public void beforeProcess(BookComment o) {logger.info("Начало обработки");}
                    public void afterProcess(BookComment o, BookCommentDoc  o2) {logger.info("Конец обработки");}
                    public void onProcessError(BookComment o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }
}
