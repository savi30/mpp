package bookstore.web.converters;

import bookstore.core.domain.logs.LogsEntry;
import bookstore.web.dto.LogEntryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class LogEntryConverter implements Converter<LogEntryDto, LogsEntry> {

    public LogsEntry convert(LogEntryDto dto) {
        LogsEntry entity = new LogsEntry();
        entity.setBookId(dto.getBook().getId());
        entity.setClientId(dto.getUser().getId());
        entity.setTransactionId(Long.getLong(dto.getId()));
        entity.setTransactionDate(Timestamp.valueOf(dto.getDate()));
        return entity;
    }
}

