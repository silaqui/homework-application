package com.example.homework.application.service;

import com.example.homework.application.dao.BookDao;
import com.example.homework.application.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public Collection<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public Optional<Book> getBook(String id) {
        return bookDao.getBook(id);
    }

    public Collection<Book> getCategory(String categoryName) {
        return bookDao.getCategory(categoryName);
    }

    public Collection<Book> getQuery(String phrase) {
        return bookDao.getQuery(phrase);
    }

}
