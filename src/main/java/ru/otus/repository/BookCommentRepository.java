package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.domain.BookComment;

public interface BookCommentRepository extends ReactiveMongoRepository<BookComment, String> {
}
