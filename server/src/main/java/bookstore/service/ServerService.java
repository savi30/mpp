package bookstore.service;

import bookstore.repository.book.BookRepository;
import bookstore.repository.logs.LogsRepository;
import bookstore.repository.user.UserRepository;
import bookstore.service.book.BookServiceImpl;
import bookstore.service.logs.LogsService;
import bookstore.service.report.ReportServiceImpl;
import bookstore.service.user.UserServiceImpl;
import bookstore.utils.RepositoryFactory;
import bookstore.utils.domain.book.Book;
import bookstore.utils.domain.logs.LogsEntry;
import bookstore.utils.domain.user.User;
import bookstore.utils.service.BookService;
import bookstore.utils.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ServerService implements ClientServerService {
    private UserService userService;
    private BookService bookService;
    private ReportServiceImpl reportService;
    private ExecutorService executorService;

    @SuppressWarnings("unchecked")
    public ServerService(ExecutorService executorService) {
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        LogsService logsService;
        bookService = new BookServiceImpl((BookRepository) repositoryFactory.getDBRepository(Book.class));
        userService = new UserServiceImpl((UserRepository) repositoryFactory.getDBRepository(User.class));
        logsService = new LogsService((LogsRepository) repositoryFactory.getDBRepository(LogsEntry.class));
        reportService = new ReportServiceImpl(bookService, userService, logsService);
        this.executorService = executorService;
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
