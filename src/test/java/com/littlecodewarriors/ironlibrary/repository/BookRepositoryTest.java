package com.littlecodewarriors.ironlibrary.repository;

import com.littlecodewarriors.ironlibrary.model.Author;
import com.littlecodewarriors.ironlibrary.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    private Book book1;
    private Author author1;
    @BeforeEach
    void setUp() {
        book1 = new Book("7asd7asyd","Title","history", 2);
        author1 = new Author("Nameee","asdasd");
        bookRepository.save(book1);
        authorRepository.save(author1);

        List<Book> listOfbooks = new ArrayList<Book>();
        listOfbooks.add(book1);
        author1.setAuthorBooks(listOfbooks);
        book1.setAuthor(author1);

        authorRepository.save(author1);
        bookRepository.save(book1);
    }

    @AfterEach
    void tearDown() {
       book1.setAuthor(null);
        bookRepository.deleteAll();
        author1.setAuthorBooks(null);
        authorRepository.deleteAll();
    }

    @Test
    void add_book_addbook(){
        Optional<Book> bookOptional = bookRepository.findById(book1.getIsbn());
        assertEquals("Title", bookOptional.get().getTitle());
    }
    @Test
    void authorBookRelationship(){
        Optional<Book> bookOptional = bookRepository.findById(book1.getIsbn());
        assertEquals("Nameee", bookOptional.get().getAuthor().getName());
    }
}