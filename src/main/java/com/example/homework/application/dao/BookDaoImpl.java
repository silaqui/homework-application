package com.example.homework.application.dao;

import com.example.homework.application.entity.Author;
import com.example.homework.application.entity.Book;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("json_data")
@Repository
public class BookDaoImpl implements BookDao {

    private static List<Book> books = new ArrayList<>();


    public BookDaoImpl() {


    }


    @Override
    public Collection<Book> getAllBooks() {
        return books;
    }

    @Override
    public Optional<Book> getBook(String id) {
        return books.stream().filter(book -> book.getIsbn().equals(id)).findFirst();
    }

    @Override
    public Collection<Book> getCategory(String categoryName) {
        return books.stream().filter(book -> book.getCategories().contains(categoryName)).collect(Collectors.toList());
    }

    @Override
    public Collection<Book> getQuery(String phrase) {
        return books.stream().filter(book -> matchPhrase(book, phrase)).collect(Collectors.toList());
    }

    private boolean matchPhrase(Book book, String phrase) {
        return book.getIsbn().contains(phrase) ||
                book.getTitle().contains(phrase) ||
                book.getSubtitle().contains(phrase) ||
                book.getPublisher().contains(phrase) ||
                book.getDescription().contains(phrase) ||
                book.getThumbnailUrl().contains(phrase) ||
                book.getLanguage().contains(phrase) ||
                book.getPreviewLink().contains(phrase) ||
                book.getLanguage().contains(phrase) ||
                book.getAuthors().stream().map(a -> a.contains(phrase)).reduce((a, b) -> a || b).orElse(false) ||
                book.getCategories().stream().map(c -> c.contains(phrase)).reduce((a, b) -> a || b).orElse(false);
    }

    @Override
    public Collection<Author> getAuthors() {


        List<Author> authors = books.stream()
                .filter(b -> b.getAverageRating() != null)
                .map(b -> b.getAuthors().stream()
                        .map(a -> new Author(a, b.getAverageRating())).collect(Collectors.toList())
                ).flatMap(Collection::stream)
                .collect(Collectors.toList());


        return null;
    }

}
