package restaurantFood;

public class Dessert extends Food {

	public Dessert(String name, int timeToEat, int price) {
		super(name, timeToEat, price);
	}
	
	@Override
	public String getType() {
		return "Dessert";
	}
}
