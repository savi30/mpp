package bookstore.web.converters;

import bookstore.core.domain.book.Book;
import bookstore.web.dto.BookDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class BookConverter implements Converter<BookDto, Book> {
    @Override
    public Book convert(BookDto dto) {
        Book entity = new Book();
        entity.setAuthors(dto.getAuthors());
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setPublishYear(Timestamp.valueOf(dto.getPublishYear()));
        entity.setQuantity(dto.getQuantity());
        return entity;
    }
}
