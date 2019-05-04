package bookstore.service.report;

import bookstore.domain.logs.LogsEntry;
import bookstore.domain.user.User;
import bookstore.service.book.BookService;
import bookstore.service.logs.LogsService;
import bookstore.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportService {

    private BookService bookService;
    private UserService userService;
    private LogsService logsService;
    private static final Logger log = LoggerFactory.getLogger(
            ReportService.class);

    @Autowired
    public ReportService(BookService bookService, UserService userService, LogsService logsService) {
        this.bookService = bookService;
        this.userService = userService;
        this.logsService = logsService;
    }

    public User getMostActiveCustomer() {
        log.trace("getMostActiveCustomer --- method entered");
        List<LogsEntry> logs = (List<LogsEntry>) logsService.findAll();
        final String[] userId = new String[1];
        logs.stream().collect(Collectors.groupingBy(LogsEntry::getUserId, Collectors.counting())).entrySet().stream()
                .reduce((a, b) -> a.getValue() > b.getValue() ? a : b)
                .ifPresentOrElse(e -> userId[0] = e.getKey(), () -> userId[0] = "");
        User result = userService.findById(userId[0]).get();
        log.trace("getMostActiveCustomer: result={}", result);
        return result;
    }

    public User getCustomerWhoSpentMost() {
        log.trace("getCustomerWhoSpentMost --- method entered");
        List<LogsEntry> logs = (List<LogsEntry>) logsService.findAll();
        final String[] userId = new String[1];
        logs.stream().collect(Collectors.groupingBy(LogsEntry::getUserId)).entrySet().stream()
                .reduce((a, b) -> a.getValue().stream()
                        .reduce((double) 0, (sum, e) -> sum += bookService.findById(e.getBookId()).get().getPrice(),
                                (sum1, sum2) -> sum1 + sum2) > b.getValue().stream()
                        .reduce((double) 0, (sum, e) -> sum += bookService.findById(e.getBookId()).get().getPrice(),
                                (sum1, sum2) -> sum1 + sum2) ? a : b
                ).ifPresentOrElse(e -> userId[0] = e.getKey(), () -> userId[0] = "");
        User result = userService.findById(userId[0]).get();
        log.trace("getCustomerWhoSpentMost: result={}", result);
        return result;
    }
}
