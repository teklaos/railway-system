package cars;

public class LiquidCar extends BasicFreightCar implements Liquid {
    private final String liquidType;

    public LiquidCar(int netWeight) {
        super(netWeight);
        this.liquidType = "Water";
    }

    @Override
    public String toString() {
        return super.superToString() + " - Liquid tank railroad car\nTransported liquid: " + liquidType +
                "\nGross Weight: " + getGrossWeight() + " kg";
    }
}
