package ru.otus.shell;

import ru.otus.dao.GenericDao;
import ru.otus.instance_service.CreateUpdateServise;

public interface ShellInputMatcher {
    CreateUpdateServise getServise(String instanceType);
    GenericDao getDao(String instanceType);
}
