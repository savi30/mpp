package bookstore.tcp;

public class TcpException extends RuntimeException {
    public TcpException(String message) {
        super(message);
    }

    public TcpException(String message, Throwable cause) {
        super(message, cause);
    }

    public TcpException(Throwable cause) {
        super(cause);
    }
}
