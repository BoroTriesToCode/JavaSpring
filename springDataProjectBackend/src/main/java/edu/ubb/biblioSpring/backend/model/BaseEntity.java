package edu.ubb.biblioSpring.backend.model;
import jakarta.persistence.*;


@MappedSuperclass
public abstract class BaseEntity extends AbstractModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false, unique = true)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
