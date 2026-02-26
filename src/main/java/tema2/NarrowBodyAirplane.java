package tema2;
import java.time.LocalTime;

public class NarrowBodyAirplane extends Airplane {

    public NarrowBodyAirplane(String id, String start, String end, LocalTime idealTime, String model, boolean emergency) {
        super(id, start, end, idealTime,"narrow",model,emergency);
    }
    @Override
    public String toString() {
        return "Narrow Body - " + super.toString();
    }
}
