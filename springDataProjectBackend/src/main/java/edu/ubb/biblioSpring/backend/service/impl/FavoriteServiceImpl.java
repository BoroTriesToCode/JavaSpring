package edu.ubb.biblioSpring.backend.service.impl;

import edu.ubb.biblioSpring.backend.model.Favorite;
import edu.ubb.biblioSpring.backend.repository.BookRepository;
import edu.ubb.biblioSpring.backend.repository.FavoriteRepository;
import edu.ubb.biblioSpring.backend.repository.UserRepository;
import edu.ubb.biblioSpring.backend.service.FavoriteService;
import edu.ubb.biblioSpring.backend.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Favorite> getUserFavorites(Long userId) throws ServiceException {
        try {
            return favoriteRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve favorites", e);
        }
    }

    @Override
    public Favorite addFavorite(Favorite favorite) throws ServiceException {
        try {
            return favoriteRepository.save(favorite);
        } catch (Exception e) {
            throw new ServiceException("Failed to add favorite", e);
        }
    }

    public void deleteFavorite(Long id) throws ServiceException {
        try {
            favoriteRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Error deleting favorite", e);
        }
    }

    public void saveComment(Long favoriteId, String comment) throws ServiceException {
        try {
            Favorite favorite = favoriteRepository.findById(favoriteId).orElseThrow(() -> new ServiceException("Favorite not found"));
            favorite.setComment(comment);
            favoriteRepository.save(favorite);
        } catch (Exception e) {
            throw new ServiceException("Error saving comment", e);
        }
    }
}
