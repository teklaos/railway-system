import cars.RailroadCar;
import models.TrainSet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class AppState extends Thread {
    private final List<TrainSet> trainSets;

    public AppState(List<TrainSet> trainSets) {
        this.trainSets = trainSets;
    }

    @Override
    public synchronized void run() {
        while (!isInterrupted()) {
            try (PrintWriter pw = new PrintWriter("AppState.txt")) {
                for (TrainSet trainSet : trainSets) {
                    pw.println("---------------\n" + trainSet + ":\n" + "---------------");
                    pw.println(trainSet.getLocomotive() + "\n");
                    trainSet.getRailroadCars().sort(Comparator.comparingInt(RailroadCar::getGrossWeight));
                    for (RailroadCar car : trainSet.getLocomotive().getRailroadCars())
                        pw.println(car + "\n");
                }
                pw.close();
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                return;
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }
}
