package engine;

import objects.Entity;
import tools.*;

public class PhysicsEngine {
	
	public static Vector collisionFix(Collider c, Vector velocity) {
		Vector forceFix = new Vector();
		//velocity = Vector.normalize(velocity);
		for(Entity e: Engine.entitiesInFrame) {//runs through all the entities in frame
			if(e.isCollidable() && e.getId() != 0) {//sees if collider c can collied with the entity and isn't its self(a player entity)
				if(Collider.intersect(c, e.getCollider())) {//collider c clips entity e
					
					//absolute distance between centers
					Vector deltaCenter = new Vector(Math.abs((c.getX() + c.getWidth()/2) - (e.getX() + e.getWidth()/2))
													   , Math.abs((c.getY() - c.getHeight()/2) - (e.getY() - e.getHeight()/2)));
					Vector minLength = new Vector((c.getWidth() + e.getWidth())/2, (c.getHeight() + e.getHeight())/2);
					Vector intersect = Vector.subtract(minLength, deltaCenter);
					if(velocity.getMagnitude() != 0) {
						Engine.vectors.set(1, Vector.add(velocity, c.getCenter()));						
					}
					Engine.vectors.set(0, c.getCenter());
									
					forceFix.addVector(collisonFixY(c, e, Vector.normalize(velocity).getY()));
					c.addVector(forceFix);
					forceFix.addVector(collisonFixX(c, e, Vector.normalize(velocity).getX()));


				
				}
			}
		}
		return forceFix;
	}
	
	public static Vector collisonFixY(Collider c, Entity e, float velocityY) {
		Vector forceFix = new Vector();//vector to move player to stop collisions
		
		//colliders on top and bottom of player with dimensions proportional to both entities
		Collider topFace = new Collider(c.getX(), c.getY() + e.getHeight()/2, c.getWidth(), e.getHeight()/2);
		Collider bottomFace = new Collider(c.getX(), c.getY() - c.getHeight(), c.getWidth(), e.getHeight()/2);
		
		//distances between centers
		float deltaY = Math.abs((c.getY() - c.getHeight()/2) - (e.getY() - e.getHeight()/2));
		float minLengthY = (c.getHeight() + e.getHeight())/2;//max length of delatY
		int intersectY = (int) (minLengthY - deltaY);//Difference between minL and deltaY
		
		boolean switchM = false;
		//checks for face and center intersection
		if(Collider.contains(topFace, e.getCenter())) {
			forceFix.addY(-intersectY);
			e.collide("-i");//face entity was hit
			switchM = true;
		}
		if(Collider.contains(bottomFace, e.getCenter())) {
			forceFix.addY(intersectY);
			e.collide("i");//face entity was hit
			switchM = true;
		}
		//if collision in y isn't picked up before. 
		if(!switchM) {
			//forceFix.setY(-velocityY/Math.abs(velocityY) * intersectY);
			//System.out.println(intersectY);
		}
		return forceFix;
	}
	
	public static Vector collisonFixX(Collider c, Entity e, float velocityX) {
		Vector forceFix = new Vector();//vector to move player to stop collisions
		
		//colliders on right and left of player with dimensions proportional to both entities
		Collider rightFace = new Collider(c.getX() + c.getWidth(), c.getY(), e.getWidth()/2, c.getHeight());
		Collider leftFace = new Collider(c.getX() - e.getWidth()/2, c.getY(), e.getWidth()/2, c.getHeight());

		//distances between centers
		float deltaX = Math.abs((c.getX() + c.getWidth()/2) - (e.getX() + e.getWidth()/2));
		float minLengthX = (c.getWidth() + e.getWidth());//max length of delatX
		int intersectX = (int) (minLengthX - deltaX);//Difference between minL and deltaX
		
		//checks for face and center intersection
		if(Collider.contains(leftFace, e.getCenter())) {
			forceFix.addX(intersectX);//face entity was hit
			e.collide("1");
		}
		if(Collider.contains(rightFace, e.getCenter())) {
			forceFix.addX(-intersectX); //face entity was hit
			e.collide("-1");
		}
		return forceFix;
	}
	
	/**
	 *Checks whether collider c has collided with anything, the collider can represent an entity
	 *if so it tells that entity the direction it collided with
	 *and returns a vector describing the translation of collilder to move to stop clipping
	 */
	public static Vector collisionFix(Collider c) {
		Vector forceFix = new Vector();
		for(Entity e: Engine.entitiesInFrame) {//runs through all the entities in frame
			if(e.isCollidable() && e.getId() != 0) {//sees if collider c can collied with the entity and isn't its self(a player entity)
				if(Collider.intersect(c, e.getCollider())) {//collider c clips entity e
					//collider's around collider c:right, top, left, bottom
					//the collider's dimension is a combination of entity e and collider c so when looking for intersections you can use centers of entities
					//colliders are made next to to the faces of collider c
					Collider right = new Collider(c.getX() + c.getWidth(), c.getY() + e.getHeight()/2, e.getWidth()/2, c.getHeight() + e.getHeight());
					Collider top = new Collider(c.getX() - e.getWidth()/2, c.getY() + e.getHeight()/2, c.getWidth() + e.getWidth(), e.getHeight()/2);
					Collider left = new Collider(c.getX() - e.getWidth()/2, c.getY() + e.getHeight()/2, e.getWidth()/2, c.getHeight() + e.getHeight());
					Collider bottom = new Collider(c.getX() - e.getWidth()/2, c.getY() - c.getHeight(), c.getWidth() + e.getWidth(), e.getHeight()/2);

					Collider rightFace = new Collider(c.getX() + c.getWidth(), c.getY(), e.getWidth()/2, c.getHeight());
					Collider topFace = new Collider(c.getX(), c.getY() + e.getHeight()/2, c.getWidth(), e.getHeight()/2);
					Collider leftFace = new Collider(c.getX() - e.getWidth()/2, c.getY(), e.getWidth()/2, c.getHeight());
					Collider bottomFace = new Collider(c.getX(), c.getY() - c.getHeight(), c.getWidth(), e.getHeight()/2);

					
					Collider ErightFace = new Collider(e.getX() + e.getWidth(), e.getY(), c.getWidth()/2, e.getHeight());
					Collider EleftFace = new Collider(e.getX() - c.getWidth()/2, e.getY(), c.getWidth()/2, e.getHeight());
					Collider EtopFace = new Collider(e.getX(), e.getY() + c.getHeight()/2, e.getWidth(), c.getHeight()/2);
					Collider EbottomFace = new Collider(e.getX(), e.getY() - e.getHeight(), e.getWidth(), c.getHeight()/2);			
					
					
					
					Engine.collider.set(0, ErightFace);
					Engine.collider.set(1, EtopFace);
					Engine.collider.set(2, EleftFace);
					Engine.collider.set(3, EbottomFace);
					
					Engine.collider.set(4, rightFace);
					Engine.collider.set(5, topFace);
					Engine.collider.set(6, leftFace);
					Engine.collider.set(7, bottomFace);
					
					//absolute distance between centers
					Vector deltaCenter = new Vector(Math.abs((c.getX() + c.getWidth()/2) - (e.getX() + e.getWidth()/2))
													   , Math.abs((c.getY() - c.getHeight()/2) - (e.getY() - e.getHeight()/2)));
					Engine.vectors.set(0, e.getCenter());
					Engine.vectors.set(1, c.getCenter());

					//System.out.println(distance_between.toString());
					//minimum distance the two colliders can be, so they don't clip
					Vector minLength = new Vector((c.getWidth() + e.getWidth())/2, (c.getHeight() + e.getHeight())/2);
					
					//Calculates a deltaX to move to stop clipping
					float x = Math.abs(minLength.getX() - deltaCenter.getX());
					//Calculates a deltaY to move to stop clipping
					float y = Math.abs(minLength.getY() - deltaCenter.getY());
					
					if(deltaCenter.getX() > minLength.getX()) {
						x = 0;
					}
					if(deltaCenter.getY() > minLength.getY()) {
						y = 0;
					}
					
					
					//checks which direction the clipping is happening
					//applies the fix calculated above in the right direction
					//tells the entity which face it was hit on using the complex plain	
					
					
					if(e.getHeight() == e.getWidth() && e.getWidth() <= 200) {
						if(Collider.contains(leftFace, e.getCenter())) {
							forceFix.addX(x);
							e.collide("1");
						}
						if(Collider.contains(rightFace, e.getCenter())) {
							forceFix.addX(-x); 
							e.collide("-1");
						}
						if(Collider.contains(topFace, e.getCenter())) {
							forceFix.addY(-y);
							e.collide("-i");
						}
						if(Collider.contains(bottomFace, e.getCenter())) {
							forceFix.addY(y);
							e.collide("i");
						}
					} else {
						if(minLength.getX() == minLength.getY() ) {
							if(deltaCenter.getX() >= deltaCenter.getY()) {
								if(Collider.contains(left, e.getCenter())) {
									forceFix.addX(x);
									e.collide("1");
								}
								if(Collider.contains(right, e.getCenter())) {
									forceFix.addX(-x); 
									e.collide("-1");
								}
							}
							if(deltaCenter.getX() <= deltaCenter.getY()) {
								if(Collider.contains(top, e.getCenter())) {
									forceFix.addY(-y);
									e.collide("-i");
								}
								if(Collider.contains(bottom, e.getCenter())) {
									forceFix.addY(y);
									e.collide("i");
								}	
							}
						} else {					
							float topArea = Collider.overLappingArea(topFace, e.getCollider())/topFace.getArea();
							float bottomArea = Collider.overLappingArea(bottomFace, e.getCollider())/bottomFace.getArea();
							float leftArea = Collider.overLappingArea(leftFace, e.getCollider())/leftFace.getArea();
							float rightArea = Collider.overLappingArea(rightFace, e.getCollider())/rightFace.getArea();
							//System.out.println(topArea + " " + bottomArea + " " + leftArea + " " + rightArea);
							//System.out.println(Collider.overLappingArea(new Collider(-1, 5, 3, 3), new Collider(1,3,3,3)));
							if(minLength.getX() >= minLength.getY()) {//major axis x
								if(Collider.intersect(topFace, e.getCollider()) || Collider.intersect(bottomFace, e.getCollider())) {
									if(deltaCenter.getY() <= minLength.getY()) {
										if(leftArea < topArea && rightArea < topArea 
											|| (leftArea < bottomArea && rightArea < bottomArea)) {
											if(Collider.contains(top, e.getCenter())) {
												forceFix.addY(-y);
												e.collide("-i");
											}
											if(Collider.contains(bottom, e.getCenter())) {
												forceFix.addY(y);
												e.collide("i");
											}
										}
									}
								}
								if(Collider.intersect(leftFace, e.getCollider()) || Collider.intersect(rightFace, e.getCollider())) {
									if(deltaCenter.getX() <= minLength.getX()) {
										if(leftArea > topArea && leftArea > bottomArea
											|| (rightArea > topArea && rightArea > bottomArea)) {
											if(Collider.contains(left, e.getCenter())) {
												forceFix.addX(x);
												e.collide("1");
											}
											if(Collider.contains(right, e.getCenter())) {
												forceFix.addX(-x); 
												e.collide("-1");
											}
										}
									} 
								}
							}
							if(minLength.getY() >= minLength.getX()) {//major axis y
								if(Collider.intersect(topFace, e.getCollider()) || Collider.intersect(bottomFace, e.getCollider())) {
									if(deltaCenter.getY() <= minLength.getY()) {
										if(leftArea < topArea && rightArea < topArea) {
											if(Collider.contains(top, e.getCenter())) {
												forceFix.addY(-y);
												e.collide("-i");
											}
										}
										if(leftArea < bottomArea && rightArea < bottomArea) {
											if(Collider.contains(bottom, e.getCenter())) {
												forceFix.addY(y);
												e.collide("i");
											}
										}
									}
								}
								if(Collider.intersect(leftFace, e.getCollider()) || Collider.intersect(rightFace, e.getCollider())) {
									if(deltaCenter.getX() <= minLength.getX()) {
										if(leftArea > topArea && leftArea > bottomArea) {
											if(Collider.contains(left, e.getCenter())) {
												forceFix.addX(x);
												e.collide("1");
											}
										}
										if(rightArea > topArea && rightArea > bottomArea) {
											if(Collider.contains(right, e.getCenter())) {
												forceFix.addX(-x); 
												e.collide("-1");
											}
										}
									}
								} 
							}	
						}
					}
					//adds fix vector to collider c before it checks to see if any other collision still happen after the fix
					c.addVector(forceFix);
				}
			}
		}
		return forceFix;
	}
	
	/**
	 * if collider c collides with any entity it will give the entity an notification
	 */
	public static void collisionDetection(Collider c) {
		for(Entity e: Engine.entitiesInFrame) {//runs through all the entities in frame
			if(e.isCollidable() && e.getId() != 0) {//check if its collidable
				if(Collider.intersect(c, e.getCollider())) {//if the two intersect
					e.collide("0");//tells entity e that it has been "hit"
					//0 signifies that direction is unknown
				}
			}
		}
	}
	
	//sees of collider c is colliding with anything it shouldn't be colliding with
	public static boolean collides(Collider c) {
		for(Entity e: Engine.entities) {//runs through all the entities in frame
			if(e.isCollidable() && e.getId() != 0) {//sees if collider c can collied with the entity and isn't its self 
				if(Collider.intersect(c, e.getCollider())) {//e and c intersect/clip/collide
					return true;
				}
			}
		}
		return false;
	}
	
	//calculates delta x for projectile motion
	public static float projectileMotion(float v, float a, float t, float tickrate) {
		//delta x = vt + 1/2at^2
		return v*t* tickrate + .5f * a * t * t * tickrate*tickrate;
	}
	
}