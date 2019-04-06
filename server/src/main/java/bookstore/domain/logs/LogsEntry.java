package bookstore.domain.logs;

import bookstore.domain.core.NamedEntity;

import java.sql.Timestamp;

public class LogsEntry extends NamedEntity<String> {
    private String user_id;
    private String book_id;
    private Timestamp transaction_date;
    private Long transaction_id;

    public LogsEntry(String s, String clientId, String bookId, Timestamp transactionDate) {
        super(s, null);
        this.transaction_id = Long.valueOf(s);
        this.user_id = clientId;
        this.book_id = bookId;
        this.transaction_date = transactionDate;
    }

    public LogsEntry(String clientId, String bookId, Timestamp transactionDate) {
        this.user_id = clientId;
        this.book_id = bookId;
        this.transaction_date = transactionDate;
    }

    public LogsEntry(){}

    public Long getTransaction_id(){
        return transaction_id;
    }

    public void setTransaction_id(Long transaction_id){
        this.transaction_id = transaction_id;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setClientId(String clientId) {
        this.user_id = clientId;
    }

    public String getBookId() {
        return book_id;
    }

    public void setBookId(String bookId) {
        this.book_id = bookId;
    }

    public Timestamp getTransactionDate() {
        return transaction_date;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transaction_date = transactionDate;
    }

    @Override
    public String toString() {
        return this.getTransaction_id() + " " + this.getUser_id() + " " + this.getBookId() + " " + this.getTransactionDate()
                .toLocalDateTime().toString();
    }
}
