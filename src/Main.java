import dataStructures.List;
import dataStructures.SinglyLinkedList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "directory.dat";

    public static void main(String[] args) {
        StationsDirectory directory = loadDirectory(args);

        Scanner in = new Scanner(System.in);
        boolean running = true;

        while (running && in.hasNextLine()) {
            String command = in.nextLine().trim();
            if (command.isEmpty()) {
                continue;
            }

            switch (command.toLowerCase()) {
                case "help":
                    executeHelp();
                    break;
                case "addjourney":
                    executeAddJourney(in, directory);
                    break;
                case "findjourney":
                    executeFindJourney(in, directory);
                    break;
                case "findjourneyminstops":
                    executeFindJourneyMinStops(in, directory);
                    break;
                case "removejourney":
                    executeRemoveJourney(in, directory);
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

    /**
     * Gere o ciclo de vida inicial da aplicação segundo a Secção 3 do enunciado:
     * 1. Se o ficheiro de serialização existir, restaura o estado.
     * 2. Senão, se for fornecido ficheiro de linha legível, carrega a rede vazia.
     * 3. Se nenhum estiver disponível, termina a execução com a mensagem de erro.
     */
    private static StationsDirectory loadDirectory(String[] args) {
        File file = new File(DATA_FILE);

        // 1. Restaurar de serialização se o ficheiro existir
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                StationsDirectory directory = (StationsDirectory) ois.readObject();
                System.out.println("Transportation network restored successfully.");
                return directory;
            } catch (IOException | ClassNotFoundException e) {
                // Se falhar a leitura do ficheiro de serialização, prossegue para os argumentos
            }
        }

        // 2. Carregar a partir do ficheiro de definição da linha (se passado por argumento)
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
                    // Erro na leitura do ficheiro de texto
                }
            }
        }

        // 3. Nem serialização nem linha disponíveis
        System.out.println("No line definition.");
        System.exit(0);
        return null;
    }

    private static void saveDirectory(StationsDirectory directory) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Na Fase 1 o comando minTransfers não existe e não pode aparecer no help.
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
     * Lê todo o input associado à viagem antes de verificar se o ID já existe.
     */
    private static void executeAddJourney(Scanner in, StationsDirectory directory) {
        String journeyId = in.nextLine().trim();
        int n = Integer.parseInt(in.nextLine().trim());

        List<StopData> stops = new SinglyLinkedList<StopData>();

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

            stops.addLast(new StopData(stationName, arr, dep));
        }

        if (directory.addJourney(journeyId, stops)) {
            System.out.println("Journey successfully added.");
        } else {
            System.out.println("Journey already exists.");
        }
    }

    /**
     * Validações em ordem:
     * 1. Invalid station.
     * 2. Invalid time.
     * 3. Stations are not in the correct order.
     */
    private static void executeFindJourney(Scanner in, StationsDirectory directory) {
        String origin = in.nextLine().trim();
        String dest = in.nextLine().trim();
        String time = in.nextLine().trim();

        if (!directory.hasStation(origin) || !directory.hasStation(dest)) {
            System.out.println("Invalid station.");
        } else if (!directory.isValidTime(time)) {
            System.out.println("Invalid time.");
        } else if (!directory.isValidOrder(origin, dest)) {
            System.out.println("Stations are not in the correct order.");
        } else {
            JourneyResult result = directory.findJourney(origin, dest, time);
            if (result == null) {
                System.out.println("No direct journey is available.");
            } else {
                printJourney(result);
            }
        }
    }

    /**
     * Validações em ordem:
     * 1. Invalid station.
     * 2. Invalid time.
     * 3. Invalid interval time.
     * 4. Stations are not in the correct order.
     */
    private static void executeFindJourneyMinStops(Scanner in, StationsDirectory directory) {
        String origin = in.nextLine().trim();
        String dest = in.nextLine().trim();
        String timeLine = in.nextLine().trim();

        // Os dois tempos vêm na mesma linha, separados por espaço
        String[] times = timeLine.split("\\s+");
        String minTime = times.length > 0 ? times[0] : "";
        String maxTime = times.length > 1 ? times[1] : "";

        if (!directory.hasStation(origin) || !directory.hasStation(dest)) {
            System.out.println("Invalid station.");
        } else if (times.length != 2 || !directory.isValidTime(minTime) || !directory.isValidTime(maxTime)) {
            System.out.println("Invalid time.");
        } else if (!directory.isValidInterval(minTime, maxTime)) {
            System.out.println("Invalid interval time.");
        } else if (!directory.isValidOrder(origin, dest)) {
            System.out.println("Stations are not in the correct order.");
        } else {
            JourneyResult result = directory.findJourneyMinStops(origin, dest, minTime, maxTime);
            if (result == null) {
                System.out.println("No direct journey is available.");
            } else {
                printJourney(result);
                System.out.println("Stops: " + result.getStops());
            }
        }
    }

    private static void printJourney(JourneyResult result) {
        System.out.println("Journey: " + result.getJourneyId());
        System.out.println("Departure station: " + result.getOriginName());
        System.out.println("Departure time: " + result.getDepartureTime());
        System.out.println("Arrival station: " + result.getDestinationName());
        System.out.println("Arrival time: " + result.getArrivalTime());
    }

    private static void executeRemoveJourney(Scanner in, StationsDirectory directory) {
        String journeyId = in.nextLine().trim();
        if (directory.removeJourney(journeyId)) {
            System.out.println("Journey successfully removed.");
        } else {
            System.out.println("Invalid journey.");
        }
    }

    private static void executeQuit() {
        System.out.println("Transportation network saved.");
        System.out.println("Goodbye.");
    }
}