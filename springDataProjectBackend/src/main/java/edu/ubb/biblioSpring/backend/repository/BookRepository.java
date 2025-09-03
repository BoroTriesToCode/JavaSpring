package edu.ubb.biblioSpring.backend.repository;

import edu.ubb.biblioSpring.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
