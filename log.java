import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private static Log instance;
    private StringBuilder log;

    private Log() {
        log = new StringBuilder();
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public void logEvent(String event) {
        log.append(event).append("\n");
    }

    public void writeLogToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(log.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        // Test the Log class
        Log log = Log.getInstance();
        log.logEvent("Test event 1");
        log.logEvent("Test event 2");
        log.writeLogToFile("log.txt");
        System.out.println("Log written to file.");
    }
}
