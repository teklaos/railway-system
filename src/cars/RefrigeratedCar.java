package cars;

public class RefrigeratedCar extends BasicFreightCar implements RequiresElectricity {
    public RefrigeratedCar(int netWeight) {
        super(netWeight);
    }

    @Override
    public String toString() {
        return super.superToString() + " - Refrigerator railroad car\nGross weight: " + getGrossWeight() + " kg";
    }
}
