package ro.uvt.dp.logger;

public class Logger {
    private static Logger instance;
    private String log;

    private Logger() {
        // Private constructor to prevent external instantiation
        log = "";
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void logMessage(String message) {
        log += message + "\n";
    }

    public String getLog() {
        return log;
    }
}
