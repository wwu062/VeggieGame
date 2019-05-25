package veggie.model;

import java.awt.Rectangle;
import java.util.Map;

import gifAnimation.Gif;
import processing.core.PGraphics;
import processing.core.PImage;
import veggie.screen.DrawingSurface;

/**
 * @author awang104 
 * MovingImage represents any image that moves an interacts with the environment while in platform mode
 */
public class MovingImage
{

	private Rectangle hitbox;
	private Map<String, PImage> image;

	/**
	 * Creates a new MovingImage object
	 * 
	 * @param x             x coordinate of the MovingImage
	 * @param y             y coordinate of the MovingImage
	 * @param width         width of the MovingImage
	 * @param height height of the MovingImage
	 * @param imageassets texture used to represent the MovingImage
	 */
	public MovingImage(int x, int y, int width, int height, Map<String, PImage> imageassets)
	{
		hitbox = new Rectangle(x, y, width, height);
		this.image = imageassets;
	}

	/**
	 * Moves the object to the designated location
	 * 
	 * @param x new x-coordinate of MovingImage
	 * @param y new y-coordinate of MovingImage
	 */
	public void moveTo(double x, double y)
	{
		hitbox.x = (int) x;
		hitbox.y = (int) y;
	}

	/**
	 * Moves the object by that amount
	 * 
	 * @param x change in x-coordinate
	 * @param y change in y-coordinate
	 */
	public void moveBy(double x, double y)
	{
		hitbox.x += x;
		hitbox.y += y;
	}

	/** Translates the MovingImage by the parameters specified
	 * 
	 * @param x the shift in x-coordinate
	 * @param y the shift in y-coordinate
	 */
	public void changeBy(int x, int y)
	{
		hitbox.x = x;
		hitbox.y = y;
	}

	/**
	 * Draws the object
	 * 
	 * @param marker Place where object is drawn on.
	 * @param key key of the image wanted
	 */
	public void draw(DrawingSurface marker, String key)
	{
		marker.image(image.get(key), (float) hitbox.x, (float) hitbox.y);

		if(image.get(key).getClass() == Gif.class)
		{
			((Gif) image.get(key)).play();
		}
	}

	/**
	 * Draws the object with PGraphics
	 * 
	 * @param graphics where the object is drawn
	 * @param key key of the image wanted
	 */
	public void draw(PGraphics graphics, String key)
	{	
		if(image.get(key).getClass() == Gif.class)
		{
			((Gif) image.get(key)).play();
		}
		graphics.beginDraw();
		graphics.image(image.get(key), (float) hitbox.x, (float) hitbox.y, 125, 125);
		graphics.endDraw();
	}

	/**
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param width width of shape
	 * @return true if MovingImage touches the shape
	 */
	public boolean intersects(double x, double y, double width) {
		boolean intersects = Math.abs(hitbox.y + hitbox.height - y) < 14 && (hitbox.x > x && hitbox.x < x + width || hitbox.x + hitbox.width > x && hitbox.x + hitbox.width < x + width);
		return intersects;
	}
	
	public boolean intersects(Rectangle bounds) {
		return this.intersects(bounds);
	}

	/**
	 * @return Rectangular bounds of the MovingImage
	 */
	public Rectangle getBounds()
	{
		return hitbox;
	}
	
	/**
	 * 
	 * @return the x-coordinate value
	 */
	public double getX() {
		return hitbox.getX();
	}
	
	/**
	 * 
	 * @return the y-coordinate value
	 */
	public double getY() {
		return hitbox.getY();
	}
	
	/**
	 * 
	 * @return the height of the MovingImage
	 */
	public double getHeight() {
		return hitbox.getHeight();
	}
	
	/**
	 * 
	 * @return the width of the MovingImage
	 */
	public double getWidth() {
		return hitbox.getWidth();
	}
	
}
