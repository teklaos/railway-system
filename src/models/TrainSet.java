package models;

import cars.RailroadCar;
import exceptions.MaxAmountExceeded;
import exceptions.MaxAmountGridExceeded;
import exceptions.MaxWeightExceeded;
import exceptions.RailroadHazard;

import java.util.*;

public class TrainSet extends Thread {
    private final int trainSetId;
    private static int counter = 3810;
    private final Locomotive locomotive;
    private Station source, destination;
    private Station currentSource, currentDestination;
    private List<Station> currentPath;
    private int currentDistance;
    private Thread thread;

    public TrainSet(Locomotive locomotive) {
        this.trainSetId = counter++;
        this.locomotive = locomotive;
        this.source = locomotive.getSource();
        this.destination = locomotive.getDestination();
        this.currentSource = locomotive.getHome();
        this.currentDestination = null;
        this.currentPath = new LinkedList<>();
        this.currentDistance = 0;
        this.thread = new Thread(() -> {
            while (!thread.isInterrupted()) {
                try {
                    getLocomotive().changeSpeed();
                    checkSpeed();
                    Thread.sleep(1000);
                } catch (RailroadHazard ex) {
                    System.out.println(ex.getMessage());
                } catch (InterruptedException ex) {
                    return;
                }
            }
        });
    }

    public int getTrainSetId() {
        return trainSetId;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public void addRailroadCar(RailroadCar railroadCar)
            throws MaxAmountExceeded, MaxAmountGridExceeded, MaxWeightExceeded {
        locomotive.addRailroadCar(railroadCar);
    }

    public ArrayList<RailroadCar> getRailroadCars() {
        return locomotive.getRailroadCars();
    }

    public void setSource(Station source) {
        locomotive.setSource(source);
        this.source = source;
    }

    public void setDestination(Station destination) {
        locomotive.setDestination(destination);
        this.destination = destination;
    }

    public Station getCurrentSource() {
        return currentSource;
    }

    public Station getCurrentDestination() {
        return currentDestination;
    }

    public Thread getThread() {
        return thread;
    }

    public void checkSpeed() throws RailroadHazard {
        if (getLocomotive().getSpeed() > 200) {
            getLocomotive().setSpeed(150);
            throw new RailroadHazard(this + " speed was decreased to 150 km/h");
        }
    }

    public void depart(Station source, Station destination) {
        setSource(source);
        setDestination(destination);
        this.thread.start();
        this.start();
    }

    @Override
    public void run() {
        try {
            findPath(source);
            currentPath = destination.getPath();
            currentPath.add(destination);
            currentSource = source;
            for (int i = 1; i < currentPath.size(); i++) {
                findPath(currentSource);
                currentDestination = currentPath.get(i);
                currentDistance = currentDestination.getDistance();
                while (currentDistance > 0) {
                    currentDistance -= (int) locomotive.getSpeed() / 4; // Scale: 1 second here - 15 min IRL
                    Thread.sleep(1000);
                }
                currentSource = currentPath.get(i);
                if (currentSource == currentPath.get(currentPath.size() - 2)) {
                    Thread.sleep(2000);
                }
            }
            Thread.sleep(30000);
        } catch (InterruptedException ignore) {
        }
    }

    public synchronized void findPath(Station newSource) {
        Set<Station> evaluated = new HashSet<>();
        Set<Station> notEvaluated = new HashSet<>();

        source = newSource;
        source.setDistance(0);
        notEvaluated.add(source);

        while (!notEvaluated.isEmpty()) {
            Station shortestDistanceStation = null;
            int shortestDistance = Integer.MAX_VALUE;
            for (Station station : notEvaluated) {
                int stationDistance = station.getDistance();
                if (stationDistance < shortestDistance) {
                    shortestDistance = stationDistance;
                    shortestDistanceStation = station;
                }
            }
            Station current = shortestDistanceStation;
            notEvaluated.remove(current);
            if (current != null) {
                for (Map.Entry<Station, Integer> entry : current.getConnectionMap().entrySet()) {
                    Station connectedStation = entry.getKey();
                    int distance = entry.getValue();
                    if (!evaluated.contains(connectedStation)) {
                        int sourceDistance = current.getDistance();
                        if (sourceDistance + distance < connectedStation.getDistance()) {
                            connectedStation.setDistance(sourceDistance + distance);
                            List<Station> shortestPath = new LinkedList<>(current.getPath());
                            shortestPath.add(current);
                            connectedStation.setPath(shortestPath);
                            notEvaluated.add(connectedStation);
                        }
                    }
                }
            }
            evaluated.add(current);
        }
    }

    @Override
    public String toString() {
        return "Train set №" + trainSetId;
    }
}
