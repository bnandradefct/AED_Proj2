import dataStructures.List;
import dataStructures.SinglyLinkedList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import system.*;

/**
 * Main entry point and command-line interface for the Journey Planner application.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 *
 * Space complexity: O(s + j * k), where s is the number of stations on the line,
 * j is the number of registered journeys, and k is the average number of stops per journey.
 */
public class Main {
    private static final String DATA_FILE = "directory.dat";

    /**
     * Starts the application, loads or initializes the transportation network,
     * and processes user commands from standard input until quit.
     *
     * @param args command-line arguments where args[0] optionally specifies the line definition file
     *
     * Time complexity:
     * - Best case: O(1) when no line definition or serialization file exists
     * - Worst case: O(c * (n + m)), where c is the number of commands, n is stops per journey, and m is departures per station
     */
    public static void main(String[] args) {
        StationsDirectory directory = loadDirectory(args);

        Scanner in = new Scanner(System.in);
        boolean running = true;

        while (running && in.hasNextLine()) {
            String command = in.nextLine().trim();
            if (command.isEmpty()) {
                continue;
            }

            String normalCmd = command.toLowerCase();
            CommandType cmd = CommandType.fromString(normalCmd);

            switch (cmd) {
                case HELP:
                    executeHelp();
                    break;
                case ADD_JOURNEY:
                    executeAddJourney(in, directory);
                    break;
                case FIND_JOURNEY:
                    executeFindJourney(in, directory);
                    break;
                case FIND_JOURNEY_MIN_STOPS:
                    executeFindJourneyMinStops(in, directory);
                    break;
                case REMOVE_JOURNEY:
                    executeRemoveJourney(in, directory);
                    break;
                case QUIT:
                    saveDirectory(directory);
                    executeQuit();
                    running = false;
                    break;
                case UNKNOWN:
                default:
                    break;
            }
        }
        in.close();
    }

    /**
     * Restores the transportation network from the serialization file if available,
     * otherwise initializes it from the line definition file provided in args,
     * or terminates the application if neither exists.
     *
     * @param args command-line arguments
     * @return the initialized or restored StationsDirectory
     *
     * Time complexity:
     * - Best case: O(1) when neither file exists
     * - Worst case: O(s + j * k) when deserializing a saved network, where s is stations, j is journeys, and k is average stops per journey
     */
    private static StationsDirectory loadDirectory(String[] args) {
        File file = new File(DATA_FILE);

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                StationsDirectory directory = (StationsDirectory) ois.readObject();
                System.out.println("Transportation network restored successfully.");
                return directory;
            } catch (IOException | ClassNotFoundException e) {
            }
        }

        if (args.length > 0) {
            File lineFile = new File(args[0]);
            if (lineFile.exists() && lineFile.canRead()) {
                try (Scanner fileScanner = new Scanner(lineFile)) {
                    if (fileScanner.hasNextLine()) {
                        int numStations = Integer.parseInt(fileScanner.nextLine().trim());
                        StationsDirectory directory = new StationsDirectoryClass();
                        for (int i = 0; i < numStations && fileScanner.hasNextLine(); i++) {
                            directory.initStation(fileScanner.nextLine().trim());
                        }
                        System.out.println("Transportation network not found.");
                        System.out.println("Loading line definition. " + directory.getStationCount() + " stations loaded.");
                        return directory;
                    }
                } catch (Exception e) {
                }
            }
        }

        System.out.println("No line definition.");
        System.exit(0);
        return null;
    }

    /**
     * Serializes the current state of the transportation network to disk.
     *
     * @param directory the StationsDirectory to persist
     *
     * Time complexity:
     * - Best case: O(n) when no journeys are stored, where n is the number of stations
     * - Worst case: O(s + j * k), where s is stations, j is journeys, and k is average stops per journey
     */
    private static void saveDirectory(StationsDirectory directory) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the list of available commands and their descriptions.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    private static void executeHelp() {
        System.out.println("addJourney: Inserts a new journey into the transportation network.");
        System.out.println("findJourney: Finds the earliest journey that allows a passenger to travel from one station to another.");
        System.out.println("findJourneyMinStops: Finds the direct journey with the minimum number of stops.");
        System.out.println("removeJourney: Removes the given journey from the transportation network.");
        System.out.println("help: Displays the list of available commands.");
        System.out.println("quit: Saves the transportation network and terminates the application.");
    }

    /**
     * Reads journey details from standard input and inserts the journey into the directory.
     *
     * @param in the input Scanner
     * @param directory the StationsDirectory
     *
     * Time complexity:
     * - Best case: O(n) when the journey ID already exists, where n is the number of stops read
     * - Worst case: O(n + m) when rehashing occurs, where n is stops and m is total registered journeys
     */
    private static void executeAddJourney(Scanner in, StationsDirectory directory) {
        if (!in.hasNextLine()) return;
        String journeyId = in.nextLine().trim();
        if (!in.hasNextLine()) return;
        int n = Integer.parseInt(in.nextLine().trim());

        List<String> stops = new SinglyLinkedList<String>();

        for (int i = 0; i < n; i++) {
            String stationName = in.nextLine().trim();
            String arr = null;
            String dep = null;

            if (i == 0) {
                dep = in.nextLine().trim();
            } else if (i == n - 1) {
                arr = in.nextLine().trim();
            } else {
                arr = in.nextLine().trim();
                dep = in.nextLine().trim();
            }
            stops.addLast(stationName);
            stops.addLast(arr);
            stops.addLast(dep);
        }

        if (directory.addJourney(journeyId, stops)) {
            System.out.println("Journey successfully added.");
        } else {
            System.out.println("Journey already exists.");
        }
    }

    /**
     * Reads origin, destination, and passenger arrival time, validates them,
     * and prints the earliest direct journey if available.
     *
     * @param in the input Scanner
     * @param directory the StationsDirectory
     *
     * Time complexity:
     * - Best case: O(1) when input validation fails
     * - Worst case: O(n), where n is the number of journeys serving the origin station
     */
    private static void executeFindJourney(Scanner in, StationsDirectory directory) {
        String origin = in.nextLine().trim();
        String dest = in.nextLine().trim();
        String time = in.nextLine().trim();

        if (!directory.hasStation(origin) || !directory.hasStation(dest)) {
            System.out.println("Invalid station.");
            return;
        }

        Time passengerTime = Time.parse(time);
        if (passengerTime == null) {
            System.out.println("Invalid time.");
            return;
        }

        if (!directory.isValidOrder(origin, dest)) {
            System.out.println("Stations are not in the correct order.");
            return;
        }

        String j = directory.findJourney(origin, dest, passengerTime);
        if (j == null) {
            System.out.println("No direct journey is available.");
        } else {
            printJourney(j, origin, dest, directory);
        }
    }

    /**
     * Reads origin, destination, and a departure time interval, validates them,
     * and prints the direct journey with the fewest stops if available.
     *
     * @param in the input Scanner
     * @param directory the StationsDirectory
     *
     * Time complexity:
     * - Best case: O(1) when input validation fails
     * - Worst case: O(n), where n is the number of journeys serving the origin station
     */
    private static void executeFindJourneyMinStops(Scanner in, StationsDirectory directory) {
        String origin = in.nextLine().trim();
        String dest = in.nextLine().trim();
        String timeLine = in.nextLine().trim();

        if (!directory.hasStation(origin) || !directory.hasStation(dest)) {
            System.out.println("Invalid station.");
            return;
        }

        String[] times = timeLine.split("\\s+");
        if (times.length != 2) {
            System.out.println("Invalid time.");
            return;
        }

        Time minTime = Time.parse(times[0]);
        Time maxTime = Time.parse(times[1]);
        if (minTime == null || maxTime == null) {
            System.out.println("Invalid time.");
            return;
        }

        if (minTime.isAfter(maxTime)) {
            System.out.println("Invalid interval time.");
            return;
        }

        if (!directory.isValidOrder(origin, dest)) {
            System.out.println("Stations are not in the correct order.");
            return;
        }

        String j = directory.findJourneyMinStops(origin, dest, minTime, maxTime);
        if (j == null) {
            System.out.println("No direct journey is available.");
        } else {
            printJourney(j, origin, dest, directory);
            System.out.println("Stops: " + directory.countStopsBetween(j, origin, dest));
        }
    }

    /**
     * Prints the formatted details of a selected journey between two stations.
     *
     * @param id the journey identifier
     * @param origin the departure station name
     * @param dest the arrival station name
     * @param dir the StationsDirectory
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(s + j + k) in case of hash collisions, where s is the number of stations, j is the number of journeys, and k is the number of stops in the journey
     */
    private static void printJourney(String id, String origin, String dest, StationsDirectory dir) {
        System.out.println("Journey: " + id);
        System.out.println("Departure station: " + dir.getStationName(origin));
        System.out.println("Departure time: " + dir.getTime(id, origin, true));
        System.out.println("Arrival station: " + dir.getStationName(dest));
        System.out.println("Arrival time: " + dir.getTime(id, dest, false));
    }

    /**
     * Reads a journey identifier from standard input and removes it from the network.
     *
     * @param in the input Scanner
     * @param directory the StationsDirectory
     *
     * Time complexity:
     * - Best case: O(1) when the journey does not exist
     * - Worst case: O(n * m + k), where n is the number of stops in the journey, m is the average departures per station, and k is the total number of registered journeys
     */
    private static void executeRemoveJourney(Scanner in, StationsDirectory directory) {
        String journeyId = in.nextLine().trim();
        if (directory.removeJourney(journeyId)) {
            System.out.println("Journey successfully removed.");
        } else {
            System.out.println("Invalid journey.");
        }
    }

    /**
     * Prints the termination messages upon exiting the application.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    private static void executeQuit() {
        System.out.println("Transportation network saved.");
        System.out.println("Goodbye.");
    }
}
