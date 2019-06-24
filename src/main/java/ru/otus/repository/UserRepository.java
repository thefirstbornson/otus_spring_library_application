package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findUserByName (String name);
}
