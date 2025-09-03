package edu.ubb.biblioSpring.backend.service.impl;

import edu.ubb.biblioSpring.backend.model.User;
import edu.ubb.biblioSpring.backend.repository.UserRepository;
import edu.ubb.biblioSpring.backend.service.ServiceException;
import edu.ubb.biblioSpring.backend.service.UserService;
import edu.ubb.biblioSpring.backend.utils.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncrypter passwordEncrypter;

    @Override
    public User register(User user) throws ServiceException {
        user.setPassword(passwordEncrypter.encrypt(user.getPassword(), user.getUuid()));
        try {
            return userRepository.save(user);
        } catch (DataAccessException e) {
            LOGGER.error("Failure during user registration", e);
            throw new ServiceException("Failure during user registration", e);
        }
    }

    @Override
    public boolean login(User user) throws ServiceException {
        try {
            User dbUser = userRepository.findByUsername(user.getUsername());
            if (dbUser == null) {
                return false;
            } else {
                user.setPassword(passwordEncrypter.encrypt(user.getPassword(), dbUser.getUuid()));
                return user.getPassword().equals(dbUser.getPassword());
            }
        } catch (DataAccessException e) {
            LOGGER.error("Failure during user login", e);
            throw new ServiceException("Failure during user login", e);
        }
    }

    @Override
    public User getById(Long id) throws ServiceException {
        try {
            return userRepository.findById(id).orElseThrow(() -> new ServiceException("User not found"));
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve user", e);
            throw new ServiceException("Failed to retrieve user", e);
        }
    }

    @Override
    public List<User> getAll() throws ServiceException {
        try {
            return userRepository.findAll();
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve all users", e);
            throw new ServiceException("Failed to retrieve all users", e);
        }
    }

    @Override
    public User update(Long id, User user) throws ServiceException {
        try {
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null) {
                throw new ServiceException("User not found");
            }
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncrypter.encrypt(user.getPassword(), existingUser.getUuid()));
            return userRepository.save(existingUser);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to update user", e);
            throw new ServiceException("Failed to update user", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null) {
                throw new ServiceException("User not found");
            }
            userRepository.delete(existingUser);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to delete user", e);
            throw new ServiceException("Failed to delete user", e);
        }
    }

    @Override
    public User getByUsername(String username) throws ServiceException {
        try {
            return userRepository.findByUsername(username);
        } catch (DataAccessException e) {
            LOGGER.error("Failed to retrieve user by username", e);
            throw new ServiceException("Failed to retrieve user by username", e);
        }
    }

    @Override
    public Boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

}
