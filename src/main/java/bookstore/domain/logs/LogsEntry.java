package bookstore.domain.logs;

import bookstore.domain.core.NamedEntity;

import java.sql.Timestamp;

public class LogsEntry extends NamedEntity<String> {
    private String clientId;
    private String bookId;
    private Timestamp transactionDate;

    public LogsEntry(String s, String clientId, String bookId, Timestamp transactionDate) {
        super(s, null);
        this.clientId = clientId;
        this.bookId = bookId;
        this.transactionDate = transactionDate;
    }

    public LogsEntry(String clientId, String bookId, Timestamp transactionDate) {
        this.clientId = clientId;
        this.bookId = bookId;
        this.transactionDate = transactionDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return this.getId() + " " + this.getClientId() + " " + this.getClientId() + " " + this.getTransactionDate()
                .toLocalDateTime().toString();
    }
}
