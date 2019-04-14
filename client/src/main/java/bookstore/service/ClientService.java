package bookstore.service;

import bookstore.utils.domain.book.Book;
import bookstore.utils.domain.user.User;
import bookstore.utils.service.BookService;
import bookstore.utils.service.ReportService;
import bookstore.utils.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientService implements ClientServerService {
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private BookService bookService;
    private UserService userService;
    private ReportService reportService;


    public ClientService(BookService bookService, UserService userService,
                         ReportService reportService) {
        this.bookService = bookService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @Override
    public Future<List<User>> getUsers() {
        return executorService.submit((Callable<List<User>>) userService::findAll);
    }

    @Override
    public Future<List<Book>> getBooks() {
        return executorService.submit((Callable<List<Book>>) bookService::findAll);
    }

    @Override
    public Future<Optional<User>> addUser(User entity) {
        Callable<Optional<User>> callableTask = () -> userService.save(entity);
        return executorService.submit(callableTask);
    }

    @Override
    public Future<Optional<User>> updateUser(User entity) {
        Callable<Optional<User>> callableTask = () -> userService.update(entity);
        return executorService.submit(callableTask);
    }

    @Override
    public Future<Optional<User>> deleteUser(String id) {
        Callable<Optional<User>> callableTask = () -> userService.delete(id);
        return executorService.submit(callableTask);
    }

    @Override
    public Future<Optional<Book>> addBook(Book entity) {
        Callable<Optional<Book>> callableTask = () -> bookService.save(entity);
        return executorService.submit(callableTask);
    }

    @Override
    public Future<Optional<Book>> updateBook(Book entity) {
        Callable<Optional<Book>> callableTask = () -> bookService.update(entity);
        return executorService.submit(callableTask);
    }

    @Override
    public Future<Optional<Book>> deleteBook(String id) {
        Callable<Optional<Book>> callableTask = () -> bookService.delete(id);
        return executorService.submit(callableTask);
    }

    @Override
    public Future<Optional<Book>> buyBook(String bookID, String userID) {
        Callable<Optional<Book>> callableTask = () -> bookService.buy(bookID, userID);
        return executorService.submit(callableTask);
    }
}
