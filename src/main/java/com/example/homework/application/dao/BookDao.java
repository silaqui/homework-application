package com.example.homework.application.dao;

import com.example.homework.application.entity.Author;
import com.example.homework.application.entity.Book;

import java.util.Collection;
import java.util.Optional;

public interface BookDao {
    Collection<Book> getAllBooks();

    Optional<Book> getBook(String id);

    Collection<Book> getCategory(String categoryName);

    Collection<Book> getQuery(String phrase);

    Collection<Author> getAuthors();
}
