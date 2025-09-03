package edu.ubb.biblioSpring.backend;
import edu.ubb.biblioSpring.backend.service.UserService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    @Autowired
    private UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
        SpringApplication.run(Main.class, args);
            LOGGER.info("Application started successfully.");
        } catch (Exception e) {
            LOGGER.error("An error occurred during application startup", e);
        }
    }

    @PostConstruct
    private void post() {
        System.out.println("Hello World!");
        //System.out.println(userService.getAll());
        //System.out.println(userService.getById(7L));
    }
}
