import dataStructures.List;
import java.io.Serializable;

public interface StationsDirectory extends Serializable {
    int getStationCount();
    void initStation(String stationName);
    Station getStation(String name);
    boolean hasStation(String name);
    boolean isValidOrder(String originName, String destName);

    boolean hasJourney(String journeyId);
    boolean addJourney(String id, List<StopData> stops);
    boolean removeJourney(String journeyId);

    Journey findJourney(String originName, String destName, Time passengerTime);
    Journey findJourneyMinStops(String originName, String destName, Time minTime, Time maxTime);
}
