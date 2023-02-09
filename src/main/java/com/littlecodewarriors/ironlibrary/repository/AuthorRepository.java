package com.littlecodewarriors.ironlibrary.repository;

import com.littlecodewarriors.ironlibrary.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByNameAndEmail(String name, String email);
}
