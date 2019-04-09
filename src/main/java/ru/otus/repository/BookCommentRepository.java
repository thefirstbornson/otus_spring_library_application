package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.BookComment;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
}
