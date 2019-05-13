package bookstore.web.converters;

import bookstore.core.domain.logs.LogsEntry;
import bookstore.core.service.book.BookService;
import bookstore.core.service.user.UserService;
import bookstore.web.dto.LogEntryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class LogEntryDtoConverter implements Converter<LogsEntry, LogEntryDto> {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    public LogEntryDto convert(LogsEntry entity) {
        LogEntryDto dto = new LogEntryDto();
        dto.setId(entity.getTransactionId().toString());
        dto.setDate(entity.getTransactionDate().toString());
        dto.setUser(userService.findById(entity.getUserId()).get());
        dto.setBook(bookService.findById(entity.getBookId()).get());
        return dto;
    }
}
