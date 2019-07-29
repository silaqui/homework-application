package com.example.homework.application.service;

import com.example.homework.application.dao.BookDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BookServiceTest {

    private static final String BOOK_ID = "book_id";
    private static final String CATEGORY = "category";
    private static final String PHASE = "phase";

    @Mock
    private BookDao bookDao;

    @InjectMocks
    public BookService tested;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAllBooks() {
        //given
        //when
        tested.getAllBooks();
        //then
        verify(bookDao, times(1)).getAllBooks();
    }

    @Test
    public void shouldGetBook() {
        //given
        //when
        tested.getBook(BOOK_ID);
        //then
        verify(bookDao, times(1)).getBook(BOOK_ID);
    }

    @Test
    public void shouldGetCategory() {
        //given
        //when
        tested.getCategory(CATEGORY);
        //then
        verify(bookDao, times(1)).getCategory(CATEGORY);
    }

    @Test
    public void shouldGetQuery() {
        //given
        //when
        tested.getQuery(PHASE);
        //then
        verify(bookDao, times(1)).getQuery(PHASE);
    }
}
