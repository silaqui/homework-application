package com.example.homework.application.dao.helpers;

import com.example.homework.application.entity.Author;
import com.example.homework.application.entity.Book;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorListHelper {

    public Collection<Author> getAuthorsByRating(List<Book> books) {
        return createListOfAuthorsFromAuthorRatingMap(mapBooksToAuthorRatingMap(books));
    }

    Map<String, List<Double>> mapBooksToAuthorRatingMap(List<Book> books) {
        Map<String, List<Double>> authorsRatings = new HashMap<>();
        for (Book book : books) {
            if (book.getAverageRating() != null) {
                for (String author : book.getAuthors()) {
                    if (authorsRatings.get(author) != null) {
                        authorsRatings.get(author).add(book.getAverageRating());
                    } else {
                        List<Double> ratings = new ArrayList<>();
                        ratings.add(book.getAverageRating());
                        authorsRatings.put(author, ratings);
                    }
                }
            }
        }
        return authorsRatings;
    }

    List<Author> createListOfAuthorsFromAuthorRatingMap(Map<String, List<Double>> authorsRatings) {
        List<Author> output = new ArrayList<>();
        authorsRatings.forEach((a, r) -> output
                .add(new Author(a, r.stream().mapToDouble(val -> val).average().orElse(0.0))));
        output.sort(Comparator.comparing(Author::getAverageRating, Comparator.reverseOrder()));
        return output;
    }

}
