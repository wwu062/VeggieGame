package veggie.model;

import java.awt.Rectangle;
import java.awt.Shape;

import processing.core.PImage;
import veggie.screen.DrawingSurface;

/**
 * 
 * @author awang104
 *
 *         MovingImage represents any image that moves an interacts with the
 *         environment while in platform mode
 *
 */
public class MovingImage {

	private Rectangle hitbox;
	private PImage image;

	/**
	 * Creates a new MovingImage object
	 * 
	 * @param x      x coordinate of the MovingImage
	 * @param y      y coordinate of the MovingImage
	 * @param width  width of the MovingImage
	 * @param height height of the MovingImage
	 * @param image  texture used to represent the MovingImage
	 */
	public MovingImage(int x, int y, int width, int height, PImage image) {
		hitbox = new Rectangle(x, y, width, height);
		this.image = image;
	}

	/**
	 * Moves the object to the designated location
	 * 
	 * @param x new x-coordinate of MovingImage
	 * @param y new y-coordinate of MovingImage
	 */
	public void moveTo(double x, double y) {
		hitbox.x += x;
		hitbox.y += y;
	}

	/**
	 * Moves the object by that amount
	 * 
	 * @param x change in x-coordinate
	 * @param y change in y-coordinate
	 */
	public void moveBy(double x, double y) {
		hitbox.x += x;
		hitbox.y += y;
	}

	public void changeBy(int x, int y) {
		hitbox.x = x;
		hitbox.y = y;
	}

	/**
	 * Draws the object
	 * 
	 * @param marker Place where object is drawn on.
	 */
	public void draw(DrawingSurface marker) {
		marker.image(image, (float) hitbox.x, (float) hitbox.y);
	}

	/**
	 * 
	 * @param bounds Shape interacting with MovingImage
	 * @return true if MovingImage touches the shape
	 */
	public boolean contains(Shape bounds) {
		return hitbox.intersects(bounds.getBounds());
	}

	/**
	 * 
	 * @return Rectangular bounds of the MovingImage
	 */
	public Rectangle getBounds() {
		return hitbox;
	}
}
