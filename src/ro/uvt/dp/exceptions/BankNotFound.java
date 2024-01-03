package ro.uvt.dp.exceptions;

public class BankNotFound extends Exception {
    public BankNotFound() {
        super();
    }
    public BankNotFound(String message) {
        super(message);
    }
    public BankNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
