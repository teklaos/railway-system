package cars;

public class ToxicLiquidCar extends HeavyFreightCar implements Toxic, Liquid {
    private final String toxicLiqType;

    public ToxicLiquidCar(int netWeight) {
        super(netWeight);
        this.toxicLiqType = "Radioactive Waste";
    }

    @Override
    public String toString() {
        return super.superToString() + " - Toxic Liquid Railroad Car\nTransported Toxic Liquid: " + toxicLiqType +
                "\nGross Weight: " + getGrossWeight() + " kg";
    }
}
