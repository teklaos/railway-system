package models;

import java.util.*;

public class Station {
    private final String name;
    private int distance;
    private List<Station> path = new LinkedList<>();
    private final Map<Station, Integer> connectionMap = new HashMap<>();

    public Station(String name) {
        this.name = name;
        this.distance = Integer.MAX_VALUE;
    }

    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }

    public List<Station> getPath() {
        return path;
    }

    public void setPath(List<Station> path) {
        this.path = path;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Map<Station, Integer> getConnectionMap() {
        return connectionMap;
    }

    public void connect(Station station, int dist) {
        connectionMap.put(station, dist);
        station.connectionMap.put(this, dist);
    }

    public void disconnectAll() {
        for (Map.Entry<Station, Integer> entry : connectionMap.entrySet())
            entry.getKey().connectionMap.remove(this);
        connectionMap.clear();
    }

    public void disconnect(Station station) {
        if (connectionMap.containsKey(station) && station.connectionMap.containsKey(this)) {
            connectionMap.remove(station);
            station.connectionMap.remove(this);
        } else throw new NoSuchElementException();
    }

    public void showConnection() {
        System.out.print(this + " is connected to:");
        if (connectionMap.isEmpty()) {
            System.out.println(" - ");
        } else {
            System.out.println();
            for (Map.Entry<Station, Integer> entry : connectionMap.entrySet())
                System.out.println(entry.getKey() + " - " + entry.getValue() + " km");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
