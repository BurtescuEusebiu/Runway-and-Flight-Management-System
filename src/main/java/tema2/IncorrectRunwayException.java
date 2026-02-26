package tema2;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class IncorrectRunwayException extends Exception {
    private final LocalTime timestamp;

    public IncorrectRunwayException(String message, String folderPath, LocalTime timestamp) throws IOException {
        super(message);
        this.timestamp = timestamp;
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
