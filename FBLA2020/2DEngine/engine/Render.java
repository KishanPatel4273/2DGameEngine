package engine;

import objects.Entity;
import tools.Vector;

public class Render {
	
	public int width, height, max_num_pixels;
	public int[] pixels;
	
	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		this.max_num_pixels = width*height;
		pixels = new int[this.max_num_pixels];//creates pixel array for the display
	}
	
	/**
	 * return pixels to display
	 */
	public int[] getPixels() {
		return pixels;
	}
	
	/**
	 * clears the pixels array so all values are 0 aka the color black
	 */
	public void clear() {
		//clears the array my making it equal a new array of same length
		pixels = new int[max_num_pixels];
	}
	
	/**
	 * converts rgb to an rgb int type
	 */
	public static int toRGBInt(int r, int g, int b) {
		//converts rgb to int rgb by playing with bits
		return ((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
	}
	
	
	/**
	 * converts grayScale to an rgb int type
	 * grayScale is between 0 and 1
	 */
	public int toRGBInt(float grayScale) {
		//if gs = 0 then c = 0 so pure black and if gs = 1 then 255 pure white
		int c = (int) (255 * grayScale);
		return toRGBInt(c, c, c);
	}
	
	/**
	 * maps x in [y1, y2] to [z1, z2]
	 */
	public static float map(float x, float y1, float y2, float z1, float z2) {
		//x/range1 = xf/range2
		//y1 + x/range = x
		return (x * (z2 - z1) ) / (y2 - y1) + z1;
	}
	
	/**
	 * plots 2D noise
	 */
	public void draw2DNoise(float[] noise, Vector start, int color) {
		for (int i = 0; i < noise.length - 1; i++) {
			int x = (int) start.getX() + i;
			int y = (int) start.getY() + (int) noise[i];
			int x1 = (int) start.getX() + i + 1;
			int y1 = (int) start.getY() + (int) noise[i + 1];
			drawLine(x,y, x1, y1, color);
		}
	}
	
	/**
	 * fills in pixel a x and y with rgb int value of color if inside of window
	 * and makes bottom left corner is the origin respect to a cartesian plane
	 */
	public void drawPixel(int x_plane, int y_plane, int color) {
		int y_screen = height - y_plane;//flips y axis to make it in the cartesian plane
		if (0 <= x_plane && x_plane < width && 0 <= y_screen && y_screen < height) {//checks if pixel is in window
			pixels[x_plane + y_screen * width] = color;//plots pixel at (x,y) with rgb value of color(int)
		}
	}
	
	/**
	 *calculates pixels values to color in to create line between (x1,y1) and (x2,y2) and
	 *sets those pixels to rgb int value of color
	 */
	public void drawLine(int x1, int y1, int x2, int y2, int color) {
		if(x1 != x2){// so only non vertical lines
			float slope = ((float)(y1 - y2) / (x1 - x2));// Calculates dy/dx for line
			//calculates maximum and minimum for each axis x and y
			int maxX = Math.max(x1, x2);
			int minX = Math.min(x1, x2);
			int maxY = Math.max(y1, y2);
			int minY = Math.min(y1, y2);
			
			//Determines with variable to draw line in relation too, to increase efficiency  
			if(Math.abs(slope) <= 1) {
				for(int x = minX; x < maxX; x++){//draw line in relation to x 
					//Calculates y value in relation to x and colors in pixel
					drawPixel(x, (int) ((float)(slope * (x - x1) + y1)), color);
				}
			} else {
				for(int y = minY; y < maxY; y++){//draw line in relation to y 
					//Calculates x value in relation to y and colors in pixel
					drawPixel((int) ((float)((y - y1) / slope)) + x1, y, color);
				}
			}
		} else {//vertical line
			drawVerticalLine(x1, y1, y2, color);//draw horizontal line
		}
	}
	
	/**
	 *calculates pixels to draw a horizontal line between (x, y1) and (x, y2) and
	 *sets those pixels to rgb int value of color
	 */
	public void drawVerticalLine(int x, int y1, int y2, int color) {
		//calculates maximum and minimum for axis y
		int maxY = Math.max(y1, y2);
		int minY = Math.min(y1, y2);
		for(int y = minY; y < maxY; y++){
			//plots pixels from (x, y max to t min)
			drawPixel(x, y, color);
		}
	}
	
	/**
	 * converts double type variables to int to pass through drawline method
	 */
	public void drawLine(double x1, double y1, double x2, double y2, int color) {
		drawLine((int) x1, (int) y1, (int) x2, (int) y2, color);
	}
	
	/**
	 * Calculates all pixels contained in the rectangle at position(top left corner) x and y 
	 * with dimension of width and height and sets those pixels to rgb int value of color
	 */
	public void fillRectangle(int x, int y, int width, int height, int color) {
		//draws rectangle by drawing vertical line at each x value along the edge of rectangle
		for(int dx = x; dx < x + width; dx++) {//run along the x axis bounded by width
			drawVerticalLine(dx, y, y - height, color);//draws vertical lines down with length of height
			// y - height because y is in the top left corner
		}
	}
	
	/**
	 * Calculates all pixels on the perimeter of the rectangle at position(top left corner) x and y 
	 * with dimension of width and height and sets those pixels to rgb int value of color
	 */
	public void drawRectangle(int x, int y, int width, int height, int color) {
		//draws lines to create perimeter
		drawLine(x, y, x + width, y, color);//top
		drawLine(x, y - height, x + width, y - height, color);//bottom
		drawVerticalLine(x, y, y-height, color);//left
		drawVerticalLine(x + width, y, y-height, color);//right
	}
	
	/**
	 * draws rectangle with a and b as its corners
	 */
	public void drawRectangle(Vector a, Vector b, int color) {
		int x = (int) Math.min(a.getX(), b.getX());//left most x value of the rectangle
		int y = (int) Math.max(a.getY(), b.getY());//top most y value of the rectangle
		//the length of rectangle in each dimension is |deta axis|
		int w = (int) Math.abs(a.getX() - b.getX()) + 1;
		int h = (int) Math.abs(a.getY() - b.getY()) + 1;
		drawRectangle(x, y, w, h, color);

	}
	
	/**
	 * converts two corners of a rectangle to the top left position and width and height array
	 */
	public int[] convertDimension(Vector a, Vector b) {
		int x = (int) Math.min(a.getX(), b.getX());//left most x value of the rectangle
		int y = (int) Math.max(a.getY(), b.getY());//top most y value of the rectangle
		//the length of rectangle in each dimension is |deta axis|
		int w = (int) Math.abs(a.getX() - b.getX()) + 1;
		int h = (int) Math.abs(a.getY() - b.getY()) + 1;
		int[] d = {x, y, w, h};
		return d;
	}
	
	/**
	 *draws the rectangle created by the entity with color color
	 */
	public void renderSolidEntity(Entity e) {	
		fillRectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight(), e.getColor());
		drawRectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight(), 255);
	}
}
