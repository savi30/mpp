package bookstore.web.converters;

import bookstore.core.domain.book.Book;
import bookstore.web.dto.BookDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookDtoConverter implements Converter<Book, BookDto> {
    @Override
    public BookDto convert(Book source) {
        BookDto dto = new BookDto();
        dto.setAuthors(source.getAuthors());
        dto.setId(source.getId());
        dto.setName(source.getName());
        dto.setPrice(source.getPrice());
        dto.setPublishYear(source.getPublishYear().toString());
        dto.setQuantity(source.getQuantity());
        return dto;
    }
}
