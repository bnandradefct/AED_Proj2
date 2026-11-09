import java.io.*;
import java.util.Scanner;
public class Main {
    private static final String DATA_FILE = "directory.dat";

    public static void main(String[] args) {
        StationsDirectory directory = loadDirectory();

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
                case "minTransfers":
                    executeMinTransfers(in, directory);
                    break;
                case "quit":
                    saveDirectory(directory);
                    executeQuit();
                    running = false;
                    break;
                default:
                    break;
            }
        }
        in.close();
    }

    private static StationsDirectory loadDirectory() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                System.out.println("Transportation network restored successfully.");
                return (StationsDirectory) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Transportation network not found.");
        System.out.println("Loading line definition. %d stations loaded.", numberStations);
        return new StationsDirectoryClass();
    }

    private static void saveDirectory(StationsDirectory directory) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
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

    private static void executeAddJourney(){
    }

    private static void executeFindJourney(){
    }

    private static void executeFindJourneyMinStops(){
    }

    private static void executeRemoveJourney(){
    }

    private static void executeMinTransfers(){
    }

    private static void executeQuit(){
    System.out.println("Transportation network saved.");
    System.out.println("Goodbye.");
    }
}

