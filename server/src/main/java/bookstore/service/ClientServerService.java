package bookstore.service;

import bookstore.domain.book.Book;
import bookstore.domain.user.User;
import bookstore.utils.validator.exception.ValidationException;
import org.checkerframework.checker.nullness.Opt;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

public interface ClientServerService {
    String SERVER_HOST = "localhost";
    int SERVER_PORT = 1234;

    Future<List<User>> getUsers();
    Future<List<Book>> getBooks();
    Future<Optional<User>> addUser(User entity);
    Future<Optional<User>> updateUser(User entity);
    Future<Optional<User>> deleteUser(String id);
    Future<Optional<Book>> addBook(Book entity);
    Future<Optional<Book>> updateBook(Book entity);
    Future<Optional<Book>> deleteBook(String id);
    Future<Optional<Book>> buyBook(String bookID, String userID);

}
