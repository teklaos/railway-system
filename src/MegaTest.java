import cars.*;
import exceptions.MaxAmountExceeded;
import exceptions.MaxAmountGridExceeded;
import exceptions.MaxWeightExceeded;
import models.Locomotive;
import models.Station;
import models.TrainSet;

import java.util.ArrayList;
import java.util.List;

public class MegaTest {
    public static void main(String[] args) {
        List<Station> stationList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Station station = new Station("Station №" + i);
            stationList.add(station);
        }

        for (int i = 0; i < stationList.size() - 3; i++) {
            stationList.get(i).connect(stationList.get(i + 1), 100 * ((int) (Math.random() * 101)));
            stationList.get(i).connect(stationList.get(i + 2), 100 * ((int) (Math.random() * 101)));
            if (Math.random() >= 0.5)
                stationList.get(i).connect(stationList.get(i + 3), 100 * ((int) (Math.random() * 101)));
            if (i == 96) {
                stationList.get(i + 3).connect(stationList.get(i + 2), 100 * ((int) (Math.random() * 101)));
                stationList.get(i + 3).connect(stationList.get(i + 1), 100 * ((int) (Math.random() * 101)));
                stationList.get(i + 3).connect(stationList.get(i), 100 * ((int) (Math.random() * 101)));
            }
        }

        List<TrainSet> trainSetList = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            int home = (int) (Math.random() * 100);
            int source = (int) (Math.random() * 100);
            int destination = (int) (Math.random() * 100);
            while (destination == source)
                destination = (int) (Math.random() * 100);
            TrainSet trainSet = new TrainSet(new Locomotive("L" + i, 100 + (int) (Math.random() * 51),
                    stationList.get(home), stationList.get(source), stationList.get(destination)));
            trainSetList.add(trainSet);
            for (int j = 1; j <= 5 + (int) (Math.random() * 6); j++) {
                try {
                    switch ((int) (Math.random() * 13)) {
                        case 1 -> trainSet.addRailroadCar(new PassengerCar(
                                (int) (Math.random() * 49)
                        ));
                        case 2 -> trainSet.addRailroadCar(new PostOfficeCar(
                                100 * (20 + (int) (Math.random() * 51))
                        ));
                        case 3 -> trainSet.addRailroadCar(new BaggageMailCar(
                                10 * (5 + (int) (Math.random() * 16))
                        ));
                        case 4 -> trainSet.addRailroadCar(new RestaurantCar(
                                10 * (10 + (int) (Math.random() * 26))
                        ));
                        case 5 -> trainSet.addRailroadCar(new BasicFreightCar(
                                1000 * (int) (Math.random() * 31)
                        ));
                        case 6 -> trainSet.addRailroadCar(new HeavyFreightCar(
                                1000 * (50 + (int) (Math.random() * 31))
                        ));
                        case 7 -> trainSet.addRailroadCar(new RefrigeratedCar(
                                1000 * (10 + (int) (Math.random() * 21))
                        ));
                        case 8 -> trainSet.addRailroadCar(new LiquidCar(
                                1000 * (20 + (int) (Math.random() * 31))
                        ));
                        case 9 -> trainSet.addRailroadCar(new GasCar(
                                1000 * (15 + (int) (Math.random() * 31))
                        ));
                        case 10 -> trainSet.addRailroadCar(new ExplosiveCar(
                                1000 * (20 + (int) (Math.random() * 31))
                        ));
                        case 11 -> trainSet.addRailroadCar(new ToxicCar(
                                1000 * (30 + (int) (Math.random() * 41))
                        ));
                        case 12 -> trainSet.addRailroadCar(new ToxicLiquidCar(
                                1000 * (25 + (int) (Math.random() * 41))
                        ));
                    }
                } catch (MaxAmountExceeded | MaxAmountGridExceeded | MaxWeightExceeded ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        for (TrainSet trainSet : trainSetList) {
            trainSet.getThread().start();
            trainSet.start();
        }

        AppState appState = new AppState(trainSetList);
        appState.start();
    }
}