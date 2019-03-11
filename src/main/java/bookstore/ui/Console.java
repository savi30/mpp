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
import java.util.Arrays;
import java.util.List;
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
        while(true){
            try {
                System.out.println(">>  ");
                cmd = bufferRead.readLine();
                if(cmd.equals("exit")){
                    break;
                }
                else if(cmd.equals("addUser")){
                    System.out.println("Add user {id, Name}");
                    params = bufferRead.readLine();
                    List<String> items = Arrays.asList(params.split(","));
                    if(items.size() != 2){
                        System.out.println("Wrong parameters, should be: id, name!");
                    }
                    else {
                        User user = new User(items.get(0), items.get(1));
                        try {
                            userService.save(user);
                        } catch (ValidationException e) {
                            e.printStackTrace();
                        }
                        System.out.println(user + " was added successfully");
                    }
                }
                else if(cmd.equals("deleteUser")){
                    System.out.println("Delete user with id:");
                    params = bufferRead.readLine();
                    userService.delete(params);
                }
                else if(cmd.equals("updateUser")){
                    System.out.println("Update user {id, Name}");
                    params = bufferRead.readLine();
                    List<String> items = Arrays.asList(params.split(","));
                    if(items.size() != 2){
                        System.out.println("Wrong parameters, should be: id, name!");
                    }
                    else {
                        User user = new User(items.get(0), items.get(1));
                        try {
                            userService.update(user);
                        } catch (ValidationException e) {
                            e.printStackTrace();
                        }
                        System.out.println(user + " was updated successfully");
                    }
                }
                else if(cmd.equals("addBook")){
                    System.out.println("Add Book {id, Title, Authors, year}");
                    params = bufferRead.readLine();
                    List<String> items = Arrays.asList(params.split(","));
                    if(items.size() != 4){
                        System.out.println("Wrong parameters, should be: id, Title, Authors, timestamp!");
                    }
                    else {
                        Book book = new Book(items.get(0), items.get(1));
                        List<String> authors = Arrays.asList(items.get(2).split(" "));
                        book.setAuthors(authors.stream().map(a -> new NamedEntity(1, a))
                                .collect(Collectors.toList()));
                        try {
                            userService.save(user);
                        } catch (ValidationException e) {
                            e.printStackTrace();
                        }
                        System.out.println(user + " was added successfully");
                    }
                }
                else {
                    System.out.println("Wrong command!");
                }
            }catch (IOException e){
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

    private void addBooks() {
        while (true) {
            Book book = readBook();
            if (book == null) {
                break;
            }
            try {
                bookService.save(book);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
    }

    private Book readBook() {
        System.out.println("Read book {id, title, author, yyyy-mm-dd}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String id = String.valueOf(bufferRead.readLine());// ...
            String title = bufferRead.readLine();
            String author = bufferRead.readLine();
            String dateString = bufferRead.readLine();

            Book book = new Book(title, author);
            book.setId(id);

            return new Book(id, title);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
