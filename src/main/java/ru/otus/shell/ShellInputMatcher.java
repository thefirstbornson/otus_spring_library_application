package ru.otus.shell;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.instance_service.CreateUpdateServise;

public interface ShellInputMatcher {
    CreateUpdateServise getServise(String instanceType);
    MongoRepository getRepository(String instanceType);
}
