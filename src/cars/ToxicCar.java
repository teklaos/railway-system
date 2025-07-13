package cars;

public class ToxicCar extends HeavyFreightCar implements Toxic {
    private final String toxicMatType;

    public ToxicCar(int netWeight) {
        super(netWeight);
        this.toxicMatType = "Uranium-235";
    }

    @Override
    public String toString() {
        return super.superToString() + " - Toxic Railroad Car\nTransported Toxic Material: " + toxicMatType +
                "\nGross Weight: " + getGrossWeight() + " kg";
    }
}
