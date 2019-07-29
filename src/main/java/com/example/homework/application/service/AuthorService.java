package com.example.homework.application.service;

import com.example.homework.application.dao.BookDao;
import com.example.homework.application.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthorService {

    @Autowired
    private BookDao bookDoa;

    public Collection<Author> getRating() {
        return bookDoa.getAuthors();
    }
}
