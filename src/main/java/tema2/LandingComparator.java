package tema2;
import java.util.Comparator;

public class LandingComparator<T extends Airplane> implements Comparator<T> {
    @Override
    public int compare(T plane1, T plane2) {
        if(plane1.getStatus() && !plane2.getStatus()){
            return 1;
        }
        if(!plane1.getStatus() && plane2.getStatus()){
            return -1;
        }
        if (plane1.checkEmergency() && !plane2.checkEmergency()) {
            return -1;
        } else if (!plane1.checkEmergency() && plane2.checkEmergency()) {
            return 1;
        }

        return plane1.getIdealTime().compareTo(plane2.getIdealTime());
    }
}
