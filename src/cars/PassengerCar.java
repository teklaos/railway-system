package cars;

public class PassengerCar extends RailroadCar implements RequiresElectricity {
    private int numberOfPassengers;

    public PassengerCar() {
        super(40 * 65);
        this.numberOfPassengers = 40;
    }

    public PassengerCar(int numberOfPassengers) {
        super(numberOfPassengers * 65);
        this.numberOfPassengers = numberOfPassengers;
    }

    public void throwOutAPassenger() {
        if (numberOfPassengers > 0) {
            numberOfPassengers--;
            setNetWeight(getNetWeight() - 65);
        } else System.out.println("There are no passengers to throw out :(");
    }

    @Override
    public String toString() {
        return super.toString() + " - Passenger railroad car\nNumber of passengers: " + numberOfPassengers +
                "\nGross weight: " + getGrossWeight() + " kg";
    }
}
