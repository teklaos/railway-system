import cars.PassengerCar;
import models.Locomotive;
import models.Station;
import models.TrainSet;

public class Presentation {
    public static void main(String[] args) {
        Station s1 = new Station("s1");
        Station s2 = new Station("s2");
        Station s3 = new Station("s3");

        s1.connect(s2, 450);
        s2.connect(s3, 450);

        Locomotive locomotive = new Locomotive("L1", 150, 12, 7, 100000, s2, s1, s3);
        TrainSet trainSet = new TrainSet(locomotive);
        PassengerCar railroadCar = new PassengerCar(40);
        railroadCar.throwOutAPassenger();

        trainSet.depart(s1, s3);
        System.out.println(trainSet + "\n" + trainSet.getLocomotive());
        System.out.println("Current Source: " + trainSet.getCurrentSource() +
                "\nCurrent Destination: " + trainSet.getCurrentDestination());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        trainSet.interrupt();
        trainSet.getThread().interrupt();
    }
}
