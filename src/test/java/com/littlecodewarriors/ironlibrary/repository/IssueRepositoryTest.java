package com.littlecodewarriors.ironlibrary.repository;

import com.littlecodewarriors.ironlibrary.model.Author;
import com.littlecodewarriors.ironlibrary.model.Book;
import com.littlecodewarriors.ironlibrary.model.Issue;
import com.littlecodewarriors.ironlibrary.model.Student;
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
class IssueRepositoryTest {
    @Autowired
    public IssueRepository issueRepository;
    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    public Issue issue1;
    private Book book1;
    private Author author1;
    private Student student1;
    @BeforeEach
    void setUp() {
        book1 = new Book("7asd7asyd","Title","history", 2);
        author1 = new Author("Nameee","asdasd");
        student1 = new Student("23d232","Pepito");
        issue1 = new Issue(student1,book1);

        studentRepository.save(student1);
        bookRepository.save(book1);
        authorRepository.save(author1);

        List<Book> listOfbooks = new ArrayList<Book>();
        listOfbooks.add(book1);
        author1.setAuthorBooks(listOfbooks);
        book1.setAuthor(author1);

        authorRepository.save(author1);
        bookRepository.save(book1);
        studentRepository.save(student1);
        issueRepository.save(issue1);
    }

    @AfterEach
    void tearDown() {
        issue1.setIssueStudent(null);
        issue1.setIssueBook(null);
        issueRepository.deleteAll();
        studentRepository.deleteAll();
        book1.setAuthor(null);
        bookRepository.deleteAll();
        author1.setAuthorBooks(null);
        authorRepository.deleteAll();
    }
    @Test
    void add_issue_addIssue(){
        Optional<Issue> issueOptional = issueRepository.findById(issue1.getId());
        assertEquals("Pepito", issueOptional.get().getIssueStudent().getName());
    }
}