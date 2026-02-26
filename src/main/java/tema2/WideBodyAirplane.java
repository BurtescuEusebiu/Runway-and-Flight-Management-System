package tema2;

import java.time.LocalTime;

public class WideBodyAirplane extends Airplane {

    public WideBodyAirplane(String id, String start, String end, LocalTime idealTime, String model, boolean emergency) {
        super(id, start, end, idealTime,"wide",model,emergency);
    }
    @Override
    public String toString() {
        return "Wide Body - " + super.toString();
    }

}
