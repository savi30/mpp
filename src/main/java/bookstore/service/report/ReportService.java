package bookstore.service.report;

import bookstore.domain.logs.LogsEntry;
import bookstore.domain.user.User;
import bookstore.service.book.BookService;
import bookstore.service.logs.LogsService;
import bookstore.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class ReportService {

    private BookService bookService;
    private UserService userService;
    private LogsService logsService;

    public ReportService(BookService bookService, UserService userService, LogsService logsService) {
        this.bookService = bookService;
        this.userService = userService;
        this.logsService = logsService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public User getMostActiveCustomer() {
        List<LogsEntry> logs = logsService.findAll();
        final String[] userId = new String[1];
        logs.stream().collect(Collectors.groupingBy(LogsEntry::getClientId, Collectors.counting())).entrySet().stream()
                .reduce((a, b) -> a.getValue() > b.getValue() ? a : b)
                .ifPresentOrElse(e -> userId[0] = e.getKey(), () -> userId[0] = "");
        return userService.findById(userId[0]);
    }

    public User getCustomerWhoSpentMost() {
        List<LogsEntry> logs = logsService.findAll();
        final String[] userId = new String[1];
        logs.stream().collect(Collectors.groupingBy(LogsEntry::getClientId)).entrySet().stream()
                .reduce((a, b) -> a.getValue().stream()
                        .reduce((double) 0, (sum, e) -> sum += bookService.findById(e.getBookId()).getPrice(),
                                (sum1, sum2) -> sum1 + sum2) > b.getValue().stream()
                        .reduce((double) 0, (sum, e) -> sum += bookService.findById(e.getBookId()).getPrice(),
                                (sum1, sum2) -> sum1 + sum2) ? a : b
                ).ifPresentOrElse(e -> userId[0] = e.getKey(), () -> userId[0] = "");
        return userService.findById(userId[0]);
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public LogsService getLogsService() {
        return logsService;
    }

    public void setLogsService(LogsService logsService) {
        this.logsService = logsService;
    }
}
