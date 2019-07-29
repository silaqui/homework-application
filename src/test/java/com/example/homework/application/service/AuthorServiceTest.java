package com.example.homework.application.service;

import com.example.homework.application.dao.BookDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AuthorServiceTest {

    @Mock
    private BookDao bookDoa;

    @InjectMocks
    public AuthorService tested;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetRating() {
        //given
        //when
        tested.getRating();
        //then
        verify(bookDoa, times(1)).getAuthors();
    }
}
