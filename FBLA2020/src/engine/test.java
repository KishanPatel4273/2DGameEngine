package engine;

import objects.Entity;
import tools.*;

public class test {
	
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

					Engine.collider.set(0, right);
					Engine.collider.set(1, top);
					Engine.collider.set(2, left);
					Engine.collider.set(3, bottom);
					
					//absolute distance between centers
					Vector deltaCenter = new Vector(Math.abs((c.getX() + c.getWidth()/2) - (e.getX() + e.getWidth()/2))
													   , Math.abs((c.getY() - c.getHeight()/2) - (e.getY() - e.getHeight()/2)));
					Engine.vectors.set(0, e.getCenter());
					Engine.vectors.set(1, c.getCenter());

					//System.out.println(distance_between.toString());
					//minimum distance the two colliders can be, so they don't clip
					Vector minLength = new Vector((c.getWidth() + e.getWidth())/2, (c.getHeight() + e.getHeight())/2);
					
					//checks which direction the clipping is happening
					//applies the fix calculated above in the right direction
					//tells the entity which face it was hit on using the complex plain	

					//first checks which axis is the major then sees which axis collision happened
					
					
					
					if((minLength.getX() >= minLength.getY() && deltaCenter.getX() >= e.getWidth()/2)//x-axis is major and collision happens in x axis too
							|| (minLength.getY() >= minLength.getX() && deltaCenter.getY() <= e.getHeight()/2)) {//y-axis is major and collision happens in x axis too
						//Calculates a deltaX to move to stop clipping
						float x = Math.abs(minLength.getX() - deltaCenter.getX());
						//checks with face left or right to apply the fix vector to stop clipping
						//tells entity e which face was clipped or interacted with
						if(Collider.contains(left, e.getCenter())) {
							forceFix.addX(x);
							e.collide("1");
						}
						if(Collider.contains(right, e.getCenter())) {
							forceFix.addX(-x); 
							e.collide("-1");
						}
					}	

					//first checks which axis is the major then sees which axis collision happened
					if((minLength.getY() >= minLength.getX() && deltaCenter.getY() >= e.getHeight()/2)//y-axis is major and collision happens in y axis too
							|| (minLength.getX() >= minLength.getY() && deltaCenter.getX() <= e.getWidth()/2)) {//x-axis is major and collision happens in y axis t0o
						//Calculates a deltaY to move to stop clipping
						float y = Math.abs(minLength.getY() - deltaCenter.getY());
						//checks with face left or right to apply the fix vector to stop clipping
						//tells entity e which face was clipped or interacted with
						if(Collider.contains(top, e.getCenter())) {
							forceFix.addY(-y);
							e.collide("-i");
						}
						if(Collider.contains(bottom, e.getCenter())) {
							forceFix.addY(y);
							e.collide("i");
						}	
					}
					//adds fix vector to collider c before it checks to see if any other collision still happen after the fix
					c.addVector(forceFix);
				}
			}
		}
		return forceFix;
	}	
	
	
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

					
					Engine.collider.set(0, right);
					Engine.collider.set(1, top);
					Engine.collider.set(2, left);
					Engine.collider.set(3, bottom);
					
					//absolute distance between centers
					Vector deltaCenter = new Vector(Math.abs((c.getX() + c.getWidth()/2) - (e.getX() + e.getWidth()/2))
													   , Math.abs((c.getY() - c.getHeight()/2) - (e.getY() - e.getHeight()/2)));
					Engine.vectors.set(0, e.getCenter());
					Engine.vectors.set(1, c.getCenter());

					//System.out.println(distance_between.toString());
					//minimum distance the two colliders can be, so they don't clip
					Vector minLength = new Vector((c.getWidth() + e.getWidth())/2, (c.getHeight() + e.getHeight())/2);
					
					//checks which direction the clipping is happening
					//applies the fix calculated above in the right direction
					//tells the entity which face it was hit on using the complex plain	

					//first checks which axis is the major then sees which axis collision happened
					//if((minLength.getX() >= minLength.getY() && deltaCenter.getX() >= e.getWidth()/2)//x-axis is major and collision happens in x axis too
					//		|| (minLength.getY() >= minLength.getX() && deltaCenter.getY() <= e.getHeight()/2)) {//y-axis is major and collision happens in x axis too
					if(deltaCenter.getX() < minLength.getX()) {
						//Calculates a deltaX to move to stop clipping
						float x = Math.abs(minLength.getX() - deltaCenter.getX());
						//checks with face left or right to apply the fix vector to stop clipping
						//tells entity e which face was clipped or interacted with
						if(Collider.intersect(leftFace, e.getCollider())) {
							forceFix.addX(x);
							e.collide("1");
						}
						if(Collider.intersect(rightFace, e.getCollider())) {
							forceFix.addX(-x); 
							e.collide("-1");
						}
					}	

					//first checks which axis is the major then sees which axis collision happened
					//if((minLength.getY() >= minLength.getX() && deltaCenter.getY() >= e.getHeight()/2)//y-axis is major and collision happens in y axis too
					//		|| (minLength.getX() >= minLength.getY() && deltaCenter.getX() <= e.getWidth()/2)) {//x-axis is major and collision happens in y axis t0o
					if(deltaCenter.getY() < minLength.getY()  && (Collider.intersect(bottomFace, e.getCollider()) || Collider.intersect(topFace, e.getCollider()))) {
						//Calculates a deltaY to move to stop clipping
						float y = Math.abs(minLength.getY() - deltaCenter.getY());
						//checks with face left or right to apply the fix vector to stop clipping
						//tells entity e which face was clipped or interacted with
						if(Collider.intersect(topFace, e.getCollider())) {
							forceFix.addY(-y);
							e.collide("-i");
						}
						if(Collider.intersect(bottomFace, e.getCollider())) {
							forceFix.addY(y);
							e.collide("i");
						}	
					}
					//adds fix vector to collider c before it checks to see if any other collision still happen after the fix
					c.addVector(forceFix);
				}
			}
		}
		return forceFix;
	}		
	
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
					
							
					Collider top_right = new Collider(c.getX() + c.getWidth(), c.getY() + e.getHeight()/2, e.getWidth()/2, e.getHeight()/2);
					Collider top_left = new Collider(c.getX() - e.getWidth()/2, c.getY() + e.getHeight()/2, e.getWidth()/2, e.getHeight()/2);
					Collider bottom_right = new Collider(c.getX() + c.getWidth(), c.getY() - c.getHeight(), e.getWidth()/2, e.getHeight()/2);
					Collider bottom_left = new Collider(c.getX() - e.getWidth()/2, c.getY() - c.getHeight(), e.getWidth()/2, e.getHeight()/2);

					
					Engine.collider.set(0, rightFace);
					Engine.collider.set(1, topFace);
					Engine.collider.set(2, leftFace);
					Engine.collider.set(3, bottomFace);
					
					//absolute distance between centers
					Vector deltaCenter = new Vector(Math.abs((c.getX() + c.getWidth()/2) - (e.getX() + e.getWidth()/2)) + 1 
													   , Math.abs((c.getY() - c.getHeight()/2) - (e.getY() - e.getHeight()/2)) + 1);
					Engine.vectors.set(0, e.getCenter());
					Engine.vectors.set(1, c.getCenter());

					//System.out.println(distance_between.toString());
					//minimum distance the two colliders can be, so they don't clip
					Vector minLength = new Vector((c.getWidth() + e.getWidth())/2, (c.getHeight() + e.getHeight())/2);
					
					//Calculates a deltaX to move to stop clipping
					float x = Math.abs(minLength.getX() - deltaCenter.getX());
					//Calculates a deltaY to move to stop clipping
					float y = Math.abs(minLength.getY() - deltaCenter.getY());
					
					
					//checks which direction the clipping is happening
					//applies the fix calculated above in the right direction
					//tells the entity which face it was hit on using the complex plain	

					if(minLength.getX() == minLength.getY()) {
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
					}
					
					if(e.getWidth() == e.getHeight()) {//entity e is a square
						if((c.getCenter().getX() <= e.getCenter().getX() ||  c.getCenter().getX() >= e.getCenter().getX()) 
								&& Collider.contains(e.getY(), c.getCenter().getY(), e.getY() - e.getHeight())) {//left
							if(Collider.contains(left, e.getCenter())) {
								forceFix.addX(x);
								e.collide("1");
							}
							if(Collider.contains(right, e.getCenter())) {
								forceFix.addX(-x); 
								e.collide("-1");
							}
						}
						if((c.getCenter().getY() <= e.getCenter().getY() ||  c.getCenter().getY() >= e.getCenter().getY()) 
								&& Collider.contains(e.getX(), c.getCenter().getX(), e.getX() + e.getWidth())) {//left
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
					if(minLength.getX() >= minLength.getY()) {//major axis x
						
					} else {
						
					}
					
					/**
					
					//first checks which axis is the major then sees which axis collision happened
					if((minLength.getX() >= minLength.getY() && deltaCenter.getX() >= e.getWidth()/2)//x-axis is major and collision happens in x axis too
							|| (minLength.getY() >= minLength.getX() && deltaCenter.getY() <= e.getHeight()/2)) { //y-axis is major and collision happens in x axis too
						//checks with face left or right to apply the fix vector to stop clipping
						//tells entity e which face was clipped or interacted with
						if(Collider.contains(left, e.getCenter())) {
							forceFix.addX(x);
							e.collide("1");
						}
						if(Collider.contains(right, e.getCenter())) {
							forceFix.addX(-x); 
							e.collide("-1");
						}
					}	

					//first checks which axis is the major then sees which axis collision happened
					if((minLength.getY() >= minLength.getX() && deltaCenter.getY() >= e.getHeight()/2)//y-axis is major and collision happens in y axis too
							|| (minLength.getX() >= minLength.getY() && deltaCenter.getX() <= e.getWidth()/2)) {//x-axis is major and collision happens in y axis t0o
						//checks with face left or right to apply the fix vector to stop clipping
						//tells entity e which face was clipped or interacted with
						if(Collider.contains(top, e.getCenter())) {
							forceFix.addY(-y);
							e.collide("-i");
						}
						if(Collider.contains(bottom, e.getCenter())) {
							forceFix.addY(y);
							e.collide("i");
						}	
					}
					**/
					//adds fix vector to collider c before it checks to see if any other collision still happen after the fix
					c.addVector(forceFix);
				}
			}
		}
		return forceFix;
	}	
}
