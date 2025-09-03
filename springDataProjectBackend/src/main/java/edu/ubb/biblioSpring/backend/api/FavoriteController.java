package edu.ubb.biblioSpring.backend.api;

import edu.ubb.biblioSpring.backend.api.assembler.FavoriteAssembler;
import edu.ubb.biblioSpring.backend.dto.in.FavoriteInDto;
import edu.ubb.biblioSpring.backend.dto.out.FavoriteOutDto;
import edu.ubb.biblioSpring.backend.model.Book;
import edu.ubb.biblioSpring.backend.model.Favorite;
import edu.ubb.biblioSpring.backend.model.User;
import edu.ubb.biblioSpring.backend.service.BookService;
import edu.ubb.biblioSpring.backend.service.FavoriteService;
import edu.ubb.biblioSpring.backend.service.ServiceException;
import edu.ubb.biblioSpring.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private FavoriteAssembler favoriteAssembler;

    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteController.class);

    @GetMapping
    public ResponseEntity<List<FavoriteOutDto>> getUserFavorites(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("username") != null) {
                String username = (String) session.getAttribute("username");
                User user = userService.getByUsername(username);
                List<Favorite> favorites = favoriteService.getUserFavorites(user.getId());
                List<FavoriteOutDto> favoriteDtos = favorites.stream()
                        .map(favoriteAssembler::modelToDto)
                        .collect(Collectors.toList());
                LOGGER.info("Successfully retrieved user favorites for user: {}", username);
                return ResponseEntity.ok(favoriteDtos);
            } else {
                LOGGER.warn("Unauthorized access attempt");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (ServiceException e) {
            LOGGER.error("Error getting user favorites", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestBody FavoriteInDto favoriteInDto) {
        try {
            User user = userService.getById(favoriteInDto.getUserId());
            Book book = bookService.getById(favoriteInDto.getBookId());
            Favorite newFavorite = favoriteService.addFavorite(favoriteAssembler.dtoToModel(favoriteInDto, user, book));
            LOGGER.info("Book (Title: {}) added to user (Username: {}) favorites", book.getTitle(), user.getUsername());
            return ResponseEntity.ok(favoriteAssembler.modelToDto(newFavorite));
        } catch (ServiceException e) {
            LOGGER.error("Error adding to favorites", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding to favorites");
        } catch (Exception e) {
            LOGGER.error("Invalid request data", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@RequestBody FavoriteInDto favoriteInDto) {
        try {
            if (favoriteInDto.getId() == null || favoriteInDto.getComment() == null) {
                throw new IllegalArgumentException("Favorite ID and comment must not be null");
            }
            favoriteService.saveComment(favoriteInDto.getId(), favoriteInDto.getComment());
            LOGGER.info("Comment saved successfully for favorite ID: {}", favoriteInDto.getId());
            return ResponseEntity.ok("Comment saved");
        } catch (ServiceException e) {
            LOGGER.error("Error saving comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the comment");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid request data", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long id) {
        try {
            favoriteService.deleteFavorite(id);
            LOGGER.info("Book deleted from user favorites with favorite ID: {}", id);
            return ResponseEntity.ok().build();
        } catch (ServiceException e) {
            LOGGER.error("Error deleting favorite with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the favorite");
        }
    }
}
