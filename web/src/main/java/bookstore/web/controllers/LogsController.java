package bookstore.web.controllers;

import bookstore.core.domain.logs.LogsEntry;
import bookstore.core.service.logs.LogsService;
import bookstore.web.dto.LogEntryDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/logs")
public class LogsController {

    private final LogsService logsService;
    private final ConversionService conversionService;

    public LogsController(LogsService logsService, ConversionService conversionService) {
        this.logsService = logsService;
        this.conversionService = conversionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<LogEntryDto> get() {
        return StreamSupport.stream(logsService.findAll().spliterator(), false)
                            .map(logsEntry -> conversionService.convert(logsEntry, LogEntryDto.class))
                            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public LogEntryDto update(@RequestBody LogEntryDto dto) {
        return conversionService
                .convert(logsService.update(conversionService.convert(dto, LogsEntry.class)), LogEntryDto.class);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@RequestBody LogEntryDto dto) {
        logsService.save(conversionService.convert(dto, LogsEntry.class));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        logsService.delete(Long.valueOf(id));
    }

}
