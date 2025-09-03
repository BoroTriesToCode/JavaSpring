package edu.ubb.biblioSpring.backend.repository;
import edu.ubb.biblioSpring.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
