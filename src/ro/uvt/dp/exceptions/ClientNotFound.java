package ro.uvt.dp.exceptions;

public class ClientNotFound extends Exception {
    public ClientNotFound() {
        super();
    }
    public ClientNotFound(String message) {
        super(message);
    }
    public ClientNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
