package com.littlecodewarriors.ironlibrary.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String issueDate;
    private String returnDate;
    @OneToOne
    private Student issueStudent;
    @OneToOne
    private Book issueBook;

    public Issue() {
    }

    public Issue(Student issueStudent, Book issueBook) {
        this.issueDate = "asdsad";//currentDate(0);
        this.returnDate = currentDate();
        this.issueStudent = issueStudent;
        this.issueBook = issueBook;
    }
    public String currentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDate localDate = LocalDate.now();//.plusDays(additionalDays);
        return dtf.format(localDate);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Student getIssueStudent() {
        return issueStudent;
    }

    public void setIssueStudent(Student issueStudent) {
        this.issueStudent = issueStudent;
    }

    public Book getIssueBook() {
        return issueBook;
    }

    public void setIssueBook(Book issueBook) {
        this.issueBook = issueBook;
    }
}
