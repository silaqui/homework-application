package com.example.homework.application.dao;

import com.example.homework.application.dao.helpers.AuthorListHelper;
import com.example.homework.application.dao.helpers.JsonReader;
import com.example.homework.application.dao.helpers.QueryHelper;
import com.example.homework.application.entity.Author;
import com.example.homework.application.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("json_data")
@Repository
public class BookDaoImpl implements BookDao {

    private static List<Book> books;

    @Autowired
    AuthorListHelper authorListHelper;

    @Autowired
    QueryHelper queryHelper;

    static {
        books = JsonReader.readJsonFromResource("books.json");
    }

    @Override
    public Collection<Book> getAllBooks() {
        return books;
    }

    @Override
    public Optional<Book> getBook(String id) {
        return books.stream().filter(book -> book.getIsbn() != null && book.getIsbn().equals(id))
                .findFirst();
    }

    @Override
    public Collection<Book> getCategory(String categoryName) {
        return books.stream().filter(b -> queryHelper.isPhraseInListOfString(b.getCategories(), categoryName))
                .collect(Collectors.toList());

    }

    @Override
    public Collection<Book> getQuery(String phrase) {
        return books.stream().filter(book -> matchPhrase(book, phrase))
                .collect(Collectors.toList());
    }

    // @formatter:off
    private boolean matchPhrase(Book book, String phrase) {
        return queryHelper.isPhraseInString(book.getIsbn(), phrase) ||
                queryHelper.isPhraseInString(book.getTitle(), phrase) ||
                queryHelper.isPhraseInString(book.getSubtitle(), phrase) ||
                queryHelper.isPhraseInString(book.getPublisher(), phrase) ||
                queryHelper.isPhraseInString(book.getDescription(), phrase) ||
                queryHelper.isPhraseInString(book.getThumbnailUrl(), phrase) ||
                queryHelper.isPhraseInString(book.getLanguage(), phrase) ||
                queryHelper.isPhraseInString(book.getPreviewLink(), phrase) ||
                queryHelper.isPhraseInString(book.getLanguage(), phrase) ||
                queryHelper.isPhraseInListOfString(book.getAuthors(), phrase) ||
                queryHelper.isPhraseInListOfString(book.getCategories(), phrase);
    }
    // @formatter:on

    @Override
    public Collection<Author> getAuthors() {
        return authorListHelper.getAuthorsByRating(books);
    }
}
