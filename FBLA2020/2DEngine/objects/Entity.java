package objects;

import tools.Collider;
import tools.Vector;

public class Entity {
	
	//Intrinsic properties of entity
	private int x, y, width, height, color, id;
	private boolean transparency, collidable, gravity;
	private String tag;
	
	public Entity(int x, int y, int width, int height, int id) {
		//position of entity
		this.x = x;
		this.y = y;
		//dimensions of entity
		this.width = width;
		this.height = height;
		//identifiers of entity and its type
		this.id = id;
		this.tag = "Entity";
		//look in the environment
		this.transparency = false;
		this.color = 16777215;
		//how it interacts with the environment
		this.collidable = true;
		this.gravity = false;
	}
	
	//update the entities characteristics
	public void update() {
		
	}
	
	//render the entity
	public void render() {
		
	}
	
	//tells entity it has been collided with and what direction
	//direction is in the complex plane
	public void collide(String direction) {
		
	}

	//getters and setters for Intrinsic variables
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	//adds to existing x
	public void addX(int x) {
		this.x += x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	//adds to existing y
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public boolean isTransparency() {
		return transparency;
	}

	public void setTransparency(boolean transparency) {
		this.transparency = transparency;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	public boolean hasGravity() {
		return gravity;
	}
	
	//calculates the center of the collider
	public Vector getCenter() {
		//it's -1/2*length because (x, y) is top left corner and half b/c thats where the center is
		return new Vector(x + width/2, y - height/2);
	}
	
	//creates collider that is the same dimension as the entity
	public Collider getCollider() {
		return new Collider(x, y, width, height);
	}

	@Override
	public String toString() {
		return tag + "|" + x + "|" + y + "|" + width + "|" + height + "|" + id + "|" + transparency + "|";	
	}
	
	
	
}
