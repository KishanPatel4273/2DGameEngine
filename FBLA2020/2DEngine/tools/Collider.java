package tools;

public class Collider {

	//properties of the collider, such as position and dimension
	private int x, y, width, height;
	
	//Constructs collider
	//where (x,y) is the top left hand corner and (width, height) is the dimension
	public Collider(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//getters and setter for x, y, width, and height
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	//adds x to the initial x
	public void addX(int x) {
		this.x += x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	//adds y to the initial y
	public void addY(int y) {
		this.y += y;
	}

	//adds vector v to the existing position
	public void addVector(Vector v) {
		//allows for a translation
		this.x += (int) v.getX();
		this.y += (int) v.getY();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	//returns the the characteristics of the collider
	public String toString() {
		return "[" + x  + " ," + y + " ," + width + " ," + height + "]";
	}
	
	//calculates the center of the collider
	public Vector getCenter() {
		//it's -1/2*length because (x, y) is top left corner and half b/c thats where the center is
		return new Vector(x + width/2, y - height/2);
	}
	
	//calclates total area of collider
	public float getArea() {
		return width*height;
	}
	
	/**
	 *checks of the point (x,y) is inside the container made by the collider
	 */
	public static boolean contains(Collider c, int x, int y) {
		//p - position of collider(center) and v -(x,y) to calculate if v is inside the collider
		// |p-v| < 1/2(width, height)
		Vector center = c.getCenter();
		return 2 * Math.abs(center.getX() - x) < c.getWidth() && 2 * Math.abs(center.getY() - y) < c.getHeight(); 
	}
	
	/**
	 *checks if vector v is inside the container made by the collider
	 */
	public static boolean contains(Collider c, Vector v) {
		//reformats input to use contains method above
		return contains(c, (int) v.getX(), (int) v.getY());
	}
	
	/**
	 *Calculates whether collider's a and b intersect
	 */
	public static boolean intersect(Collider a, Collider b) {
		//create new collider that is a combination of a and b
		//this is done to use the container's centers only and not look at every vertex - works because they are rectangles
		Collider c = new Collider(a.getX() - b.getWidth()/2, a.getY() + b.getHeight()/2, a.getWidth() + b.getWidth(), a.getHeight() + b.getHeight());
		//gets the center of b because the new collider, c , was made in respect with a's position
		return contains(c, b.getCenter());//checks if the center of b is inside of new collider
	}
	
	/**
	 * Checks whether x is in between a and b
	 */
	public static boolean contains(float a, float x, float b) {
		//checks if x is in between both and b
		return Math.max(a, b) >= x && x >= Math.min(a, b);
	}
	
	/**
	 *gets area of over lap between two colliders
	 */
	public static float overLappingArea(Collider a, Collider b) {
		//area = x intersection * y intersection
		if(!intersect(a, b)) {// if a and b don't intersect area is 0
			return 0;
		}
	
		//Minimums and maximum for each axis and each collider
		float minAX = Math.min(a.getX(), a.getX() + a.getWidth());
		float maxAX = Math.max(a.getX(), a.getX() + a.getWidth());
		float minBX = Math.min(b.getX(), b.getX() + b.getWidth());
		float maxBX = Math.max(b.getX(), b.getX() + b.getWidth());
		
		float minAY = Math.min(a.getY(), a.getY() - a.getHeight());
		float maxAY = Math.max(a.getY(), a.getY() - a.getHeight());
		float minBY = Math.min(b.getY(), b.getY() - b.getHeight());
		float maxBY = Math.max(b.getY(), b.getY() - b.getHeight());
		
		//calculates overlap
		//calculations are done in respect to a Euclidean space
		float deltaX = 0;
		float deltaY = 0;
		//if one contains the other full the delta in that axis is the length in the contained object
		if(contains(maxAX, minBX, minAX) && contains(maxAX, maxBX, minAX)) {
			deltaX = maxBX - minBX;
		} else if(contains(maxBX, minAX, minBX) && contains(maxBX, maxAX, minBX)) {
			deltaX = maxAX - minAX;
		} else { 
			//calculated partial overlap
			if(maxAX < maxBX) {
				deltaX = maxAX - minBX;
			} else {
				deltaX = maxBX - maxAX;
			}
		}
		//if one contains the other full the delta in that axis is the length in the contained object
		if(contains(maxAY, minBY, minAY) && contains(maxAY, maxBY, minAY)) {
			deltaY = maxBY - minBY;
		} else if(contains(maxBY, minAY, minBY) && contains(maxBY, maxAY, minBY)) {
			deltaY = maxAY - minAY;
		} else {
			//calculated partial overlap
			if(maxAY > maxBY) {
				deltaY = maxBY - minAY;
			} else {
				deltaY = maxAY - minBY;
			}
		}
		//returns the product if the two overlaps to get the area of intersection
		return deltaY * deltaX;
	}
	
}
