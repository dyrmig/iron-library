package com.littlecodewarriors.ironlibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Student {
    @Id
    private String usn;
    private String name;
    @OneToMany(mappedBy = "issueStudent")
    private List<Issue> issuedBooks;

    public Student() {
    }
    public Student(String usn, String name) {
        this.usn = usn;
        this.name = name;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Issue> getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(List<Issue> issuedBooks) {
        this.issuedBooks = issuedBooks;
    }
}
