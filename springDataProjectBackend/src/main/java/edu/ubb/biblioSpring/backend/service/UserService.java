package edu.ubb.biblioSpring.backend.service;

import edu.ubb.biblioSpring.backend.model.User;

import java.util.List;

public interface UserService {
    User register(User user) throws ServiceException;
    boolean login(User user) throws ServiceException;
    User getById(Long id) throws ServiceException;
    List<User> getAll() throws ServiceException;
    User update(Long id, User user) throws ServiceException;
    void delete(Long id) throws ServiceException;
    User getByUsername(String username) throws ServiceException;
    Boolean usernameExists(String username) throws ServiceException;
}
