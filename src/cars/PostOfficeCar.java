package cars;

public class PostOfficeCar extends RailroadCar implements RequiresElectricity {
    private final int numberOfLetters;

    public PostOfficeCar(int numberOfLetters) {
        super(numberOfLetters / 2);
        this.numberOfLetters = numberOfLetters;
    }

    @Override
    public String toString() {
        return super.toString() + " - Post office railroad car\nNumber of letters: " + numberOfLetters +
                "\nGross weight: " + getGrossWeight() + " kg";
    }
}
