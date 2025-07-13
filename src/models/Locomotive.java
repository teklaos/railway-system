package models;

import cars.RailroadCar;
import cars.RequiresElectricity;
import exceptions.MaxAmountExceeded;
import exceptions.MaxAmountGridExceeded;
import exceptions.MaxWeightExceeded;

import java.util.ArrayList;

public class Locomotive {
    private final int locomotiveId;
    private static int counter = 9010;
    private final String name;
    private double speed;
    private Station source, destination;
    private Station home;
    private final ArrayList<RailroadCar> railroadCars;
    private final int maxCars;
    private final int maxElectricityCars;
    private int maxCounter = 0;
    private final int maxWeight;

    public Locomotive(String name, double speed) {
        this.locomotiveId = counter++;
        this.name = name;
        this.speed = speed;
        this.railroadCars = new ArrayList<>();
        this.maxCars = 12;
        this.maxElectricityCars = 7;
        this.maxWeight = 1_000_000;
    }

    public Locomotive(String name, double speed, Station home, Station source, Station destination) {
        this(name, speed);
        setStations(home, source, destination);
    }

    public Locomotive(String name, double speed, int maxCars, int maxElectricityCars, int maxWeight) {
        this.locomotiveId = counter++;
        this.name = name;
        this.speed = speed;
        this.railroadCars = new ArrayList<>();
        this.maxCars = maxCars;
        this.maxElectricityCars = maxElectricityCars;
        this.maxWeight = maxWeight;
    }

    public Locomotive(String name, double speed,
                      int maxCars, int maxElectricityCars, int maxWeight,
                      Station home, Station source, Station destination) {
        this(name, speed, maxCars, maxElectricityCars, maxWeight);
        setStations(home, source, destination);
    }

    public int getLocomotiveId() {
        return locomotiveId;
    }

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Station getSource() {
        return source;
    }

    public void setSource(Station source) {
        this.source = source;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public Station getHome() {
        return home;
    }

    public void setStations(Station home, Station source, Station destination) {
        this.home = home;
        this.source = source;
        this.destination = destination;
    }

    public synchronized void changeSpeed() {
        if (Math.random() >= 0.50)
            speed *= 1.03;
        else
            speed *= 0.97;
    }

    public ArrayList<RailroadCar> getRailroadCars() {
        return railroadCars;
    }

    public void addRailroadCar(RailroadCar railroadCar)
            throws MaxAmountExceeded, MaxAmountGridExceeded, MaxWeightExceeded {
        int weightSum = 0;
        for (RailroadCar rc : railroadCars)
            weightSum += rc.getGrossWeight();
        weightSum += railroadCar.getGrossWeight();

        if (railroadCars.size() < maxCars) {
            if (maxCounter < maxElectricityCars) {
                if (weightSum < maxWeight) {
                    if (railroadCar instanceof RequiresElectricity) maxCounter++;
                    railroadCars.add(railroadCar);
                } else {
                    throw new MaxWeightExceeded("The maximum amount of weight is exceeded");
                }
            } else throw new MaxAmountGridExceeded("The maximum amount of railroad cars " +
                    "requiring connection to the electricity grid is exceeded");
        } else throw new MaxAmountExceeded("The maximum amount of railroad cars is exceeded");
    }

    @Override
    public String toString() {
        return "Locomotive №" + locomotiveId + "\nName: " + name + "\nSpeed: " + speed + " km/h\nHome: " + home +
                "\nFrom " + source + " to " + destination;
    }
}
