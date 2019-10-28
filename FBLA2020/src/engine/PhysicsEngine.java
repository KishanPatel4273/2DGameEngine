package engine;

import objects.Entity;
import tools.*;

public class PhysicsEngine {
	
	/**
	 *Checks whether collider c has collided with anything, the collider can represent an entity
	 *if so it tells that entity the direction it collided with
	 *and returns a vector describing the translation of collilder to move to stop clipping
	 */
	public static Vector collisionDetection(Collider c) {
		Vector forceFix = new Vector();
		for(Entity e: Engine.entities) {//runs through all the entities in frame
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

					if(e.getHeight() == e.getWidth()) {
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
								}
							}
							if(minLength.getY() >= minLength.getX()) {//major axis y
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
	 * 	Collider top_right = new Collider(c.getX() + c.getWidth(), c.getY() + e.getHeight()/2, e.getWidth()/2, e.getHeight()/2);
					Collider top_left = new Collider(c.getX() - e.getWidth()/2, c.getY() + e.getHeight()/2, e.getWidth()/2, e.getHeight()/2);
					Collider bottom_right = new Collider(c.getX() + c.getWidth(), c.getY() - c.getHeight(), e.getWidth()/2, e.getHeight()/2);
					Collider bottom_left = new Collider(c.getX() - e.getWidth()/2, c.getY() - c.getHeight(), e.getWidth()/2, e.getHeight()/2);

Collider right = new Collider(c.getX() + c.getWidth(), c.getY(), e.getWidth()/2, c.getHeight());
					Collider top = new Collider(c.getX(), c.getY() + e.getHeight()/2, c.getWidth(), e.getHeight()/2);
					Collider left = new Collider(c.getX() - e.getWidth()/2, c.getY(), e.getWidth()/2, c.getHeight());
					Collider bottom = new Collider(c.getX(), c.getY() - c.getHeight(), c.getWidth(), e.getHeight()/2);


						if(Collider.intersect(ErightFace, c)  && Collider.intersect(leftFace, e.getCollider())) {
							forceFix.addX(x);
							e.collide("1");
						}
						if(Collider.intersect(EleftFace, c) && Collider.intersect(rightFace, e.getCollider())) {
							forceFix.addX(-x); 
							e.collide("-1");
						}
						if(Collider.intersect(EbottomFace, c) && Collider.intersect(topFace, e.getCollider())) {
							forceFix.addY(-y);
							e.collide("-i");
						}
						if(Collider.intersect(EtopFace, c) && Collider.intersect(bottomFace, e.getCollider())) {
							forceFix.addY(y);
							e.collide("i");
						}
						
							
							if(deltaCenter.getX() <= minLength.getX()) {
							if(Collider.intersect(EleftFace, c) || Collider.intersect(ErightFace, c)) {
								if(Collider.intersect(leftFace, e.getCollider()) || Collider.intersect(rightFace, e.getCollider())) {
									if(c.getCenter().getX() <= e.getCenter().getX() ||  c.getCenter().getX() >= e.getCenter().getX()) {
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
						if(deltaCenter.getY() <= minLength.getY()) {
							if(Collider.intersect(EtopFace, c) || Collider.intersect(EbottomFace, c)) {
								if(Collider.intersect(topFace, e.getCollider()) || Collider.intersect(bottomFace, e.getCollider())) {
								if(c.getCenter().getY() <= e.getCenter().getY() ||  c.getCenter().getY() >= e.getCenter().getY()) {
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
						}
	 */
}
