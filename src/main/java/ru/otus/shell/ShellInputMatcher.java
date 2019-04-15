package ru.otus.shell;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.instance_service.CreateUpdateServise;

public interface ShellInputMatcher {
    CreateUpdateServise getServise(String instanceType);
    JpaRepository getRepository(String instanceType);
}
