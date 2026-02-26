package tema2;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

public class UnavailableRunwayException extends Exception {
    private final LocalTime timestamp;

    public UnavailableRunwayException(String message, String folderPath, LocalTime Timestamp) throws IOException {
        super(message);
        this.timestamp = Timestamp;
        logException(folderPath);
    }

    private void logException(String folderPath) throws IOException {
        Path folder = Path.of(folderPath);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String logFilePath = folderPath + "/board_exceptions.out";
        try (FileWriter writer = new FileWriter(logFilePath, true)) {
            writer.write(timestamp.format(outputFormatter) + " | " + getMessage() + "\n");
        }
    }
}
