package cars;

public class BasicFreightCar extends RailroadCar {
    public BasicFreightCar(int netWeight) {
        super(netWeight);
    }

    public String superToString() {
        return super.toString();
    }

    @Override
    public String toString() {
        return super.toString() + " - Freight railroad car\nGross weight: " + getGrossWeight() + " kg";
    }
}
