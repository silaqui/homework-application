package com.example.homework.application.dao.helpers;

import com.example.homework.application.entity.Author;
import com.example.homework.application.entity.Book;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AuthorListHelperTest {


    private static final String AUTHOR_NAME = "Lambert";
    private static final Double BOOK_ONE_RATING = 3.0;
    private static final Double BOOK_TWO_RATING = 5.0;
    private static final Double AVERAGE_RATING = 4.0;
    private static final int AUTHOR_COUNT = 1;

    private AuthorListHelper tested = new AuthorListHelper();

    @Test
    public void shouldMapBooksToAuthorRatingMap() {
        Book bookOne = new Book();
        bookOne.setAuthors(Arrays.asList(AUTHOR_NAME));
        bookOne.setAverageRating(BOOK_ONE_RATING);
        Book bookTwo = new Book();
        bookTwo.setAuthors(Arrays.asList(AUTHOR_NAME));
        bookTwo.setAverageRating(BOOK_TWO_RATING);
        List<Book> books = Arrays.asList(bookOne, bookTwo);

        Map<String, List<Double>> expected = new HashMap<>();
        expected.put(AUTHOR_NAME, Arrays.asList(BOOK_ONE_RATING, BOOK_TWO_RATING));
        //when
        Map<String, List<Double>> actual = tested.mapBooksToAuthorRatingMap(books);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldCreateListOfAuthorsFromAuthorRatingMap() {
        //given
        Map<String, List<Double>> input = new HashMap<>();
        input.put(AUTHOR_NAME, Arrays.asList(BOOK_ONE_RATING, BOOK_TWO_RATING));
        //when
        List<Author> actual = tested.createListOfAuthorsFromAuthorRatingMap(input);
        //then
        assertThat(actual.size()).isEqualTo(AUTHOR_COUNT);
        assertThat(actual.get(0).getAuthor()).isEqualTo(AUTHOR_NAME);
        assertThat(actual.get(0).getAverageRating()).isEqualTo(AVERAGE_RATING);
    }


}
