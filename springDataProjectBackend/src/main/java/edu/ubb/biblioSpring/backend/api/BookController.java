package edu.ubb.biblioSpring.backend.api;

import edu.ubb.biblioSpring.backend.api.assembler.BookAssembler;
import edu.ubb.biblioSpring.backend.dto.in.BookInDto;
import edu.ubb.biblioSpring.backend.dto.out.BookOutDto;
import edu.ubb.biblioSpring.backend.model.Book;
import edu.ubb.biblioSpring.backend.service.BookService;
import edu.ubb.biblioSpring.backend.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger LOGGER = Logger.getLogger(BookController.class.getName());

    @Autowired
    private BookService bookService;

    @Autowired
    private BookAssembler bookAssembler;

    @GetMapping
    public ResponseEntity<List<BookOutDto>> getAllBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            List<BookOutDto> bookDtos = books.stream()
                    .map(bookAssembler::modelToDto)
                    .collect(Collectors.toList());
            LOGGER.info("Successfully retrieved all books");
            return ResponseEntity.ok(bookDtos);
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, "Exception in getAllBooks method", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<BookOutDto> createBook(@RequestBody BookInDto bookInDto) {
        try {
            Book newBook = bookService.createBook(bookAssembler.dtoToModel(bookInDto));
            LOGGER.info("Successfully created a new book");
            return ResponseEntity.ok(bookAssembler.modelToDto(newBook));
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, "Exception in createBook method", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
