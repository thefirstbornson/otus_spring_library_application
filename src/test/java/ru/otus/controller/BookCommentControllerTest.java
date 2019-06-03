package ru.otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.BookComment;
import ru.otus.domain.Genre;
import ru.otus.repository.BookCommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(BookCommentController.class)
class BookCommentControllerTest {
    @Autowired
    private JacksonTester<BookComment> json;

    @Autowired
    private MockMvc mvc;

    @MockBean
    BookCommentRepository bookCommentRepository;
    BookComment bookComment;
    List<BookComment> bookcomments;

    private static final long ID =999;
    private static final long FAKE_ID=199;

    @BeforeEach
    void setUp() {
        Book book =new Book("Snuff",new Author("Viktor","Pelevin"), new Genre("sci-fi"));
        bookComment = new BookComment("Nice book", book);
        bookcomments = new ArrayList<>(
                List.of(new BookComment(2,"Good",book)
                        ,new BookComment(3, "Bad",book)
                        ,new BookComment(4,"Ugly",book)));

    }

    @Test
    @DisplayName("Показать все комментарии")
    void getAllAuthors() throws Exception {
        given(bookCommentRepository.findAll(new Sort(Sort.Direction.ASC, "id")))
                .willReturn(bookcomments);
        mvc.perform(get("/bookcomments"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Snuff")))
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON));
        verify(bookCommentRepository, Mockito.times(1)).findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    @Test
    @DisplayName("Показать комментарий с ID "+ID)
    void getAuthor() throws Exception {
        bookComment.setId(ID);
        given(bookCommentRepository.findById(ID))
                .willReturn(Optional.of(bookComment));
        mvc.perform(get("/bookcomments/"+ ID))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pelevin")))
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON));
        verify(bookCommentRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Удалить комментарий с ID "+ ID)
    void removeAuthor() throws Exception {
        mvc.perform(delete("/bookcomments/"+ ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновить комментарий с ID "+ ID)
    void editAuthor() throws Exception {
        bookComment.setId(ID);
        bookComment.setComment("another bookComment");
        given(bookCommentRepository.findById(ID)).willReturn(Optional.of(bookComment));
        given(bookCommentRepository.save(bookComment)).willReturn(bookComment);
        mvc.perform(put("/bookcomments/"+ ID).contentType(APPLICATION_JSON)
                .content(this.json.write(bookComment).getJson()))
                .andExpect(content().string("{\"status\":\"updated\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Возврат статуса 204 (no content) при попытке обновить комментарий с неактуальным ID "+FAKE_ID)
    void editAuthorfail() throws Exception {
        bookComment.setId(ID);
        bookComment.setComment("another bookComment");
        given(bookCommentRepository.findById(FAKE_ID)).willReturn(Optional.empty());
        mvc.perform(put("/bookcomments/"+ FAKE_ID).contentType(APPLICATION_JSON)
                .content(this.json.write(bookComment).getJson()))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Сохранить комментарий")
    void saveAuthor() throws Exception {
        bookComment.setId(ID);
        given(bookCommentRepository.save(bookComment)).willReturn(bookComment);
        mvc.perform(post("/bookcomments").contentType(APPLICATION_JSON)
                .content(this.json.write(bookComment).getJson()))
                .andExpect(content().string("{\"status\":\"saved\"}"))
                .andExpect(status().isCreated());
    }
}