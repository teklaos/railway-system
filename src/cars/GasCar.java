package cars;

public class GasCar extends BasicFreightCar {
    private final String gasType;

    public GasCar(int netWeight) {
        super(netWeight);
        this.gasType = "Oxygen";
    }

    @Override
    public String toString() {
        return super.superToString() + " - Gas tank railroad car\nTransported gas: " + gasType +
                "\nGross weight: " + getGrossWeight() + " kg";
    }
}
