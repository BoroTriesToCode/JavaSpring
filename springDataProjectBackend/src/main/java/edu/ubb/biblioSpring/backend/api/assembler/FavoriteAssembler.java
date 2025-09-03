package edu.ubb.biblioSpring.backend.api.assembler;

import edu.ubb.biblioSpring.backend.dto.in.FavoriteInDto;
import edu.ubb.biblioSpring.backend.dto.out.FavoriteOutDto;
import edu.ubb.biblioSpring.backend.model.Favorite;
import edu.ubb.biblioSpring.backend.model.Book;
import edu.ubb.biblioSpring.backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class FavoriteAssembler {

    public FavoriteOutDto modelToDto(Favorite model) {
        FavoriteOutDto dto = new FavoriteOutDto();
        dto.setId(model.getId());
        dto.setBook(new BookAssembler().modelToDto(model.getBook()));
        dto.setComment(model.getComment());
        return dto;
    }

    public Favorite dtoToModel(FavoriteInDto dto, User user, Book book) {
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBook(book);
        favorite.setComment(dto.getComment());
        return favorite;
    }
}
