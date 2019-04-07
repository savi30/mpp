package bookstore;

import bookstore.domain.book.Book;
import bookstore.domain.user.User;
import bookstore.service.ClientServerService;
import bookstore.service.ServerService;
import bookstore.tcp.Message;
import bookstore.tcp.TcpServer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class ServerApp {
    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors());

        TcpServer tcpServer = new TcpServer(executorService,
                ClientServerService.SERVER_PORT);

        ServerService serverService = new ServerService(executorService);


        tcpServer.addHandler(
                "getUsers", (request) -> {
                    Future<List<User>> result =
                            serverService.getUsers();
                    try {
                        return getMessage(Message.OK, "", result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return getMessage(Message.ERROR, e.getMessage(), null);
                    }
                });

        tcpServer.addHandler(
                "getBooks", (request) -> {
                    Future<List<Book>> result =
                            serverService.getBooks();
                    try {
                        return getMessage(Message.OK, "", result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return getMessage(Message.ERROR, e.getMessage(), null);
                    }
                });

        tcpServer.addHandler(
                "addUser", (request) -> {
                    try {
                        AtomicReference<Message> message = new AtomicReference<>();
                        Future<Optional<User>> result =
                                serverService.addUser((User) request.getEntity());
                        result.get().ifPresentOrElse(
                                opt -> message.set(getMessage(Message.OK, "", opt)),
                                () -> message.set(getMessage(Message.OK, "", null))
                        );
                        return message.get();
                    }catch (InterruptedException | ExecutionException ve){
                        ve.printStackTrace();
                        return getMessage(Message.ERROR, ve.getMessage(), null);
                    }
                });

        tcpServer.addHandler(
                "updateUser", (request) -> {
                    try {
                        AtomicReference<Message> message = new AtomicReference<>();
                        Future<Optional<User>> result =
                                serverService.updateUser((User) request.getEntity());
                        result.get().ifPresentOrElse(
                                opt -> message.set(getMessage(Message.OK, "", opt)),
                                () -> message.set(getMessage(Message.OK, "", null))
                        );
                        return message.get();
                    }catch (InterruptedException | ExecutionException ve){
                        ve.printStackTrace();
                        return getMessage(Message.ERROR, ve.getMessage(), null);
                    }
                });

        tcpServer.addHandler(
                "deleteUser", (request) -> {
                    try {
                        AtomicReference<Message> message = new AtomicReference<>();
                        Future<Optional<User>> result =
                                serverService.deleteUser(request.getBody());
                        result.get().ifPresentOrElse(
                                opt -> message.set(getMessage(Message.OK, "", opt)),
                                () -> message.set(getMessage(Message.OK, "", null))
                        );
                        return message.get();
                    }catch (InterruptedException | ExecutionException ve){
                        ve.printStackTrace();
                        return getMessage(Message.ERROR, ve.getMessage(), null);
                    }
                });

        tcpServer.addHandler(
                "addBook", (request) -> {
                    try {
                        AtomicReference<Message> message = new AtomicReference<>();
                        Future<Optional<Book>> result =
                                serverService.addBook((Book) request.getEntity());
                        result.get().ifPresentOrElse(
                                opt -> message.set(getMessage(Message.OK, "", opt)),
                                () -> message.set(getMessage(Message.OK, "", null))
                        );
                        return message.get();
                    }catch (InterruptedException | ExecutionException ve){
                        ve.printStackTrace();
                        return getMessage(Message.ERROR, ve.getMessage(), null);
                    }
                });

        tcpServer.addHandler(
                "deleteBook", (request) -> {
                    try {
                        AtomicReference<Message> message = new AtomicReference<>();
                        Future<Optional<Book>> result =
                                serverService.deleteBook(request.getBody());
                        result.get().ifPresentOrElse(
                                opt -> message.set(getMessage(Message.OK, "", opt)),
                                () -> message.set(getMessage(Message.OK, "", null))
                        );
                        return message.get();
                    }catch (InterruptedException | ExecutionException ve){
                        ve.printStackTrace();
                        return getMessage(Message.ERROR, ve.getMessage(), null);
                    }
                });

        tcpServer.addHandler(
                "buyBook", (request) -> {
                    try {
                        AtomicReference<Message> message = new AtomicReference<>();
                        String[] ids = request.getBody().split(" ");
                        Future<Optional<Book>> result =
                                serverService.buyBook(ids[0], ids[1]);
                        result.get().ifPresentOrElse(
                                opt -> message.set(getMessage(Message.OK, "", opt)),
                                () -> message.set(getMessage(Message.OK, "", null))
                        );
                        return message.get();
                    }catch (InterruptedException | ExecutionException ve){
                        ve.printStackTrace();
                        return getMessage(Message.ERROR, ve.getMessage(), null);
                    }
                });

        tcpServer.startServer();

        System.out.println("server - bye");

    }

    private static Message getMessage(String header, String body, Object entity) {
        return Message.builder()
                .header(header)
                .body(body)
                .entity(entity)
                .build();
    }


}
