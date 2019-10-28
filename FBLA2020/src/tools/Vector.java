package tools;

public class Vector {
	
	private float x, y;

	//creates a vector with a x and y argument which both equal 0
	public Vector() {
		this.x = 0;
		this.y = 0;
	}
	
	//creates a vector with a x and y argument
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the length of vector
	 */
	public float getMagnitude() {
		return (float) Math.sqrt(x * x + y * y);
	}

	//getters and setters for x and y
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}
	
	//add x to existing x
	public void addX(float x) {
		this.x += x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	//add y to existing y
	public void addY(float y) {
		this.y += y;
	}
	
	//adds Vector v to existing vector
	public void addVector(Vector v) {
		this.x += v.getX();
		this.y += v.getY();
	}
	
	//returns a string representation of vector
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/**
	 * vector v gets scaled by scaler
	 * scaler * v
	 */
	public static Vector scale(Vector v, float scale) {
		return new Vector(v.getX() * scale, v.getY() * scale);
	}

	/**
	 * makes vector v a unit vector
	 * v -> v hat
	 */
	public static Vector normalize(Vector v) {
		return scale(v, 1.0f/v.getMagnitude());
	}

	/**
	 * calculates dot product between vectors a and b 
	 * a dot b
	 */
	public static float dotPoduct(Vector a, Vector b) {
		return a.getX() * b.getX() + a.getY() * b.getY();
	}

	/**
	 * adds vectors a and b together
	 * a+b
	 */
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.getX() + b.getX(), a.getY() + b.getY());
	}

	/**
	 * subtracts vectors a and b
	 * a-b
	 */
	public static Vector subtract(Vector a, Vector b) {
		return new Vector(a.getX() - b.getX(), a.getY() - b.getY());
	}

}
