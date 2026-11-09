import dataStructures.List;
import dataStructures.SinglyLinkedList;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "directory.dat";

    public static void main(String[] args) {
        StationsDirectory directory = null;
        File file = new File(DATA_FILE);

        if (file.exists()) {
            directory = loadFromFile(DATA_FILE);
            if (directory != null) {
                System.out.println("Transportation network restored successfully.");
            }
        }

        if (directory == null) {
            if (args.length > 0) {
                directory = StationsDirectory.loadLineDefinition(args[0]);
                if (directory != null) {
                    System.out.println("Transportation network not found.");
                    System.out.printf("Loading line definition. %d stations loaded.%n", directory.getNumberOfStations());
                }
            }
        }

        if (directory == null) {
            System.out.println("No line definition.");
            return;
        }


        Scanner in = new Scanner(System.in);
        boolean running = true;

        while (running && in.hasNextLine()) {
            String command = in.nextLine().trim();
            if (command.isEmpty()) {
                continue;
            }

            switch (command) {
                case "help":
                    executeHelp();
                    break;
                case "addJourney":
                    executeAddJourney(in, directory);
                    break;
                case "findJourney":
                    executeFindJourney(in, directory);
                    break;
                case "findJourneyMinStops":
                    executeFindJourneyMinStops(in, directory);
                    break;
                case "removeJourney":
                    executeRemoveJourney(in, directory);
                    break;
                case "quit":
                    saveToFile(directory, DATA_FILE);
                    executeQuit();
                    running = false;
                    break;
                default:
                    break;
            }
        }
        in.close();
    }

    private static StationsDirectory loadFromFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                return (StationsDirectory) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void saveToFile(StationsDirectory directory, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void executeHelp() {
    System.out.println("addJourney: Inserts a new journey into the transportation network.ê\n" +
            "findJourney: Finds the earliest journey that allows a passenger to travel\n" +
            "from one station to another.ê\n" +
            "findJourneyMinStops: Finds the direct journey with the minimum number\n" +
            "of stops.ê\n" +
            "removeJourney: Removes the given journey from the transportation network.ê\n" +
            "help: Displays the list of available commands.");
    }

    private static void executeAddJourney(Scanner in, StationsDirectory directory){
        String journeyID = in.nextLine().trim();
        int n = Integer.parseInt(in.nextLine());

        List<String> stations = new SinglyLinkedList<>();
        List<String> arrivals = new SinglyLinkedList<>();
        List<String> departures = new SinglyLinkedList<>();


        String firstStationName = in.nextLine().trim();
        String firstDeparture = in.nextLine().trim();

        stations.addLast(firstStationName);
        arrivals.addLast(null);
        departures.addLast(firstDeparture);

        for(int i = 1; i < n-1; i++){
            String stationName = in.nextLine();
            String arrivalTime = in.nextLine().trim();
            String departureTime = in.nextLine().trim();

            stations.addLast(stationName);
            arrivals.addLast(arrivalTime);
            departures.addLast(departureTime);
        }

        String lastStationName = in.nextLine().trim();
        String lastArrival = in.nextLine().trim();

        stations.addLast(lastStationName);
        arrivals.addLast(lastArrival);
        departures.addLast(null);

        if(directory.addJourney(journeyID, stations, arrivals, departures)){
            System.out.println("Journey successfully added.");
        } else{
            System.out.println("Journey already exists.");
        }
    }

    private static void executeFindJourney(Scanner in, StationsDirectory directory){
        String originStationName = in.nextLine().trim();
        String destinationStationName = in.nextLine().trim();
        String passengerTime = in.nextLine().trim();

        if(!directory.hasStation(originStationName) || !directory.hasStation(destinationStationName)){
            System.out.println("Invalid station.");
        } else if(!directory.validTime(passengerTime)){
            System.out.println("Invalid time.");
        } else if(!directory.correctOrder(originStationName, destinationStationName)){
            System.out.println("Stations are not in the correct order.");
        } else{
            Journey journey = directory.findJourney(originStationName, destinationStationName, passengerTime);
            if(journey != null){
                printJourney(journey);
            } else{
                System.out.println("No direct journey is available.");
            }
        }
    }

    private static void executeFindJourneyMinStops(Scanner in, StationDirectory directory){
        String originStationName = in.nextLine().trim();
        String destinationStationName = in.nextLine().trim();
        String interval = in.nextLine().trim();

        int space = interval.indexOf(' ');
        String minimumTime = interval.substring(0, space).trim();
        String maximumTime = interval.substring(space + 1).trim();

        if (!directory.hasStation(originStationName) || !directory.hasStation(destinationStationName)) {
            System.out.println("Invalid station.");
        } else if (!directory.validTime(minimumTime) || !directory.validTime(maximumTime)) {
            System.out.println("Invalid time.");
        } else if (!directory.validInterval(minimumTime, maximumTime)) {
            System.out.println("Invalid interval time.");
        } else if (!directory.correctOrder(originStationName, destinationStationName)) {
            System.out.println("Stations are not in the correct order.");
        } else {
            Journey journey = directory.findJourneyMinStops(originStationName, destinationStationName,
                    minimumTime, maximumTime);
            if (journey != null) {
                printJourney(journey);
                System.out.printf("Stops: %d%n", journey.getStops());
            } else {
                System.out.println("No direct journey is available.");
            }
        }
    }


    private static void executeRemoveJourney(Scanner in, StationsDirectory directory){
        String journeyID = in.nextLine().trim();
        if (directory.removeJourney(journeyID)) {
            System.out.println("Journey successfully removed.");
        } else {
            System.out.println("Invalid journey.");
        }
    }

    private static void executeQuit(){
    System.out.println("Transportation network saved.");
    System.out.println("Goodbye.");
    }

    private static void printJourney(Journey journey) {
        System.out.printf("Journey: %s%n", journey.getID());
        System.out.printf("Departure station: %s%n", journey.getOrigin());
        System.out.printf("Departure time: %s%n", journey.getDeparture());
        System.out.printf("Arrival station: %s%n", journey.getDestination());
        System.out.printf("Arrival time: %s%n", journey.getArrival());
    }
}

