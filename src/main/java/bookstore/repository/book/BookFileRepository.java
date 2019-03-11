package bookstore.repository.book;

import bookstore.domain.book.Book;
import bookstore.domain.core.NamedEntity;
import bookstore.repository.InMemoryRepository;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;
import org.checkerframework.checker.nullness.Opt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author pollos_hermanos.
 */
public class BookFileRepository extends BookInMemoryRepository {
    private String fileName;

    public BookFileRepository(Validator<Book> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                String id = String.valueOf(items.get(0));
                String title = items.get(1);
                List<String> authors = Arrays.asList(items.get(2).split(" "));
                Timestamp date = Timestamp.valueOf(items.get(3));
                Integer quantity = Integer.valueOf(items.get(5));
                Double price = Double.valueOf(items.get(4));

                Book book = new Book(id, title);
                book.setAuthors(authors.stream().map(a -> new NamedEntity(1, a))
                        .collect(Collectors.toList()));
                book.setPublishYear(date);
                book.setPrice(price);
                book.setQuantity(quantity);
                try {
                    super.save(book);
                } catch (ValidationException ve) {
                    ve.printStackTrace();
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveToFile(Book entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," +entity.getTitle() + "," + entity.getAuthorsString() + ","
                            + entity.getPublishYear() + "," + entity.getPrice() + "," + entity.getQuantity());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Book> buy(String bookId, String clientId){
        Optional<Book> optional = super.buy(bookId, clientId);
        if(optional.isPresent()){
            writeAllToFile();
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidationException {
        Optional<Book> optional = super.save(entity);
        if (optional.isPresent()) {
            saveToFile(entity);
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> delete(String id){
        Optional<Book> optional = super.delete(id);
        if (optional.isPresent()) {
            writeAllToFile();
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(Book entity)throws ValidationException {
        Optional<Book> optional = super.update(entity);
        if (optional.isPresent()) {
            writeAllToFile();
            return optional;
        }
        return Optional.empty();
    }

    private void writeAllToFile(){
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            entities.values().forEach(book -> {try{
                bufferedWriter.write(book.getId() + "," +book.getTitle() + "," + book.getAuthorsString()
                        + "," + book.getPublishYear() + "," + book.getPrice() + "," + book.getQuantity() +"\n");
            }catch (IOException e){
                e.printStackTrace();
            }});
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
