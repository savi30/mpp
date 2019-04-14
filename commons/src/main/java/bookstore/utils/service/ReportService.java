package bookstore.utils.service;

import bookstore.utils.domain.user.User;

public interface ReportService {

    public User getMostActiveCustomer();

    public User getCustomerWhoSpentMost();

}
