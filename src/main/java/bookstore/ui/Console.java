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
import java.util.NoSuchElementException;
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

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        label:
        while (true) {
            try {
                System.out.println(">>  ");
                cmd = bufferedReader.readLine();
                switch (cmd) {
                    case "exit":
                        break label;
                    case "help": {
                        help();
                        break;
                    }
                    case "addUser": {
                        System.out.println("Add user {id, Name}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 2) {
                            System.out.println("Wrong parameters, should be: id, name!");
                        } else {
                            User user = new User(items.get(0), items.get(1));
                            try {
                                userService.save(user);
                                System.out.println(user + " was added successfully");
                            } catch (ValidationException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "deleteUser": {
                        System.out.println("Delete user with id:");
                        params = bufferedReader.readLine();
                        try {
                            userService.delete(params);
                        } catch (NoSuchElementException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                    case "updateUser": {
                        System.out.println("Update user {id, Name}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 2) {
                            System.out.println("Wrong parameters, should be: id, name!");
                        } else {
                            User user = new User(items.get(0), items.get(1));
                            try {
                                userService.update(user);
                                System.out.println(user + " was updated successfully");
                            } catch (ValidationException | NoSuchElementException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "addBook": {
                        System.out.println("Add Book {id, Title, Authors, timestamp, price, quantity}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 6) {
                            System.out.println("Wrong parameters, should be: id, Title, Authors, timestamp, price, quantity!");
                        } else {
                            try {
                                Book book = parseBook(items);
                                bookService.save(book);
                                System.out.println("Book was added successfully!");
                            } catch (ValidationException | IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "updateBook": {
                        System.out.println("Update Book {id, Title, Authors, timestamp, price, quantity}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 6) {
                            System.out.println("Wrong parameters, should be: id, Title, Authors, timestamp, price, quantity!");
                        } else {
                            try {
                                Book book = parseBook(items);
                                bookService.update(book);
                                System.out.println("Book was updated successfully!");
                            } catch (ValidationException | IllegalArgumentException | NoSuchElementException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "deleteBook":{
                        System.out.println("Delete book with id:");
                        params = bufferedReader.readLine();
                        try {
                            bookService.delete(params);
                        }catch (NoSuchElementException e){
                            e.printStackTrace();
                        }
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
                        params = bufferedReader.readLine();
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
                    case "filterBooksByTitle":{
                        System.out.println("Filter book by title:");
                        params = bufferedReader.readLine();
                        bookService.filterBooksByTitle(params).forEach(System.out::println);
                        break;
                    }
                    case "filterBooksByAuthor":{
                        System.out.println("Filter books by author:");
                        params = bufferedReader.readLine();
                        bookService.filterBooksByAuthor(params).forEach(System.out::println);
                        break;
                    }
                    case "filterBooksByDate":{
                        System.out.println("Filter books by date {start date, end date}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if(items.size() != 2){
                            System.out.println("Wrong parameters, should be {start date, end date}");
                        }
                        else{
                            bookService.filterBooksByDate(Timestamp.valueOf(items.get(0)),
                                    Timestamp.valueOf(items.get(1))).forEach(System.out::println);
                        }
                        break;
                    }
                    case "filterBooksByPrice":{
                        System.out.println("Filter books by price {min price, max price}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if(items.size() !=2){
                            System.out.println("Wrong parameteres, should be {min price, max price}");
                        }
                        else{
                            bookService.filterBooksByPrice(Double.valueOf(items.get(0)), Double.valueOf(items.get(1)))
                                    .forEach(System.out::println);
                        }
                        break;
                    }
                    case "filterUsersByName":{
                        System.out.println("Filter users by name:");
                        params = bufferedReader.readLine();
                        userService.filterUsersByName(params).forEach(System.out::println);
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

    private Book parseBook(List<String> items) throws IllegalArgumentException {
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
        System.out.println("\tfilterBooksByTitle");
        System.out.println("\tfilterBooksByAuthor");
        System.out.println("\tfilterBooksByDate");
        System.out.println("\tfilterBooksByPrice");
        System.out.println("\tfilterUsersByName");
    }

}
