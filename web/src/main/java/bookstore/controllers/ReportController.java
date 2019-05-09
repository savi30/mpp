package bookstore.controllers;

import bookstore.domain.user.User;
import bookstore.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/mostActive", method = RequestMethod.GET)
    public User getMostActiveCustomer() {
        return reportService.getMostActiveCustomer();
    }

    @RequestMapping(value = "/biggestSpender", method = RequestMethod.GET)
    public User getBiggestSpender() {
        return reportService.getCustomerWhoSpentMost();
    }
}

