package edu.ubb.biblioSpring.backend.service;

import edu.ubb.biblioSpring.backend.model.Book;
import edu.ubb.biblioSpring.backend.service.ServiceException;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks() throws ServiceException;
    Book getById(Long id) throws ServiceException;
    Book createBook(Book book) throws ServiceException;
}
