package com.example.homework.application.dao;

import com.example.homework.application.entity.Book;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BookDaoImplTest {

    @InjectMocks
    public BookDaoImpl tested;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAllBooks() {
        //given
        //when
        Collection<Book> actual = tested.getAllBooks();
        //then
        assertThat(actual).isNotNull();
    }

}
