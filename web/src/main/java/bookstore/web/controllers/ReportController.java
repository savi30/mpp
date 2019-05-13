package bookstore.web.controllers;

import bookstore.core.domain.user.User;
import bookstore.core.service.report.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/mostActive", method = RequestMethod.GET)
    public User getMostActiveCustomer() {
        return reportService.getMostActiveCustomer();
    }

    @RequestMapping(value = "/biggestSpender", method = RequestMethod.GET)
    public User getBiggestSpender() {
        return reportService.getCustomerWhoSpentMost();
    }
}