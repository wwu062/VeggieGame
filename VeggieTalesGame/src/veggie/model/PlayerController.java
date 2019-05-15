package veggie.model;

import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PImage;

/**
 * 
 * @author awang104
 *
 *         PlayerController is the player character for the platform mode with
 *         physics traits.
 */
public class PlayerController extends MovingImage {
	private double dx;
	private double dy;
	private boolean jump;

	/**
	 * Creates a new PlayerController object
	 * 
	 * @param x      x coordinate of object
	 * @param y      y coordinate of object
	 * @param width  width of object
	 * @param height height of object
	 * @param image  image texture of object
	 */
	public PlayerController(int x, int y, int width, int height, PImage image) {
		super(x, y, width, height, image);
		dx = 0;
		dy = 0;
		jump = false;
	}

	/**
	 * Creates a new PlayerController object
	 * 
	 * @param x     x coordinate of object
	 * @param y     y coordinate of object
	 * @param image image texture of object
	 */
	public PlayerController(PImage image, int x, int y) {
		super(x, y, image.width, image.height, image);
		dx = 0;
		dy = 0;
		System.out.println(image.width + ", " + image.height);
		jump = false;
	}

	/**
	 * Increases horizontal velocity of object
	 * 
	 * @param dir 1 is right, -1 is left
	 */
	public void walk(int dir) {
		dx += dir * 0.5;
	}

	/**
	 * Changes vertical velocity of object
	 * 
	 */
	private void fall() {
		dy += 0.1;
	}

	/**
	 * Changes vertical velocity of object
	 * 
	 */
	public void jump() {
		if (!jump) {
			dy--;
			jump = true;
		}
	}

	/**
	 * Determines what velocity is changed its movement due to it
	 * 
	 * @param platform Shape objects the PlayerController interacts with.
	 */
	public void act(ArrayList<Shape> platform) {
		fall();
		moveBy(dx, dy);
	}

}
