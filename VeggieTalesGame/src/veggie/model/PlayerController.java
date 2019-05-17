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
	private double velX;
	private double velY;
	private boolean onSurface;
	private boolean isWalking;

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
		velX = 0;
		velY = 0;
		onSurface = false;
		isWalking = false;
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
		velX = 0;
		velY = 0;
		onSurface = false;
		isWalking = false;
	}

	/**
	 * Increases horizontal velocity of object
	 * 
	 * @param dir 1 is right, -1 is left
	 */
	public void walk(int dir) {
		velX += dir * 0.5;
		isWalking = true;
	}

	/**
	 * Changes vertical velocity of object
	 * 
	 */
	public void fall() {
		
		if(Math.abs(velX) < 0.2) {
			isWalking = false;
			velX = 0;
		}
		
		velY += 0.7; // Gravity
		if(isWalking && onSurface)
			velX += -0.05*(Math.abs(velX)/velX); // Friction
			
			
			onSurface = false;

			
	}
	
	public void checkPlayer(ArrayList<Shape> platform) {
		moveBy(velX, velY);
		for(Shape s : platform) {
			if(super.intersects(s)) {
				velY = 0;
				onSurface = true;
				moveTo(this.getBounds().x, s.getBounds().y - 1.001*this.getBounds().getHeight());
				break;
			}
		}
	}

	/**
	 * Changes vertical velocity of object
	 * 
	 */
	public void jump() {
		if (onSurface) {
			velY = -15;
		}
	}
	
	public boolean battle(PlayerController bot) {
		return bot.getBounds().intersects(this.getBounds());
	}
	
	public void stop() {
		velX = 0;
		velY = 0;
	}


}
