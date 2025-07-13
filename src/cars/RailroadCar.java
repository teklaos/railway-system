package cars;

public abstract class RailroadCar {
    private final int railroadCarId;
    private static int counter = 1000;
    private int tareWeight = 30_000;
    private int netWeight;

    public RailroadCar() {
        this.railroadCarId = counter++;
    }

    public RailroadCar(int netWeight) {
        this.railroadCarId = counter++;
        this.netWeight = netWeight;
    }

    public RailroadCar(int netWeight, int tareWeight) {
        this.railroadCarId = counter++;
        this.tareWeight = tareWeight;
        this.netWeight = netWeight;
    }

    public int getRailroadCarId() {
        return railroadCarId;
    }

    public int getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(int netWeight) {
        this.netWeight = netWeight;
    }

    public int getGrossWeight() {
        return netWeight + tareWeight;
    }

    @Override
    public String toString() {
        return "Railroad car №" + railroadCarId;
    }
}
