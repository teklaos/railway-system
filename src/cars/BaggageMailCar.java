package cars;

public class BaggageMailCar extends RailroadCar {
    private final int numberOfLuggage;

    public BaggageMailCar(int numberOfLuggage) {
        super(numberOfLuggage * 25);
        this.numberOfLuggage = numberOfLuggage;
    }

    @Override
    public String toString() {
        return super.toString() + " - Baggage and mail railroad car\nNumber of luggage: " + numberOfLuggage +
                "\nGross weight: " + getGrossWeight() + " kg";
    }
}
