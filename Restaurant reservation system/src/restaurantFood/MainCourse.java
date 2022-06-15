package restaurantFood;

public class MainCourse extends Food {

	public MainCourse(String name, int timeToEat, int price) {
		super(name, timeToEat, price);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return "Main Course";
	}
}
