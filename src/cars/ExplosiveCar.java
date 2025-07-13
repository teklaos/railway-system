package cars;

public class ExplosiveCar extends HeavyFreightCar {
    private final String explosivesType;

    public ExplosiveCar(int netWeight) {
        super(netWeight);
        this.explosivesType = "TNT";
    }

    public String toString() {
        return super.superToString() + " - Explosive railroad car\nTransported explosives: " + explosivesType +
                "\nGross weight: " + getGrossWeight() + " kg";
    }
}
