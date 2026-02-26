package tema2;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Runway<T extends Airplane> {
    private String id;
    private String type;
    private String planeType;
    private PriorityQueue<T> airplanes;
    private LocalTime lastLanding;
    private Comparator<T> airplaneComparator;
    private int maneuverTime;
    public Runway(String id, String type , String planeType, Comparator<T> comparator) {
        this.id = id;
        this.planeType = planeType;
        this.airplaneComparator = comparator;
        this.type = type;
        if(type.equals("landing")) {
            this.maneuverTime=10;
        }
        else this.maneuverTime=5;
        this.airplanes = new PriorityQueue<>(comparator);
        this.lastLanding = null;
    }
    public void add(Airplane airplane) {
        airplanes.add((T) airplane);
    }

    @Override
    public String toString() {
        String result = this.id;
        ArrayList<T> airplaneList = new ArrayList<>(airplanes);
        airplaneList.sort(airplaneComparator);

        for (T airplane : airplaneList) {
           if(!airplane.getStatus()) result += "\n" + airplane;
        }

        return result;
    }

    public PriorityQueue<T> getAirplanes() {
        return airplanes;
    }

    public String getType() {
        return type;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void makeManeuver(LocalTime timeStamp,String folderPath) throws IOException, UnavailableRunwayException {
        if(lastLanding == null || timeStamp.isAfter(lastLanding.plusMinutes(maneuverTime))) {
            lastLanding = timeStamp;
            T plane = this.airplanes.poll();
            plane.maneuver();
            plane.setRealTime(timeStamp);
            airplanes.add(plane);
        }
        else throw new UnavailableRunwayException("The chosen runway for maneuver is currently occupied",folderPath,timeStamp);
    }

    public String toStringWithAvailability(LocalTime timeStamp) {
        String original = toString();
        String[] lines = original.split("\n", 2);

        String availability;
        if (lastLanding == null || timeStamp.isAfter(lastLanding.plusMinutes(maneuverTime))) {
            availability = " - FREE ";
        } else {
            availability = " - OCCUPIED ";
        }

        return lines[0] + availability + (lines.length > 1 ? "\n" + lines[1] : "");
    }


}
