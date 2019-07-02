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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
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
    public FlatFileItemWriter writer() {
        return new FlatFileItemWriterBuilder<>()
                .name("genreItemWriter")
                .resource(new FileSystemResource("output/output1.csv"))
                .lineAggregator(new DelimitedLineAggregator<>())
                .build();
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
    public Job LibraryJob(Step step1, Step step2) {
        return jobBuilderFactory.get("importLibraryJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .next(step2)
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
    public Step step1(MongoItemWriter<BookDoc> writer, RepositoryItemReader<Book> reader
            , @Qualifier("bookDocItemProcessor") ItemProcessor itemProcessor) {
        return stepBuilderFactory.get("step1")
                .chunk(5)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListener() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(Object o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) { logger.info("Начало записи"); }
                    public void afterWrite(List list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {logger.info("Начало обработки");}
                    public void afterProcess(Object o, Object o2) {logger.info("Конец обработки");}
                    public void onProcessError(Object o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }
    @Bean
    public Step step2(MongoItemWriter<BookCommentDoc> writer
            , RepositoryItemReader<BookComment> reader, @Qualifier("bookCommentDocItemProcessor") ItemProcessor processor) {
        return stepBuilderFactory.get("step2")
                .chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new ItemReadListener() {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(Object o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) { logger.info("Начало записи"); }
                    public void afterWrite(List list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {logger.info("Начало обработки");}
                    public void afterProcess(Object o, Object o2) {logger.info("Конец обработки");}
                    public void onProcessError(Object o, Exception e) {logger.info("Ошбка обработки");}
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }
}
