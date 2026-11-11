package system;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;
import dataStructures.Map;
import dataStructures.SepChainHashTable;
import java.io.Serializable;

/**
 *
 * @space O(S + J * K) onde S é o número de estações na linha, J o número de
 *        viagens registadas e K a média de paragens por viagem.
 */
public class StationsDirectoryClass implements StationsDirectory, Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Station> lineStations;
    private final Map<String, Station> stationsMap;
    private final Map<String, Journey> journeysMap;
    private int insertionCounter;

    /**
     * @time O(1)
     * @space O(1)
     */
    public StationsDirectoryClass() {
        this.lineStations = new DoublyLinkedList<Station>();
        this.stationsMap = new SepChainHashTable<String, Station>();
        this.journeysMap = new SepChainHashTable<String, Journey>();
        this.insertionCounter = 0;
    }

    @Override
    public int getStationCount() {
        return lineStations.size();
    }

    @Override
    public void initStation(String stationName) {
        Station st = new StationClass(stationName, lineStations.size());
        lineStations.addLast(st);
        stationsMap.put(stationName.toLowerCase(), st);
    }

    @Override
    public Station getStation(String name) {
        if (name == null) return null;
        return stationsMap.get(name.toLowerCase());
    }

    @Override
    public boolean hasStation(String name) {
        return getStation(name) != null;
    }

    @Override
    public boolean isValidOrder(String originName, String destName) {
        Station origin = getStation(originName);
        Station dest = getStation(destName);
        if (origin == null || dest == null) {
            return false;
        }
        return origin.getOrderInLine() < dest.getOrderInLine();
    }

    @Override
    public boolean hasJourney(String journeyId) {
        if (journeyId == null) return false;
        return journeysMap.get(journeyId.toLowerCase()) != null;
    }

    /**
     * Adiciona uma viagem à rede.
     * @time O(N) onde N é o número de paragens da viagem.
     * @space O(N)
     */
    @Override
    public boolean addJourney(String id, List<StopData> stopsData) {
        if (hasJourney(id)) {
            return false;
        }

        JourneyClass journey = new JourneyClass(id, insertionCounter++);
        Iterator<StopData> it = stopsData.iterator();
        int index = 0;

        while (it.hasNext()) {
            StopData sd = it.next();
            Station st = getStation(sd.stationName);
            Time arr = null;
            if (sd.arrivalTime != null) {
            arr = Time.parse(sd.arrivalTime);
            }

            Time dep = null;
            if (sd.departureTime != null) {
            dep = Time.parse(sd.departureTime);
            }


            Stop stop = new StopClass(st, arr, dep, index++);
            journey.addStop(stop);
            st.addDeparture(journey);
        }

        journeysMap.put(id.toLowerCase(), journey);
        return true;
    }

    /**
     * Remove uma viagem existente.
     * @time O(N * D) onde N é o número de paragens da viagem e D o número de partidas por estação.
     * @space O(1)
     */
    @Override
    public boolean removeJourney(String journeyId) {
        Journey journey = journeysMap.get(journeyId.toLowerCase());
        if (journey == null) {
            return false;
        }

        journeysMap.remove(journeyId.toLowerCase());

        Iterator<Stop> it = journey.getStops().iterator();
        while (it.hasNext()) {
            Stop stop = it.next();
            stop.getStation().removeDeparture(journey);
        }
        return true;
    }

    /**
     * Procura a viagem direta mais cedo para o passageiro.
     * Desempate: hora de partida mais cedo; persistindo, inserida há mais tempo no sistema.
     * @time O(D) onde D é o número de partidas que servem a estação de origem.
     * @space O(1)
     */
    @Override
    public Journey findJourney(String originName, String destName, Time passengerTime) {
        Station origin = getStation(originName);
        Station dest = getStation(destName);
        if (origin == null || dest == null) {
            return null;
        }

        Journey bestJourney = null;
        Time bestDepTime = null;

        Iterator<Journey> it = origin.getDepartures().iterator();
        while (it.hasNext()) {
            Journey j = it.next();
            if (j.stopsAt(dest)) {
                Stop origStop = j.getStop(origin);
                Stop destStop = j.getStop(dest);

                if (origStop.getStopIndex() < destStop.getStopIndex()) {
                    Time dep = origStop.getDepartureTime();
                    if (dep != null && !dep.isBefore(passengerTime)) {
                        if (bestJourney == null) {
                            bestJourney = j;
                            bestDepTime = dep;
                        } else if (dep.isBefore(bestDepTime)) {
                            bestJourney = j;
                            bestDepTime = dep;
                        } else if (dep.compareTo(bestDepTime) == 0 &&
                                   j.getInsertionOrder() < bestJourney.getInsertionOrder()) {
                            bestJourney = j;
                            bestDepTime = dep;
                        }
                    }
                }
            }
        }
        return bestJourney;
    }

    /**
     * Procura a viagem direta com menor número de paragens dentro do intervalo de partida.
     * Desempates sucessivos: menor chegada, menor partida, inserida há mais tempo.
     * @time O(D) onde D é o número de partidas da estação de origem.
     * @space O(1)
     */
    @Override
    public Journey findJourneyMinStops(String originName, String destName, Time minTime, Time maxTime) {
        Station origin = getStation(originName);
        Station dest = getStation(destName);
        if (origin == null || dest == null) {
            return null;
        }

        Journey bestJourney = null;
        int minStops = Integer.MAX_VALUE;
        Time bestArrTime = null;
        Time bestDepTime = null;

        Iterator<Journey> it = origin.getDepartures().iterator();
        while (it.hasNext()) {
            Journey j = it.next();
            if (j.stopsAt(dest)) {
                Stop origStop = j.getStop(origin);
                Stop destStop = j.getStop(dest);

                if (origStop.getStopIndex() < destStop.getStopIndex()) {
                    Time dep = origStop.getDepartureTime();
                    Time arr = destStop.getArrivalTime();

                    if (dep != null && !dep.isBefore(minTime) && !dep.isAfter(maxTime)) {
                        int stops = j.countStopsBetween(origin, dest);

                        if (bestJourney == null) {
                            bestJourney = j;
                            minStops = stops;
                            bestArrTime = arr;
                            bestDepTime = dep;
                        } else if (stops < minStops) {
                            bestJourney = j;
                            minStops = stops;
                            bestArrTime = arr;
                            bestDepTime = dep;
                        } else if (stops == minStops) {
                            if (arr.isBefore(bestArrTime)) {
                                bestJourney = j;
                                bestArrTime = arr;
                                bestDepTime = dep;
                            } else if (arr.compareTo(bestArrTime) == 0) {
                                if (dep.isBefore(bestDepTime)) {
                                    bestJourney = j;
                                    bestDepTime = dep;
                                } else if (dep.compareTo(bestDepTime) == 0 &&
                                           j.getInsertionOrder() < bestJourney.getInsertionOrder()) {
                                    bestJourney = j;
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestJourney;
    }
}
