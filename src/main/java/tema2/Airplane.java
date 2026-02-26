package tema2;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

enum Status {
    WAITING_FOR_TAKEOFF,
    DEPARTED,
    WAITING_FOR_LANDING,
    LANDED
}
public class Airplane {
    private String type;
    private String id;
    private String start;
    private String end;
    private LocalTime idealTime;
    private LocalTime realTime;
    private Status status;
    private String model;
    private boolean emergency;
    public Airplane(String id, String start, String end, LocalTime idealTime, String type, String model, boolean emergency) {
        this.type = type;
        this.id = id;
        this.start = start;
        this.end = end;
        this.idealTime = idealTime;
        this.model = model;
        this.emergency = emergency;
        if(start.equals("Bucharest")) {
            this.status = Status.WAITING_FOR_TAKEOFF;
        }
        else this.status = Status.WAITING_FOR_LANDING;

    }
    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String string = this.model + " - " + this.id + " - " + this.start + " - " + this.end + " - "
                + this.status + " - " + this.idealTime.format(outputFormatter);
        if(status == Status.DEPARTED || status == Status.LANDED) {
            string += " - " + this.realTime.format(outputFormatter);
        }
        return string;
    }
    public void setEmergency() {
        this.emergency = true;
    }

    public boolean checkEmergency() {
        return this.emergency;
    }

    public LocalTime getIdealTime() {
        return idealTime;
    }

    public String getId() {
        return id;
    }

    public void maneuver() {
        if (status == Status.WAITING_FOR_TAKEOFF) {
            status = Status.DEPARTED;
        }
        else{
            status = Status.LANDED;
        }
    }

    public boolean getStatus() {
        if(status == Status.DEPARTED || status == Status.LANDED) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setRealTime(LocalTime realTime) {
        this.realTime = realTime;
    }
}
