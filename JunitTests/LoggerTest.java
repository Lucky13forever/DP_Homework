import static org.junit.Assert.*;
import org.junit.Test;
import ro.uvt.dp.logger.Logger;

public class LoggerTest {

    @Test
    public void testLoggerSingleton() {
        // Get two instances of the logger
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        // Check if both instances are the same (singleton pattern)
        assertSame(logger1, logger2);

        // Log some messages
        logger1.logMessage("Message 1");
        logger2.logMessage("Message 2");

        // Check if the log contains the logged messages
        assertTrue(logger1.getLog().contains("Message 1"));
        assertTrue(logger1.getLog().contains("Message 2"));
        assertTrue(logger2.getLog().contains("Message 1"));
        assertTrue(logger2.getLog().contains("Message 2"));
    }
}
