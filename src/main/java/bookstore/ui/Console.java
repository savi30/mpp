package bookstore.ui;

import bookstore.domain.book.Book;
import bookstore.domain.core.NamedEntity;
import bookstore.domain.user.User;
import bookstore.service.book.BookService;
import bookstore.service.user.UserService;
import bookstore.utils.validator.exception.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author pollos_hermanos.
 */
public class Console {
    private BookService bookService;
    private UserService userService;

    public Console(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    public void runConsole() {
        String params, cmd;

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        label:
        while (true) {
            try {
                System.out.println(">>  ");
                cmd = bufferRead.readLine();
                switch (cmd) {
                    case "exit":
                        break label;
                    case "help": {
                        help();
                        break;
                    }
                    case "addUser": {
                        System.out.println("Add user {id, Name}");
                        params = bufferRead.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 2) {
                            System.out.println("Wrong parameters, should be: id, name!");
                        } else {
                            User user = new User(items.get(0), items.get(1));
                            try {
                                userService.save(user);
                            } catch (ValidationException e) {
                                e.printStackTrace();
                            }
                            System.out.println(user + " was added successfully");
                        }
                        break;
                    }
                    case "deleteUser":
                        System.out.println("Delete user with id:");
                        params = bufferRead.readLine();
                        userService.delete(params);
                        break;
                    case "updateUser": {
                        System.out.println("Update user {id, Name}");
                        params = bufferRead.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 2) {
                            System.out.println("Wrong parameters, should be: id, name!");
                        } else {
                            User user = new User(items.get(0), items.get(1));
                            try {
                                userService.update(user);
                            } catch (ValidationException e) {
                                e.printStackTrace();
                            }
                            System.out.println(user + " was updated successfully");
                        }
                        break;
                    }
                    case "addBook": {
                        System.out.println("Add Book {id, Title, Authors, timestamp, price, quantity}");
                        params = bufferRead.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 6) {
                            System.out.println("Wrong parameters, should be: id, Title, Authors, timestamp, price, quantity!");
                        } else {
                            Book book = parseBook(items);
                            try {
                                bookService.save(book);
                            } catch (ValidationException e) {
                                e.printStackTrace();
                            }
                            System.out.println(book + " was added successfully");
                        }
                        break;
                    }
                    case "updateBook": {
                        System.out.println("Update Book {id, Title, Authors, timestamp, price, quantity}");
                        params = bufferRead.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 6) {
                            System.out.println("Wrong parameters, should be: id, Title, Authors, timestamp, price, quantity!");
                        } else {
                            Book book = parseBook(items);
                            try {
                                bookService.update(book);
                            } catch (ValidationException e) {
                                e.printStackTrace();
                            }
                            System.out.println(book + " was updated successfully");
                        }
                        break;
                    }
                    case "deleteBook":{
                        System.out.println("Delete book with id:");
                        params = bufferRead.readLine();
                        bookService.delete(params);
                        break;
                    }
                    case "printBooks":{
                        printAllBooks();
                        break;
                    }
                    case "printUsers":{
                        printAllUsers();
                        break;
                    }
                    case "buyBook":{
                        System.out.println("Buy book {bookId, userId}");
                        params = bufferRead.readLine();
                        List<String > items = Arrays.asList(params.split(","));
                        if(items.size() != 2){
                            System.out.println("Wrong parameters, should be: bookId, userId!");
                        }
                        else{
                            Optional<Book> optional = bookService.buy(items.get(0), items.get(1));
                            if(optional.isPresent()){
                                System.out.println("Buy was successful!");
                            }
                            else{
                                System.out.println("Book not in stock!");
                            }
                        }
                        break;
                    }
                    default:
                        System.out.println("Wrong command!");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printAllBooks() {
        List<Book> books = bookService.findAll();
        books.forEach(System.out::println);
    }

    private void printAllUsers(){
        List<User> users = userService.findAll();
        users.forEach(System.out::println);
    }

    private Book parseBook(List<String> items) {
        Book book = new Book(items.get(0), items.get(1));
        List<String> authors = Arrays.asList(items.get(2).split(" "));
        book.setAuthors(authors.stream().map(a -> new NamedEntity(1, a))
                .collect(Collectors.toList()));
        Timestamp date = Timestamp.valueOf(items.get(3));
        Double price = Double.valueOf(items.get(4));
        Integer quantity = Integer.valueOf(items.get(5));
        book.setPrice(price);
        book.setPublishYear(date);
        book.setQuantity(quantity);
        return book;
    }

    private void help(){
        System.out.println("Available commands:");
        System.out.println("\texit");
        System.out.println("\taddUser");
        System.out.println("\tdeleteUser");
        System.out.println("\tupdateUser");
        System.out.println("\taddBook");
        System.out.println("\tdeleteBook");
        System.out.println("\tupdateBook");
        System.out.println("\tprintBooks");
        System.out.println("\tprintUsers");
        System.out.println("\tbuyBook");
    }

}
