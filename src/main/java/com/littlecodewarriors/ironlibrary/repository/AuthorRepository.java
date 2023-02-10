package com.littlecodewarriors.ironlibrary.repository;

import com.littlecodewarriors.ironlibrary.model.Author;
import com.littlecodewarriors.ironlibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByNameAndEmail(String name, String email);
//    Optional<Author> findByName(String name);
    List<Author> findByNameContaining(String authorName);

    @Query("SELECT a FROM Author a JOIN FETCH authorBooks WHERE a.name LIKE %:authorName%")
    List<Author> findByNameContainingWithBooks(@Param("authorName") String authorName);

}
