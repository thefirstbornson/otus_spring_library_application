package ru.otus.instance_service;

public interface CreateUpdateServise<T> {
    T create();
    T update();
}
