package bookstore.domain.logs;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "store_logs")
public class LogsEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "transaction_date")
    private Timestamp transactionDate;

    public LogsEntry(String clientId, String bookId, Timestamp transactionDate) {
        this.userId = clientId;
        this.bookId = bookId;
        this.transactionDate = transactionDate;
    }

    public LogsEntry() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }


    public String getUserId() {
        return userId;
    }

    public void setClientId(String clientId) {
        this.userId = clientId;
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
        return this.getTransactionId() + " " + this.getUserId() + " " + this.getBookId() + " " + this
                .getTransactionDate()
                .toLocalDateTime().toString();
    }
}
