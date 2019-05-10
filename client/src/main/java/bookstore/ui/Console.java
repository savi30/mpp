package bookstore.ui;

import bookstore.domain.book.Book;
import bookstore.domain.user.Author;
import bookstore.domain.user.User;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pollos_hermanos.
 */
@Component
public class Console {
    private RestTemplate restTemplate;
    String url;

    @Autowired
    public Console(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.url = "http://localhost:8080/book_store/";
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
                        postUser(params);
                        break;
                    }
                    case "deleteUser": {
                        System.out.println("Delete user with id:");
                        params = bufferedReader.readLine();
                        try {
                            restTemplate.delete(url + "users/" + params);
                        } catch (NoSuchElementException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                    case "updateUser": {
                        System.out.println("Update user {id, Name}");
                        params = bufferedReader.readLine();
                        postUser(params);
                        break;
                    }
                    case "addBook": {
                        System.out.println("Add Book {id, Title, author_id.AuthorName, timestamp, price, quantity}");
                        params = bufferedReader.readLine();
                        postBook(params);
                        break;
                    }
                    case "updateBook": {
                        System.out.println("Update Book {id, Title, author_id.AuthorName, timestamp, price, quantity}");
                        params = bufferedReader.readLine();
                        postBook(params);
                        break;
                    }
                    case "deleteBook": {
                        System.out.println("Delete book with id:");
                        params = bufferedReader.readLine();
                        try {
                            restTemplate.delete(url + "books/" + params);
                        } catch (NoSuchElementException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "printBooks": {
                        printAllBooks();
                        break;
                    }
                    case "printUsers": {
                        printAllUsers();
                        break;
                    }
                    case "printUsersWithPaging":
                        //printAllEntitiesWithPaging(userService);
                        break;
                    case "printBooksWithPaging":
                        //printAllEntitiesWithPaging(bookService);
                        break;
                    case "mostActive":
                        int a;
                        ResponseEntity<User> response
                                = restTemplate.getForEntity(url + "users/mostActive", User.class);
                        System.out.println(response.getBody());
                        break;
                    case "biggestSpender":
                        ResponseEntity<User> responseBiggestSpender
                                = restTemplate.getForEntity(url + "users/biggestSpender", User.class);
                        System.out.println(responseBiggestSpender.getBody());
                        break;
//                    case "buyBook": {
//                        System.out.println("Buy book {bookId, userId}");
//                        params = bufferedReader.readLine();
//                        List<String> items = Arrays.asList(params.split(","));
//                        if (items.size() != 2) {
//                            System.out.println("Wrong parameters, should be: bookId, userId!");
//                        } else {
//                            Optional<Book> optional = bookService.buy(items.get(0), items.get(1));
//                            optional.ifPresentOrElse(opt -> System.out.println("Buy was successful!"),
//                                    () -> System.out.println("Book not in stock!"));
//                        }
//                        break;
//                    }
                    case "filterBooksByTitle": {
                        System.out.println("Filter book by title:");
                        params = bufferedReader.readLine();
                        ResponseEntity<List> responseFilterTitle
                                = restTemplate.getForEntity(url + "books/title/" + params, List.class);
                        List<Book> books = (List<Book>) responseFilterTitle.getBody();
                        if(books.size() == 0){
                            System.out.println("No match was found!");
                        }
                        else{
                            books.forEach(System.out::println);
                        }
                        break;
                    }
                    case "filterBooksByQuantity": {
                        System.out.println("Filter book by quantity:");
                        params = bufferedReader.readLine();
                        ResponseEntity<List> responseFilterQuantity
                                = restTemplate.getForEntity(url + "books/quantity/" + params, List.class);
                        List<Book> books = (List<Book>) responseFilterQuantity.getBody();
                        if(books.size() == 0){
                            System.out.println("No match was found!");
                        }
                        else{
                            books.forEach(System.out::println);
                        }
                        break;
                    }
                    case "filterBooksByAuthor": {
                        System.out.println("Filter books by author:");
                        params = bufferedReader.readLine();
                        ResponseEntity<List> responseFilterAuthor
                                = restTemplate.getForEntity(url + "books/author/" + params, List.class);
                        List<Book> books = (List<Book>) responseFilterAuthor.getBody();
                        if (books.size() == 0) {
                            System.out.println("No match was found!");
                        } else {
                            books.forEach(System.out::println);
                        }
                        break;
                    }
//                    case "filterBooksByDate": {
//                        System.out.println("Filter books by date {start date, end date}");
//                        params = bufferedReader.readLine();
//                        List<String> items = Arrays.asList(params.split(","));
//                        if (items.size() != 2) {
//                            System.out.println("Wrong parameters, should be {start date, end date}");
//                        } else {
//                            Collection<Book> books = bookService.filterBooksByDate(Timestamp.valueOf(items.get(0)),
//                                    Timestamp.valueOf(items.get(1)));
//                            if(books.size() == 0){
//                                System.out.println("No match was found!");
//                            }
//                            else{
//                                books.forEach(System.out::println);
//                            }
//                        }
//                        break;
//                    }
                    case "filterBooksByPrice": {
                        System.out.println("Filter books by price {min price, max price}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 2) {
                            System.out.println("Wrong parameteres, should be {min price, max price}");
                        } else {
                            ResponseEntity<Book[]> responseFilterPrice
                                    = restTemplate.getForEntity(url + "books/price/" + params, Book[].class);
                            List<Book> books = Arrays.asList(responseFilterPrice.getBody());
                            if(books.size() == 0){
                                System.out.println("No match was found!");
                            }
                            else{
                                books.forEach(System.out::println);
                            }
                        }
                        break;
                    }
                    case "filterUsersByName": {
                        System.out.println("Filter users by name:");
                        params = bufferedReader.readLine();
                        ResponseEntity<User[]> responseFilterName
                                = restTemplate.getForEntity(url + "users/username/" + params, User[].class);
                        List<User> users = Arrays.asList(responseFilterName.getBody());
                        if(users.size() == 0){
                            System.out.println("No match was found!");
                        }
                        else{
                            users.forEach(System.out::println);
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
        ResponseEntity<Book[]> response
                = restTemplate.getForEntity(url + "books", Book[].class);
        List<Book> books = Arrays.asList(response.getBody());
        books.forEach(System.out::println);
    }

    private void printAllUsers() {
        ResponseEntity<User[]> response
                = restTemplate.getForEntity(url + "users", User[].class);
        List<User> users = Arrays.asList(response.getBody());
        users.forEach(System.out::println);
    }

    private void postUser(String params){
        List<String> items = Arrays.asList(params.split(","));
        if (items.size() != 2) {
            System.out.println("Wrong parameters, should be: id, name!");
        } else {
            User user = new User(items.get(0), items.get(1));
            HttpEntity<User> request = new HttpEntity<>(user);
            restTemplate.postForObject(url + "users", request, User.class);

        }
    }

    private void postBook(String params){
        List<String> items = Arrays.asList(params.split(","));
        if (items.size() != 6) {
            System.out.println(
                    "Wrong parameters, should be: id, Title, author_id.AuthorName, timestamp, price, quantity!");
        } else {
            Book book = parseBook(items);
            HttpEntity<Book> request = new HttpEntity<>(book);
            restTemplate.postForObject(url + "books", request, Book.class);
        }
    }

    /*private void printAllEntitiesWithPaging(CrudService service) {
        System.out.println("Input page size:");
        Scanner scanner = new Scanner(System.in);
        PageRequest pageRequest = new PageRequest(0, scanner.nextInt());
        Page entities = service.findAll(pageRequest);
        while (true) {
            System.out.println("enter 'n' - for next; 'x' - for exit: ");
            String cmd = scanner.next();
            if (cmd.equals("x")) {
                System.out.println("exit");
                break;
            }
            if (!cmd.equals("n")) {
                System.out.println("Not an option!");
                continue;
            }
            if (entities.getContent().count() == 0) {
                System.out.println("Done");
                break;
            }
            entities.getContent().forEach(System.out::println);
            entities.nextPageable();
        }
    }*/

    private Book parseBook(List<String> items) throws IllegalArgumentException {
        Book book = new Book(items.get(0), items.get(1));
        List<String> authors = Arrays.asList(items.get(2).split(";"));
        List<String[]> arg = new ArrayList<>();
        authors.forEach(a -> arg.add(a.split("\\.")));
        book.setAuthors(arg.stream()
                .map(a -> new Author(a[0], a[1]))
                .collect(Collectors.toList()));
        Timestamp date = Timestamp.valueOf(items.get(3));
        Double price = Double.valueOf(items.get(4));
        Integer quantity = Integer.valueOf(items.get(5));
        book.setPrice(price);
        book.setPublishYear(date);
        book.setQuantity(quantity);
        return book;
    }

    private void help() {
        System.out.println("Available commands:");
        System.out.println("\texit");
        System.out.println("\taddUser");
        System.out.println("\tdeleteUser");
        System.out.println("\tupdateUser");
        System.out.println("\taddBook");
        System.out.println("\tdeleteBook");
        System.out.println("\tupdateBook");
        System.out.println("\tprintBooks");
        //System.out.println("\tprintBooksWithPaging");
        System.out.println("\tprintUsers");
        //System.out.println("\tprintUsersWithPaging");
        System.out.println("\tbuyBook");
        System.out.println("\tfilterBooksByTitle");
        System.out.println("\tfilterBooksByQuantity");
        System.out.println("\tfilterBooksByAuthor");
        System.out.println("\tfilterBooksByDate");
        System.out.println("\tfilterBooksByPrice");
        System.out.println("\tfilterUsersByName");
        System.out.println("\tmostActive");
        System.out.println("\tbiggestSpender");
    }

}
