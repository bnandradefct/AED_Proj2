package system;

public class StopClass implements Stop {
    private static final long serialVersionUID = 1L;
    private final Station station;
    private final Time arrivalTime;
    private final Time departureTime;
    private final int stopIndex;

    public StopClass(Station station, Time arrivalTime, Time departureTime, int stopIndex) {
        this.station = station;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopIndex = stopIndex;
    }

    @Override
    public Station getStation() { 
      return station; 
    }

    @Override
    public Time getArrivalTime() { 
      return arrivalTime; 
    }

    @Override
    public Time getDepartureTime() { 
      return departureTime; 
    }

    @Override
    public int getStopIndex() {
       return stopIndex; 
      }
}
