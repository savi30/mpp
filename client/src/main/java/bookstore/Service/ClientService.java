package bookstore.Service;

import bookstore.domain.book.Book;
import bookstore.domain.user.User;
import bookstore.service.ClientServerService;
import bookstore.tcp.Message;
import bookstore.tcp.TcpClient;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClientService implements ClientServerService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public ClientService(
            ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<List<User>> getUsers() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header("getUsers")
                    .build();

            return (List<User>) tcpClient.sendAndReceive(request).getEntity();
        });
    }

    @Override
    public Future<List<Book>> getBooks() {
        return executorService.submit(() ->{
            Message request = Message.builder()
                    .header("getBooks")
                    .build();

            return (List<Book>) tcpClient.sendAndReceive(request).getEntity();
        });
    }

    @Override
    public Future<Optional<User>> addUser(User entity) {
        return executorService.submit(() ->{
            Message request = Message.builder()
                    .header("addUser")
                    .entity(entity)
                    .build();
            return Optional.ofNullable((User)tcpClient.sendAndReceive(request).getEntity());
        });
    }

    @Override
    public Future<Optional<User>> updateUser(User entity) {
        return executorService.submit(() ->{
            Message request = Message.builder()
                    .header("updateUser")
                    .entity(entity)
                    .build();
            return Optional.ofNullable((User)tcpClient.sendAndReceive(request).getEntity());
        });
    }

    @Override
    public Future<Optional<User>> deleteUser(String id) {
        return executorService.submit(() ->{
            Message request = Message.builder()
                    .header("deleteUser")
                    .body(id)
                    .build();
            return Optional.ofNullable((User)tcpClient.sendAndReceive(request).getEntity());
        });
    }

    @Override
    public Future<Optional<Book>> addBook(Book entity) {
        return executorService.submit(() ->{
            Message request = Message.builder()
                    .header("addBook")
                    .entity(entity)
                    .build();
            return Optional.ofNullable((Book)tcpClient.sendAndReceive(request).getEntity());
        });
    }

    @Override
    public Future<Optional<Book>> updateBook(Book entity) {
        return executorService.submit(() ->{
            Message request = Message.builder()
                    .header("updateBook")
                    .entity(entity)
                    .build();
            return Optional.ofNullable((Book)tcpClient.sendAndReceive(request).getEntity());
        });
    }

    @Override
    public Future<Optional<Book>> deleteBook(String id) {
        return executorService.submit(() ->{
            Message request = Message.builder()
                    .header("deleteBook")
                    .body(id)
                    .build();
            return Optional.ofNullable((Book)tcpClient.sendAndReceive(request).getEntity());
        });
    }

    @Override
    public Future<Optional<Book>> buyBook(String bookID, String userID) {
        return executorService.submit(() ->{
            Message request = Message.builder()
                    .header("buyBook")
                    .body(bookID + " " + userID)
                    .build();
            return Optional.ofNullable((Book)tcpClient.sendAndReceive(request).getEntity());
        });
    }
}
