package com.littlecodewarriors.ironlibrary.repository;

import com.littlecodewarriors.ironlibrary.model.Author;
import com.littlecodewarriors.ironlibrary.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("SELECT s FROM Student s JOIN FETCH issuedBooks WHERE s.usn = :studentUsn")
    Optional<Student> findByUsnWithBooks(@Param("studentUsn") String studentUsn);
}
