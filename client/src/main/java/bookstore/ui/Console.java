package bookstore.ui;

import bookstore.domain.book.Book;
import bookstore.domain.core.NamedEntity;
import bookstore.domain.logs.LogsEntry;
import bookstore.domain.user.User;
import bookstore.repository.paging.Page;
import bookstore.repository.paging.impl.PageRequest;
import bookstore.service.AbstractCRUDService;
import bookstore.service.book.BookService;
import bookstore.service.logs.LogsService;
import bookstore.service.report.ReportService;
import bookstore.service.user.UserService;
import bookstore.utils.RepositoryFactory;
import bookstore.utils.validator.exception.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pollos_hermanos.
 */
public class Console {
    private BookService bookService;
    private UserService userService;
    private ReportService reportService;

    @SuppressWarnings("unchecked")
    public Console() {
        RepositoryFactory repositoryFactory = new RepositoryFactory();
        Scanner scanner = new Scanner(System.in);
        LogsService logsService;
        label2:
        while(true){
            System.out.println("Choose repository type: inMemory, file, xml, db");
            String type = scanner.next();
            switch (type) {
                case "inMemory":
                    bookService = new BookService(repositoryFactory.getRepository(Book.class));
                    userService = new UserService(repositoryFactory.getRepository(User.class));
                    logsService = new LogsService(repositoryFactory.getRepository(LogsEntry.class));
                    break label2;
                case "file":
                    bookService = new BookService(repositoryFactory.getFileRepository(Book.class, "Data/Books"));
                    userService = new UserService(repositoryFactory.getFileRepository(User.class, "Data/Users"));
                    logsService = new LogsService(repositoryFactory.getFileRepository(LogsEntry.class, "Data/Logs"));
                    break label2;
                case "xml":
                    bookService = new BookService(repositoryFactory.getXMLRepository(Book.class, "Data/BooksXML.xml"));
                    userService = new UserService(repositoryFactory.getXMLRepository(User.class, "Data/UsersXML.xml"));
                    logsService = new LogsService(repositoryFactory.getXMLRepository(LogsEntry.class, "Data/LogsXML.xml"));
                    break label2;
                case "db":
                    bookService = new BookService(repositoryFactory.getDBRepository(Book.class));
                    userService = new UserService(repositoryFactory.getDBRepository(User.class));
                    logsService = new LogsService(repositoryFactory.getDBRepository(LogsEntry.class));
                    break label2;
                default:
                    System.out.println("No such type");
                    break;
            }
        }
        reportService = new ReportService(bookService,userService, logsService);
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
                            } catch (ValidationException | NoSuchElementException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "addBook": {
                        System.out.println("Add Book {id, Title, author_id.AuthorName, timestamp, price, quantity}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 6) {
                            System.out.println(
                                    "Wrong parameters, should be: id, Title, author_id.AuthorName, timestamp, price, quantity!");
                        } else {
                            try {
                                Book book = parseBook(items);
                                bookService.save(book);
                            } catch (ValidationException | IllegalArgumentException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "updateBook": {
                        System.out.println("Update Book {id, Title, author_id.AuthorName, timestamp, price, quantity}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 6) {
                            System.out.println(
                                    "Wrong parameters, should be: id, Title, author_id.AuthorName, timestamp, price, quantity!");
                        } else {
                            try {
                                Book book = parseBook(items);
                                bookService.update(book);
                            } catch (ValidationException | IllegalArgumentException | NoSuchElementException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "deleteBook": {
                        System.out.println("Delete book with id:");
                        params = bufferedReader.readLine();
                        try {
                            bookService.delete(params);
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
                        printAllEntitiesWithPaging(userService);
                        break;
                    case "printBooksWithPaging":
                        printAllEntitiesWithPaging(bookService);
                        break;
                    case "mostActive":
                        System.out.println(reportService.getMostActiveCustomer());
                        break ;
                    case "biggestSpender":
                        System.out.println(reportService.getCustomerWhoSpentMost());
                        break ;
                    case "buyBook": {
                        System.out.println("Buy book {bookId, userId}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 2) {
                            System.out.println("Wrong parameters, should be: bookId, userId!");
                        } else {
                            Optional<Book> optional = bookService.buy(items.get(0), items.get(1));
                            optional.ifPresentOrElse( opt -> System.out.println("Buy was successful!"),
                                    ()-> System.out.println("Book not in stock!"));
                        }
                        break;
                    }
                    case "filterBooksByTitle": {
                        System.out.println("Filter book by title:");
                        params = bufferedReader.readLine();
                        bookService.filterBooksByTitle(params).forEach(System.out::println);
                        break;
                    }
                    case "filterBooksByQuantity": {
                        System.out.println("Filter book by quantity:");
                        params = bufferedReader.readLine();
                        bookService.filterBooksByQuantity(Integer.valueOf(params)).forEach(System.out::println);
                        break;
                    }
                    case "filterBooksByAuthor": {
                        System.out.println("Filter books by author:");
                        params = bufferedReader.readLine();
                        bookService.filterBooksByAuthor(params).forEach(System.out::println);
                        break;
                    }
                    case "filterBooksByDate": {
                        System.out.println("Filter books by date {start date, end date}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 2) {
                            System.out.println("Wrong parameters, should be {start date, end date}");
                        } else {
                            bookService.filterBooksByDate(Timestamp.valueOf(items.get(0)),
                                    Timestamp.valueOf(items.get(1))).forEach(System.out::println);
                        }
                        break;
                    }
                    case "filterBooksByPrice": {
                        System.out.println("Filter books by price {min price, max price}");
                        params = bufferedReader.readLine();
                        List<String> items = Arrays.asList(params.split(","));
                        if (items.size() != 2) {
                            System.out.println("Wrong parameteres, should be {min price, max price}");
                        } else {
                            bookService.filterBooksByPrice(Double.valueOf(items.get(0)), Double.valueOf(items.get(1)))
                                    .forEach(System.out::println);
                        }
                        break;
                    }
                    case "filterUsersByName": {
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

    private void printAllBooks(){
        List<Book> books = bookService.findAll();
        books.forEach(System.out::println);
    }

    private void printAllUsers(){
        List<User> users = userService.findAll();
        users.forEach(System.out::println);
    }

    private void printAllEntitiesWithPaging(AbstractCRUDService service) {
        System.out.println("Input page size:");
        Scanner scanner = new Scanner(System.in);
        PageRequest pageRequest = new PageRequest(0,scanner.nextInt());
        Page entities = service.findAll(pageRequest);
        while (true){
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
            if(entities.getContent().count()==0){
                System.out.println("Done");
                break;
            }
            entities.getContent().forEach(System.out::println);
            entities.nextPageable();
        }
    }

    private Book parseBook(List<String> items) throws IllegalArgumentException {
        Book book = new Book(items.get(0), items.get(1));
        List<String> authors = Arrays.asList(items.get(2).split(";"));
        List<String[]> arg = new ArrayList<>();
        authors.forEach(a -> arg.add(a.split("-")));
        book.setAuthors(arg.stream()
                .map(a -> new NamedEntity(a[0], a[1]))
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
        System.out.println("\tprintBooksWithPaging");
        System.out.println("\tprintUsers");
        System.out.println("\tprintUsersWithPaging");
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
