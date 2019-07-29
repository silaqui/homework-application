package com.example.homework.application.controller;

import com.example.homework.application.service.AuthorService;
import com.example.homework.application.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ControllerTest {

    private static final int STATUS_OK = 200;
    private static final int STATUS_NOT_FOUND = 404;

    private static final String BOOK_ID = "book_id";
    private static final String CATEGORY = "category";
    private static final String PHASE = "phase";

    @Mock
    public BookService bookService;
    @Mock
    public AuthorService authorService;

    @InjectMocks
    public Controller tested;

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
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void shouldGetBook() {
        //given
        //TODO
//        Optional<Book> expected = new Optional<>(new Book());
//        when(bookService.getBook(eq(BOOK_ID))).thenReturn(expected);
        //when
        ResponseEntity actual = tested.getBook(BOOK_ID);
        //then
        verify(bookService, times(1)).getBook(BOOK_ID);
//        assertEquals(actual.getStatusCode(), STATUS_OK);
    }

    @Test
    public void shouldGetBookNotFound() {
        //given
        //TODO
//        Optional<Book> expected = new Optional<>();
//        when(bookService.getBook(eq(BOOK_ID))).thenReturn(expected);
        //when
        ResponseEntity actual = tested.getBook(BOOK_ID);
        //then
        verify(bookService, times(1)).getBook(BOOK_ID);
//        assertEquals(actual.getStatusCode(), STATUS_NOT_FOUND);

    }

    @Test
    public void shouldGetCategory() {
        //given
        //when
        tested.getCategory(CATEGORY);
        //then
        verify(bookService, times(1)).getCategory(CATEGORY);
    }

    @Test
    public void shouldGetQuery() {
        //given
        //when
        tested.getQuery(PHASE);
        //then
        verify(bookService, times(1)).getQuery(PHASE);
    }

    @Test
    public void shouldGetRating() {
        //given
        //when
        tested.getRating();
        //then
        verify(authorService, times(1)).getRating();
    }

}
