package tema2;
import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;


public class Commands {
    public void addRunway(String testFolder, HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String id, String use, String type) {
       if(type.equals("wide body")) {
            if(use.equals("landing")) {
                LandingComparator<WideBodyAirplane> comparator = new LandingComparator<>();
                Runway<WideBodyAirplane> runway = new Runway<>(id,use,type,comparator);
                runways.put(id,runway);
            }
            else if(use.equals("takeoff")) {
                TakeOffComparator<WideBodyAirplane> comparator = new TakeOffComparator<>();
                Runway<WideBodyAirplane> runway = new Runway<>(id,use,type,comparator);
                runways.put(id,runway);
            }
       }
       else if(type.equals("narrow body")) {
           if(use.equals("landing")) {
               LandingComparator<NarrowBodyAirplane> comparator = new LandingComparator<>();
               Runway<NarrowBodyAirplane> runway = new Runway<>(id,use,type,comparator);
               runways.put(id,runway);
           }
           else if(use.equals("takeoff")) {
               TakeOffComparator<NarrowBodyAirplane> comparator = new TakeOffComparator<>();
               Runway<NarrowBodyAirplane> runway = new Runway<>(id,use,type,comparator);
               runways.put(id,runway);
           }
       }

    }
    public void addPlane(String testFolder,HashMap<String, Runway<? extends Airplane>> runways, LocalTime timeStamp, String type, String model ,
                         String id, String from, String to, LocalTime idealTime, String runwayID, String emergency) throws IncorrectRunwayException, IOException {
        Runway<? extends Airplane> runway = runways.get(runwayID);
        if(!Objects.equals(type, runway.getPlaneType()))
            throw new IncorrectRunwayException("The chosen runway for allocating the plane is incorrect",testFolder,timeStamp);
        if(from.equals("Bucharest")&& !Objects.equals(runway.getType(), "takeoff")) {
            throw new IncorrectRunwayException("The chosen runway for allocating the plane is incorrect",testFolder,timeStamp);
        }
        if(to.equals("Bucharest")&& !Objects.equals(runway.getType(), "landing")) {
            throw new IncorrectRunwayException("The chosen runway for allocating the plane is incorrect",testFolder,timeStamp);
        }
        if(type.equals("wide body")) {
            WideBodyAirplane plane;
            if(emergency==null)
            {
                plane = new WideBodyAirplane(id, from, to, idealTime, model, false);
            }
            else{
                plane = new WideBodyAirplane(id, from, to, idealTime, model, true);
            }
            runway.add(plane);
        }
        else {
            NarrowBodyAirplane plane;
            if(emergency==null)
            {
                plane = new NarrowBodyAirplane(id, from, to, idealTime, model, false);
            }
            else{
                plane = new NarrowBodyAirplane(id, from, to, idealTime, model, true);
            }
            runway.add(plane);
        }

    }
    public void makeManeuver(String testFolder,HashMap<String, Runway<? extends Airplane>> runways,LocalTime timeStamp,String runwayID) throws IOException, IncorrectRunwayException, UnavailableRunwayException {
        Runway<? extends Airplane> runway = runways.get(runwayID);
        runway.makeManeuver(timeStamp,testFolder);

    }

    public void runwayInfo(String testFolder,HashMap<String, Runway<? extends Airplane>> runways,LocalTime timeStamp,String runwayID) throws IOException, UnavailableRunwayException {
        Runway<? extends Airplane> runway = runways.get(runwayID);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH-mm-ss");
        Path outputPath = Paths.get(testFolder, "runway_info_" + runwayID + "_" + timeStamp.format(outputFormatter) + ".out");
        Path parentDir = outputPath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath.toString()));
        writer.write(runway.toStringWithAvailability(timeStamp));
        writer.close();
    }

    public void airplaneInfo(String testFolder,HashMap<String, Runway<? extends Airplane>> runways,LocalTime timeStamp,String id) throws IOException {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (Runway<? extends Airplane> runway : runways.values()) {
            PriorityQueue<? extends Airplane> airplanes = runway.getAirplanes();
            for (Airplane airplane : airplanes) {
                if (airplane.getId().equals(id)) {
                    String outputFilePath = Paths.get(testFolder, "flight_info.out").toString();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath,true));
                    String formattedTime = timeStamp.format(outputFormatter);
                    String out = formattedTime + " | "+ airplane.toString() +'\n';
                    writer.write(out);
                    writer.close();
                    break;
                }
            }
        }
    }
}
