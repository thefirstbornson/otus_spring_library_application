package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.BookComment;

public interface BookCommentRepository extends MongoRepository<BookComment, String> {
}
