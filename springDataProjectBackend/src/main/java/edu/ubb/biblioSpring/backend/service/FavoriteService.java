package edu.ubb.biblioSpring.backend.service;

import edu.ubb.biblioSpring.backend.model.Favorite;

import java.util.List;

public interface FavoriteService {
    List<Favorite> getUserFavorites(Long userId) throws ServiceException;
    Favorite addFavorite(Favorite favorite) throws ServiceException;
    void saveComment(Long favoriteId, String comment) throws ServiceException;

    void deleteFavorite(Long favoriteId) throws ServiceException;
}
