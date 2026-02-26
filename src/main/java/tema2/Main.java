package tema2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Runway<? extends Airplane>> runways = new HashMap<>();
        String testFolder = "src/main/resources/" + args[0];
        Path inputPath = Paths.get(testFolder, "input.in");
        Commands commandsExecutor = new Commands();
        ArrayList<String> commands = null;
        try {
            commands = (ArrayList<String>) Files.readAllLines(inputPath);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        for (String command : commands) {
            if(command.length() == 0) return;
            String[] tokens = command.split(" - ");
            String timeString = tokens[0];
            String commandName = tokens[1];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime timeStamp = LocalTime.parse(timeString, formatter);
            switch (commandName) {
                case "add_runway_in_use":
                    commandsExecutor.addRunway(testFolder, runways, timeStamp, tokens[2], tokens[3], tokens[4]);
                    break;
                case "allocate_plane":
                    String idealString = tokens[7];
                    LocalTime idealTime = LocalTime.parse(idealString, formatter);
                    try {
                        if(tokens.length>9)commandsExecutor.addPlane(testFolder, runways, timeStamp, tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], idealTime, tokens[8], tokens[9]);
                        else commandsExecutor.addPlane(testFolder, runways, timeStamp, tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], idealTime, tokens[8],null);
                    }
                    catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "permission_for_maneuver":
                    try {commandsExecutor.makeManeuver(testFolder, runways, timeStamp, tokens[2]);}
                    catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "runway_info":
                    try{commandsExecutor.runwayInfo(testFolder, runways, timeStamp, tokens[2]);}
                    catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "flight_info":
                    try{commandsExecutor.airplaneInfo(testFolder, runways, timeStamp, tokens[2]);}
                    catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "exit":
                    break;
            }
        }
    }

}
