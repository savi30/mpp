package bookstore.utils.builder;

import bookstore.domain.book.Book;
import bookstore.domain.core.NamedEntity;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookBuilder implements Builder<Book> {
    public Book get(String line){
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
        return book;
    }
}
