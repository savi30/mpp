package bookstore.web.dto;

import bookstore.core.domain.book.Book;
import bookstore.core.domain.user.User;

public class LogEntryDto {
    private String id;
    private String date;
    private Book book;
    private User user;

    public LogEntryDto() {

    }

    public LogEntryDto(String id, String date, Book book, User user) {
        this.id = id;
        this.date = date;
        this.book = book;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
