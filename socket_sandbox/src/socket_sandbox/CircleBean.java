import java.io.Serializable;


public class CircleBean implements Serializable {
	private double radius;
	private double area;
	
	public CircleBean(double radius){
		this.radius = radius;
	}

	
	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getRadius() {
		return radius;
	}
	
	public double getArea() {
		return area;
	}


	
	
	
}
