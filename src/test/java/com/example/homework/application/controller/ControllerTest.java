package com.example.homework.application.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.homework.application.entity.Book;
import com.example.homework.application.service.AuthorService;
import com.example.homework.application.service.BookService;

public class ControllerTest {

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
		Optional<Book> expected = Optional.of(new Book());
		when(bookService.getBook(eq(BOOK_ID))).thenReturn(expected);
        //when
        ResponseEntity actual = tested.getBook(BOOK_ID);
        //then
        verify(bookService, times(1)).getBook(BOOK_ID);
		assertEquals(actual.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldGetBookNotFound() {
        //given
		Optional<Book> expected = Optional.empty();
		when(bookService.getBook(eq(BOOK_ID))).thenReturn(expected);
        //when
        ResponseEntity actual = tested.getBook(BOOK_ID);
        //then
        verify(bookService, times(1)).getBook(BOOK_ID);
		assertEquals(actual.getStatusCode(), HttpStatus.NOT_FOUND);

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
