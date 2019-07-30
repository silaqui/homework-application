package com.example.homework.application.controller;

import static com.example.homework.application.controller.Controller.BASE_PATH;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.homework.application.entity.Author;
import com.example.homework.application.entity.Book;
import com.example.homework.application.service.AuthorService;
import com.example.homework.application.service.BookService;

@RestController
@RequestMapping(BASE_PATH)
public class Controller {

    static final String BASE_PATH = "/api";
    static final String ALL_BOOKS = "/books";
    static final String BOOK = "/book/{id}";
    static final String CATEGORY = "/category/{categoryName}";
	static final String SEARCH = "/search";
    static final String RATING = "/rating";

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @RequestMapping(value = ALL_BOOKS, method = RequestMethod.GET)
    public Collection<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @RequestMapping(value = BOOK, method = RequestMethod.GET)
    public ResponseEntity getBook(@PathVariable("id") String id) {
        Optional<Book> book = bookService.getBook(id);
        if (book.isPresent()) {
            return ResponseEntity.of(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = CATEGORY, method = RequestMethod.GET)
    public Collection<Book> getCategory(@PathVariable("categoryName") String categoryName) {
        return bookService.getCategory(categoryName);
    }

    @RequestMapping(value = SEARCH, method = RequestMethod.GET)
	public Collection<Book> getQuery(@RequestParam("q") String phrase) {
        return bookService.getQuery(phrase);
    }

    @RequestMapping(value = RATING, method = RequestMethod.GET)
    public Collection<Author> getRating() {
        return authorService.getRating();
    }

}
