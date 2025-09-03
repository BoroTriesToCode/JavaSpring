package edu.ubb.biblioSpring.backend.api;

import edu.ubb.biblioSpring.backend.api.assembler.UserAssembler;
import edu.ubb.biblioSpring.backend.dto.in.UserInDto;
import edu.ubb.biblioSpring.backend.dto.out.UserOutDto;
import edu.ubb.biblioSpring.backend.model.User;
import edu.ubb.biblioSpring.backend.service.ServiceException;
import edu.ubb.biblioSpring.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private UserAssembler userAssembler;

    @GetMapping
    public ResponseEntity<List<UserOutDto>> getAllUsers() {
        try {
            List<User> users = userService.getAll();
            List<UserOutDto> userDtos = users.stream()
                    .map(userAssembler::modelToDto)
                    .collect(Collectors.toList());
            LOGGER.info("Successfully retrieved all users");
            return ResponseEntity.ok(userDtos);
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, "Exception in getAllUsers method", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserInDto userInDto) {
        try {
            if (userService.usernameExists(userInDto.getUsername())) {
                LOGGER.warning("Username already exists: " + userInDto.getUsername());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
            }
            User newUser = userService.register(userAssembler.dtoToModel(userInDto));
            LOGGER.info("Successfully registered a new user");
            return ResponseEntity.ok(userAssembler.modelToDto(newUser));
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, "Exception in registerUser method", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{userid}")
    public ResponseEntity<UserOutDto> updateUser(@PathVariable("userid") Long id, @RequestBody UserInDto inDto) {
        try {
            User updatedUser = userService.update(id, userAssembler.dtoToModel(inDto));
            LOGGER.info("Successfully updated user");
            return ResponseEntity.ok(userAssembler.modelToDto(updatedUser));
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, "Exception in updateUser method", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{userid}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userid") Long id) {
        try {
            userService.delete(id);
            LOGGER.info("Successfully deleted user");
            return ResponseEntity.noContent().build();
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, "Exception in deleteUser method", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserInDto userInDto, HttpServletRequest request) {
        try {
            User loginUser = new User();
            loginUser.setUsername(userInDto.getUsername());
            loginUser.setPassword(userInDto.getPassword());
            boolean loginSuccess = userService.login(loginUser);
            if (loginSuccess) {
                User dbUser = userService.getByUsername(userInDto.getUsername());
                HttpSession session = request.getSession(true);
                session.setAttribute("username", dbUser.getUsername());
                LOGGER.info("Login successful");
                return ResponseEntity.ok(userAssembler.modelToDto(dbUser));
            } else {
                LOGGER.warning("Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, "Exception in login method", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            LOGGER.info("Logout successful");
        }
        return ResponseEntity.ok("Logout successful");
    }
}
