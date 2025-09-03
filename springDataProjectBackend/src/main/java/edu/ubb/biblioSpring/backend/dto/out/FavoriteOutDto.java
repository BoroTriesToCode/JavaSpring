package edu.ubb.biblioSpring.backend.dto.out;

public class FavoriteOutDto {
    private Long id;
    private BookOutDto book;
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookOutDto getBook() {
        return book;
    }

    public void setBook(BookOutDto book) {
        this.book = book;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
