package edu.ubb.biblioSpring.backend.api.assembler;

import edu.ubb.biblioSpring.backend.dto.in.BookInDto;
import edu.ubb.biblioSpring.backend.dto.out.BookOutDto;
import edu.ubb.biblioSpring.backend.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookAssembler {
    public BookOutDto modelToDto(Book model) {
        BookOutDto newDto = new BookOutDto();
        newDto.setId(model.getId());
        newDto.setTitle(model.getTitle());
        newDto.setAuthorName(model.getAuthorName());
        newDto.setGenre(model.getGenre());
        return newDto;
    }

    public Book dtoToModel(BookInDto dto) {
        Book newBook = new Book();
        newBook.setTitle(dto.getTitle());
        newBook.setGenre(dto.getGenre());
        newBook.setAuthorName(dto.getAuthorName());
        return newBook;
    }
}
