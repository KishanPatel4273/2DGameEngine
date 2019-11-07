package tools;

import objects.Entity;
import engine.Engine;
import window.Display;

public class Ray {
	
	public static int width = Display.WIDTH, height = Display.HEIGHT;
	public static Vector dimensions = new Vector(width, height);
	
	/**
	 * cast ray starting at start in direction of direction and checks with all entities 
	 * the distance between the start and the intersection 
	 * direction vector is a unit vector their is only one non zero component  d = {<+1,0>, <0,+1>, <-1,0>, <0,-1>}
	 */
	public static float rayCasting(Vector start, Vector direction) {
		//L is the maximum distance a object can be away form the start if it is in the frame 
		float minL = Math.abs(Vector.dotPoduct(direction, dimensions)) + 2;//+2 b/c of edges
		
		//calculates the second point of the first segment
		Vector endOfSegment1 = Vector.add(start, Vector.hadamardProduct(direction, dimensions));
		Vector entitySegement1 = new Vector();
		Vector entitySegement2 = new Vector();

		for(Entity e: Engine.entitiesInFrame) {//runs through all entities in frame
			if(e.getId() != 0) {//not a player bc mostly used of its not a player
				float currentDistance = -1;
				//calculates the segment of e
				if(direction.getX() == 0) {
					//direction are relative to bottom right as being origin
					if(direction.getY() == -1) {//ray cast down
						//segment of e is its top face
						entitySegement1 = new Vector(e.getX(), e.getY());
						entitySegement2 = new Vector(e.getX() + e.getWidth(), e.getY());
					} else if(direction.getY() == 1) {//ray cast up
						//segment of e is its bottom face
						entitySegement1 = new Vector(e.getX(), e.getY() - e.getHeight());
						entitySegement2 = new Vector(e.getX() + e.getWidth(), e.getY() - e.getHeight());
					}
				} else {
					if(direction.getX() == 1) {//ray cast right
						//segment of e is its left face
						entitySegement1 = new Vector(e.getX(), e.getY());
						entitySegement2 = new Vector(e.getX(), e.getY() - e.getHeight());
					} else if(direction.getX() == -1) {//ray cast left
						//segment of e is its right face
						entitySegement1 = new Vector(e.getX() + e.getWidth(), e.getY());
						entitySegement2 = new Vector(e.getX()  + e.getWidth(), e.getY() - e.getHeight());
					}	
				}
				
				//Calculates the distance away between start and segment of e
				currentDistance = segmentIntersection(start, endOfSegment1, entitySegement1, entitySegement2);
				
				//if this entity intersection is closer it replaces the current
				if(currentDistance != -1 && currentDistance < minL) {
					minL = currentDistance;	
				}
			}
		}
		//returns the closest distance from entities in said direction 
		return minL;
	}
	
	
	/**
	 * returns the magnitude of intersection between two segments
	 * the segments are r = b-a and s = d-c
	 * if their is no intersection -1 will be returned
	 * math explained in https://www.youtube.com/watch?v=c065KoXooSw&t
	 */
	public static float segmentIntersection(Vector a, Vector b, Vector c, Vector d) {
		//creates two segments in a plain with a gaussian curvature of 0
		Vector r = Vector.subtract(b, a);
		Vector s = Vector.subtract(d, c);
		//calculates the cross between s and r to be used later on
		float sxr = Vector.crossProduct(s, r);
		// the point of intersection happens when a + tr = c + us, where t,u -> [0,1]
		//calculates u and checks if it is valid
 		float u = Vector.crossProduct(Vector.subtract(a, c), r) / sxr;
 		if(!(0 <= u && u <= 1)) {
			return -1;//no intersection happens
		}
 		//calculates t and if its valid returns the magnitude of intersection
 		float t = Vector.crossProduct(Vector.subtract(c, a), s) / -sxr;
		if(0 <= t && t <= 1) {
			return Vector.scale(t, r).getMagnitude();//distance from a vector the intersection happens at
		} 
		return -1;//no intersection happens
	}
}
