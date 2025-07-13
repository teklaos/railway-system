package cars;

public class RestaurantCar extends RailroadCar implements RequiresElectricity {
    private final int numberOfMeals;
    private final String[] menu = {"Caesar Salad", "Smashed Potatoes", "Rib-Eye Steak"};

    public RestaurantCar(int numberOfMeals) {
        super(numberOfMeals + 1000);
        this.numberOfMeals = numberOfMeals;
    }

    @Override
    public String toString() {
        return super.toString() + " - Restaurant Railroad Car\nNumber of Meals: " + numberOfMeals +
                "\nGross Weight: " + getGrossWeight() + " kg";
    }
}
