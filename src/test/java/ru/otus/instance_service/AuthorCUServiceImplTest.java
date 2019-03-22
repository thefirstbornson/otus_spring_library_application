package ru.otus.instance_service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.Assert.*;

@JdbcTest
@ComponentScan
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class AuthorCUServiceImplTest {
    @Autowired
    private AuthorCUService authorCUService;

    @Test
    public void create() {
    //    authorCUService.create();
    }

    @Test
    public void update() {
    }
}