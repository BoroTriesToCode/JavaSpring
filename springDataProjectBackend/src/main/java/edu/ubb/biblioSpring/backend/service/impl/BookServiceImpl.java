package edu.ubb.biblioSpring.backend.service.impl;

import edu.ubb.biblioSpring.backend.model.Book;
import edu.ubb.biblioSpring.backend.repository.BookRepository;
import edu.ubb.biblioSpring.backend.service.BookService;
import edu.ubb.biblioSpring.backend.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() throws ServiceException {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve books", e);
        }
    }

    @Override
    public Book getById(Long id) throws ServiceException {
        try {
            return bookRepository.findById(id).orElseThrow(() -> new ServiceException("Book not found"));
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve book by id", e);
        }
    }

    @Override
    public Book createBook(Book book) throws ServiceException {
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new ServiceException("Failed to create book", e);
        }
    }
}
