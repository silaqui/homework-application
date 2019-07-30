package com.example.homework.application.dao;

import com.example.homework.application.dao.helpers.AuthorListHelper;
import com.example.homework.application.dao.helpers.QueryHelper;
import com.example.homework.application.entity.Book;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("json_data")
public class BookDaoImplTest {

    private static final int ALL_BOOKS_COUNT = 40;
    private static final String BOOK_ISBN_13 = "9781592432172";
    private static final String BOOK_ISBN_13_NAME = "A Hypervista of the Java Landscape";
    private static final String BOOK_ID = "gJEC2q7DzpQC";
    private static final String BOOK_ID_NAME = "The History of Java";
    private static final String BOOK_NOT_EXISTING = "_NOT_EXISTING_";
    private static final String BOOK_CATEGORY = "Religion";
    private static final String QUERY_PHRASE = "phrase";

    @Mock
    private AuthorListHelper authorListHelper;

    @Mock
    QueryHelper queryHelper;

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
        assertThat(actual.size()).isEqualTo(ALL_BOOKS_COUNT);
    }

    @Test
    public void shouldGetBookByISBN() {
        //given
        //when
        Optional<Book> actual = tested.getBook(BOOK_ISBN_13);
        //then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getTitle()).isEqualTo(BOOK_ISBN_13_NAME);
    }


    @Test
    public void shouldGetBookById() {
        //given
        //when
        Optional<Book> actual = tested.getBook(BOOK_ID);
        //then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getTitle()).isEqualTo(BOOK_ID_NAME);
    }


    @Test
    public void shouldGetBookNotFound() {
        //given
        //when
        Optional<Book> actual = tested.getBook(BOOK_NOT_EXISTING);
        //then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    public void shouldGetCategory() {
        //given
        //when
        Collection<Book> actual = tested.getCategory(BOOK_CATEGORY);
        //then
        assertThat(actual).isNotNull();
        verify(queryHelper, times(ALL_BOOKS_COUNT)).isPhraseInListOfString(any(), eq(BOOK_CATEGORY));
    }

    @Test
    public void shouldGetQuery() {
        //given
        //when
        Collection<Book> actual = tested.getQuery(QUERY_PHRASE);
        //then
        assertThat(actual).isNotNull();
        verify(queryHelper, times(9 * ALL_BOOKS_COUNT)).isPhraseInString(any(), eq(QUERY_PHRASE));
        verify(queryHelper, times(2 * ALL_BOOKS_COUNT)).isPhraseInListOfString(any(), eq(QUERY_PHRASE));
    }

    @Test
    public void shouldGetAuthors() {
        //given
        //when
        tested.getAuthors();
        //then
        verify(authorListHelper, times(1)).getAuthorsByRating(any());
    }

}
