package cars;

public class HeavyFreightCar extends RailroadCar {
    public HeavyFreightCar(int netWeight) {
        super(netWeight);
    }

    public String superToString() {
        return super.toString();
    }

    @Override
    public String toString() {
        return super.toString() + " - Heavy freight railroad car\nGross weight: " + getGrossWeight() + " kg";
    }
}
