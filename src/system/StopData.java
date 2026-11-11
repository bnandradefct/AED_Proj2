package system;

public class StopData {
    public final String stationName;
    public final String arrivalTime;
    public final String departureTime;

    public StopData(String stationName, String arrivalTime, String departureTime) {
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
}
