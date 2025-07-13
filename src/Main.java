import cars.PassengerCar;
import cars.RailroadCar;
import exceptions.MaxAmountExceeded;
import exceptions.MaxAmountGridExceeded;
import exceptions.MaxWeightExceeded;
import models.Locomotive;
import models.Station;
import models.TrainSet;

import java.util.*;

public class Main {
    private static final Scanner in = new Scanner(System.in);

    private static final List<TrainSet> trainSetList = new ArrayList<>();
    private static final List<Locomotive> locomotiveList = new ArrayList<>();
    private static final List<RailroadCar> railroadCarList = new ArrayList<>();
    private static final List<Station> stationList = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            try {
                System.out.println("""
                        Choose an action:
                        \t1. Train
                        \t2. Locomotive
                        \t3. Railroad Car
                        \t4. Station
                        \t0. Exit""");

                int choice = getIntInput();
                switch (choice) {
                    case 1 -> trainMenu();
                    case 2 -> locomotiveMenu();
                    case 3 -> railroadCarMenu();
                    case 4 -> stationMenu();
                    case 0 -> {
                        running = false;
                        for (TrainSet trainSet : trainSetList) {
                            trainSet.getThread().interrupt();
                            trainSet.interrupt();
                        }
                    }
                    default -> System.out.println("There is no such option");
                }
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void trainMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("""
                    Train:
                    \t1. Create
                    \t2. Delete
                    \t3. Depart
                    \t4. Add Railroad Car
                    \t5. Show All
                    \t0. Go Back""");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> createTrain();
                case 2 -> deleteTrain();
                case 3 -> departTrain();
                case 4 -> addRailroadCar();
                case 5 -> showAllTrains();
                case 0 -> back = true;
                default -> System.out.println("There is no such option");
            }
        }
    }

    public static void createTrain() {
        if (locomotiveList.isEmpty()) {
            System.out.println("There are no locomotives to assign a train to");
        } else {
            System.out.println("Choose a locomotive to assign:");
            for (Locomotive locomotive : locomotiveList) {
                System.out.println("\tLocomotive №" + locomotive.getLocomotiveId() +
                        " " + locomotive.getName());
            }

            int id;
            boolean locomotiveExists = false;
            while (!locomotiveExists) {
                id = getIntInput();
                for (Locomotive locomotive : locomotiveList) {
                    if (locomotive.getLocomotiveId() == id) {
                        TrainSet trainSet = new TrainSet(locomotive);
                        trainSetList.add(trainSet);
                        locomotiveExists = true;
                    }
                }
                if (!locomotiveExists) {
                    System.out.println("There is no such locomotive");
                }
            }
            System.out.println("Train was created");
        }
    }

    public static void deleteTrain() {
        if (trainSetList.isEmpty()) {
            System.out.println("There are no trains to delete");
        } else {
            System.out.println("Choose a train to delete:");
            for (TrainSet trainSet : trainSetList) {
                System.out.println(trainSet.toString());
            }

            int id;
            boolean trainExists = false;
            while (!trainExists) {
                id = getIntInput();
                for (int i = 0; i < trainSetList.size(); i++) {
                    if (trainSetList.get(i).getTrainSetId() == id) {
                        trainSetList.remove(trainSetList.get(i));
                        trainExists = true;
                    }
                }
                if (!trainExists) {
                    System.out.println("There is no such train");
                }
            }
            System.out.println("Train was deleted");
        }
    }

    public static void departTrain() {
        if (trainSetList.isEmpty()) {
            System.out.println("There are no trains to depart");
        } else {
            System.out.println("Choose a train to depart:");
            for (TrainSet trainSet : trainSetList) {
                System.out.println(trainSet.toString());
            }

            int id;
            boolean trainExists = false;
            while (!trainExists) {
                id = getIntInput();
                for (TrainSet trainSet : trainSetList) {
                    if (trainSet.getTrainSetId() == id) {
                        Locomotive locomotive = trainSet.getLocomotive();
                        if (locomotive.getSource() != null && locomotive.getDestination() != null) {
                            trainSet.depart(locomotive.getSource(), locomotive.getDestination());
                        } else {
                            System.out.println("The locomotive does not have a source or destination station");
                            return;
                        }
                        trainExists = true;
                    }
                }
                if (!trainExists) {
                    System.out.println("There is no such train");
                }
            }
            System.out.println("Train has just departed");
        }
    }

    public static void addRailroadCar() {
        if (trainSetList.isEmpty()) {
            System.out.println("There are no trains to add railroad cars to");
        } else if (railroadCarList.isEmpty()) {
            System.out.println("There are no railroad cars");
        } else {
            TrainSet ts = null;
            System.out.println("Choose a train set:");
            for (TrainSet trainSet : trainSetList) {
                System.out.println(trainSet.toString());
            }

            int id;
            boolean trainSetExists = false;
            while (!trainSetExists) {
                id = getIntInput();
                for (TrainSet trainSet : trainSetList) {
                    if (trainSet.getTrainSetId() == id) {
                        ts = trainSet;
                        trainSetExists = true;
                    }
                }
                if (!trainSetExists) {
                    System.out.println("There is no such train set");
                }
            }

            if (ts.getRailroadCars().size() == railroadCarList.size()) {
                System.out.println("This train set already has all the railroad cars");
                return;
            }

            System.out.println("Choose a railroad car:");
            for (RailroadCar railroadCar : railroadCarList) {
                if (!ts.getRailroadCars().contains(railroadCar)) {
                    System.out.println(railroadCar);
                }
            }
            boolean railroadCarExists = false;
            while (!railroadCarExists) {
                id = getIntInput();
                for (RailroadCar railroadCar : railroadCarList) {
                    if (railroadCar.getRailroadCarId() == id) {
                        if (ts.getRailroadCars().contains(railroadCar)) {
                            System.out.println("This railroad car is already in the train set");
                            return;
                        }
                        try {
                            ts.addRailroadCar(railroadCar);
                        } catch (MaxAmountExceeded | MaxAmountGridExceeded | MaxWeightExceeded ex) {
                            System.out.println(ex.getMessage());
                        }
                        railroadCarExists = true;
                    }
                }
                if (!railroadCarExists) {
                    System.out.println("There is no such railroad car");
                }
            }
            System.out.println("Railroad cars were added to the train set");
        }
    }

    public static void showAllTrains() {
        if (trainSetList.isEmpty()) {
            System.out.println("There are no trains");
        } else {
            for (TrainSet trainSet : trainSetList) {
                System.out.println(trainSet + "\n" + trainSet.getLocomotive());
                System.out.println("Current Source: " + trainSet.getCurrentSource() +
                        "\nCurrent Destination: " + trainSet.getCurrentDestination());
            }
        }
    }

    public static void locomotiveMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    Locomotive:
                    \t1. Create
                    \t2. Delete
                    \t3. Set Route
                    \t4. Show All
                    \t0. Go Back""");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> createLocomotive();
                case 2 -> deleteLocomotive();
                case 3 -> setRoute();
                case 4 -> showAllLocomotives();
                case 0 -> back = true;
                default -> System.out.println("There is no such option");
            }
        }
    }

    public static void createLocomotive() {
        if (stationList.isEmpty()) {
            System.out.println("There are no stations created");
        } else {
            System.out.println("Enter the locomotive name (0 for default):");
            String name = getStringInput();
            if (name.equals("0")) {
                name = "L" + (locomotiveList.size() + 1);
            }

            System.out.println("Enter the locomotive speed (0 for default):");
            double speed = getDoubleInput();
            if (speed == 0) {
                speed = 100;
            }

            System.out.println("Enter the maximum number of railroad cars " +
                    "that can be carried by the locomotive (0 for default):");
            int maxCars = getIntInput();
            if (maxCars == 0) {
                maxCars = 12;
            }

            System.out.println("Enter the maximum number of railroad cars " +
                    "requiring connection to the power grid in the locomotive (0 for default):");
            int maxElectricityCars = getIntInput();
            if (maxElectricityCars == 0) {
                maxElectricityCars = 7;
            }

            System.out.println("Enter the maximum weight of railroad cars " +
                    "that can be carried by the locomotive (0 for default):");
            int maxWeight = getIntInput();
            if (maxWeight == 0) {
                maxWeight = 1_000_000;
            }

            Station home = null;
            System.out.println("Choose the home station of the locomotive:");
            for (Station station : stationList) {
                System.out.println("\t" + station.toString());
            }

            String homeName;
            boolean existsStation = false;
            while (!existsStation) {
                homeName = getStringInput();
                for (Station station : stationList) {
                    if (station.getName().equals(homeName)) {
                        home = station;
                        existsStation = true;
                    }
                }
                if (!existsStation) {
                    System.out.println("There is no such station");
                }
            }

            Locomotive locomotive = new Locomotive(name, speed,
                    maxCars, maxElectricityCars, maxWeight,
                    home, null, null);
            System.out.println("Locomotive №" + locomotive.getLocomotiveId() + " was created");
            locomotiveList.add(locomotive);
        }
    }

    public static void deleteLocomotive() {
        if (locomotiveList.isEmpty()) {
            System.out.println("There are no locomotives to delete");
        } else {
            System.out.println("Choose a locomotive to delete:");
            for (Locomotive locomotive : locomotiveList) {
                System.out.println("\tLocomotive №" + locomotive.getLocomotiveId() +
                        ", " + locomotive.getName());
            }

            int id;
            boolean locomotiveExists = false;
            while (!locomotiveExists) {
                id = getIntInput();
                for (int i = 0; i < locomotiveList.size(); i++) {
                    if (locomotiveList.get(i).getLocomotiveId() == id) {
                        locomotiveList.remove(locomotiveList.get(i));
                        locomotiveExists = true;
                    }
                }
                if (!locomotiveExists)
                    System.out.println("There is no such locomotive");
            }
            System.out.println("Locomotive was deleted");
        }
    }

    public static void setRoute() {
        if (locomotiveList.isEmpty()) {
            System.out.println("There are no locomotives to set route for");
        } else if (stationList.size() < 2) {
            System.out.println("There are too few stations to set a route");
        } else {
            Locomotive l = null;
            System.out.println("Choose a locomotive for which to create a route:");
            for (Locomotive locomotive : locomotiveList) {
                System.out.println("\tLocomotive №" + locomotive.getLocomotiveId() +
                        ", " + locomotive.getName());
            }

            int id;
            boolean existsLocomotive = false;
            while (!existsLocomotive) {
                id = getIntInput();
                for (Locomotive locomotive : locomotiveList) {
                    if (locomotive.getLocomotiveId() == id) {
                        l = locomotive;
                        existsLocomotive = true;
                    }
                }
                if (!existsLocomotive)
                    System.out.println("There is no such locomotive");
            }

            System.out.println("Choose a source station:");
            for (Station station : stationList) {
                System.out.println(station);
            }

            String name;
            boolean existsStation = false;
            while (!existsStation) {
                name = getStringInput();
                for (Station station : stationList) {
                    if (station.getName().equals(name)) {
                        l.setSource(station);
                        existsStation = true;
                    }
                }
                if (!existsStation)
                    System.out.println("There is no such station");
            }

            System.out.println("Choose a destination station:");
            for (Station station : stationList) {
                if (station != l.getSource()) {
                    System.out.println(station);
                }
            }

            boolean foundValidStation = false;
            while (!foundValidStation) {
                name = getStringInput();
                existsStation = false;

                for (Station station : stationList) {
                    if (station.getName().equals(name)) {
                        existsStation = true;
                        if (station == l.getSource()) {
                            System.out.println("Cannot set the destination station to " +
                                    "be the same as the source station");
                        } else {
                            l.setDestination(station);
                            foundValidStation = true;
                        }
                        break;
                    }
                }
                if (!existsStation)
                    System.out.println("There is no such station");
            }
            System.out.println("Route was set");
        }
    }

    public static void showAllLocomotives() {
        if (locomotiveList.isEmpty()) {
            System.out.println("There are no locomotives");
        } else {
            for (Locomotive locomotive : locomotiveList) {
                System.out.println(locomotive);
            }
        }
    }

    public static void railroadCarMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    Railroad Car:
                    \t1. Create
                    \t2. Delete
                    \t3. Show All
                    \t0. Go Back""");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> createRailroadCar();
                case 2 -> deleteRailroadCar();
                case 3 -> showAllRailroadCars();
                case 0 -> back = true;
                default -> System.out.println("There is no such option");
            }
        }
    }

    public static void createRailroadCar() {
        RailroadCar railroadCar = new PassengerCar();
        railroadCarList.add(railroadCar);
        System.out.println("Passenger car was created");
    }

    public static void deleteRailroadCar() {
        if (railroadCarList.isEmpty()) {
            System.out.println("There are no railroad cars to delete");
        } else {
            System.out.println("Choose a railroad car to delete:");
            for (RailroadCar railroadCar : railroadCarList) {
                System.out.println(railroadCar);
            }

            int id;
            boolean existsRailroadCar = false;
            while (!existsRailroadCar) {
                id = getIntInput();
                for (int i = 0; i < railroadCarList.size(); i++) {
                    if (railroadCarList.get(i).getRailroadCarId() == id) {
                        railroadCarList.remove(railroadCarList.get(i));
                        existsRailroadCar = true;
                    }
                }
                if (!existsRailroadCar)
                    System.out.println("There is no such railroad car");
            }
            System.out.println("Passenger car was deleted");
        }
    }

    public static void showAllRailroadCars() {
        if (railroadCarList.isEmpty()) {
            System.out.println("There are no railroad cars");
        } else {
            for (RailroadCar railroadCar : railroadCarList) {
                System.out.println(railroadCar);
            }
        }
    }

    public static void stationMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("""
                    Station:
                    \t1. Create
                    \t2. Delete
                    \t3. Connect
                    \t4. Disconnect
                    \t5. Show All
                    \t0. Go Back""");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> createStation();
                case 2 -> deleteStation();
                case 3 -> connectStation();
                case 4 -> disconnectStation();
                case 5 -> showAllStations();
                case 0 -> back = true;
                default -> System.out.println("There is no such option");
            }
        }
    }

    public static void createStation() {
        System.out.println("Enter a name for a station:");

        String name = "";
        boolean foundDuplicate = true;
        while (foundDuplicate) {
            foundDuplicate = false;
            name = getStringInput();
            for (Station s : stationList) {
                if (name.equals(s.getName())) {
                    foundDuplicate = true;
                    break;
                }
            }
            if (foundDuplicate) {
                System.out.println("This name is already taken");
            }
        }

        Station station = new Station(name);
        System.out.println("Station " + station.getName() + " was created");
        stationList.add(station);
    }

    public static void deleteStation() {
        Station s = null;
        if (stationList.isEmpty()) {
            System.out.println("There are no stations to delete");
        } else {
            System.out.println("Choose a station to delete:");
            for (Station station : stationList) {
                System.out.println("\t" + station);
            }

            String name;
            boolean existsStation = false;
            while (!existsStation) {
                name = getStringInput();
                for (int i = 0; i < stationList.size(); i++) {
                    if (stationList.get(i).getName().equals(name)) {
                        s = stationList.get(i);
                        stationList.remove(s);
                        existsStation = true;
                    }
                }
                if (!existsStation) {
                    System.out.println("There is no such station");
                }
            }

            for (Map.Entry<Station, Integer> entry : s.getConnectionMap().entrySet()) {
                entry.getKey().disconnect(s);
            }

            System.out.println("Station was deleted");
        }
    }

    public static void connectStation() {
        if (stationList.size() < 2) {
            System.out.println("There are too few stations");
        } else {
            Station s1 = null, s2 = null;
            System.out.println("Choose a station:");
            for (Station station : stationList) {
                System.out.println("\t" + station);
            }

            String name;
            boolean existsStation = false;
            while (!existsStation) {
                name = getStringInput();
                for (Station station : stationList) {
                    if (station.getName().equals(name)) {
                        s1 = station;
                        existsStation = true;
                    }
                }
                if (!existsStation) {
                    System.out.println("There is no such station");
                }
            }

            System.out.println("Choose a station to connect to:");
            for (Station station : stationList) {
                if (station != s1) {
                    System.out.println("\t" + station);
                }
            }

            boolean foundValidStation = false;
            while (!foundValidStation) {
                name = getStringInput();
                existsStation = false;

                for (Station station : stationList) {
                    if (station.getName().equals(name)) {
                        existsStation = true;
                        if (station == s1) {
                            System.out.println("Cannot connect a station to itself");
                        } else {
                            s2 = station;
                            foundValidStation = true;
                        }
                        break;
                    }
                }
                if (!existsStation) {
                    System.out.println("There is no such station");
                }
            }

            System.out.println("Enter a distance between the stations:");
            int dist = getIntInput();

            s1.connect(s2, dist);
            s1.showConnection();
        }
    }

    public static void disconnectStation() {
        if (stationList.size() < 2) {
            System.out.println("There are too few stations");
        } else {
            Station s = null;
            System.out.println("Choose a station:");
            for (Station station : stationList) {
                System.out.println("\t" + station);
            }

            String name;
            boolean existsStation = false;
            while (!existsStation) {
                name = getStringInput();
                for (Station station : stationList) {
                    if (station.getName().equals(name)) {
                        s = station;
                        existsStation = true;
                    }
                }
                if (!existsStation) {
                    System.out.println("There is no such station");
                }
            }

            if (s.getConnectionMap().isEmpty()) {
                System.out.println("This station is not connected to any other station");
                return;
            }

            System.out.println("Choose a station to disconnect from (0 for all):");
            for (Station station : stationList) {
                if (s != station) {
                    System.out.println("\t" + station);
                }
            }

            existsStation = false;
            while (!existsStation) {
                name = getStringInput();
                if (name.equals("0")) {
                    s.disconnectAll();
                    break;
                }
                for (Station station : stationList) {
                    if (station.getName().equals(name)) {
                        try {
                            s.disconnect(station);
                        } catch (NoSuchElementException ex) {
                            System.out.println("There is no such station to disconnect from");
                        }
                        existsStation = true;
                    }
                }
            }
            s.showConnection();
        }
    }

    public static void showAllStations() {
        if (stationList.isEmpty()) {
            System.out.println("There are no stations");
        }
        for (Station station : stationList) {
            station.showConnection();
        }
    }

    public static int getIntInput() {
        while (true) {
            try {
                int result = in.nextInt();
                in.nextLine();
                return result;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input");
                in.nextLine();
            }
        }
    }

    public static String getStringInput() {
        String input;
        while (true) {
            input = in.nextLine();
            if (!input.isBlank()) {
                return input.trim();
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    public static double getDoubleInput() {
        while (true) {
            try {
                double result = in.nextDouble();
                in.nextLine();
                return result;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input");
                in.nextLine();
            }
        }
    }
}