package veggie.model;

import java.awt.Rectangle;
import java.util.Map;

import gifAnimation.Gif;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;
import veggie.screen.DrawingSurface;

/**
 * @author awang104 MovingImage represents any image that moves an interacts
 *         with the environment while in platform mode
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
	 * @param height        height of the MovingImage
	 * @param lettuceAssets texture used to represent the MovingImage
	 */
	public MovingImage(int x, int y, int width, int height, Map<String, PImage> lettuceAssets)
	{
		hitbox = new Rectangle(x, y, width, height);
		this.image = lettuceAssets;
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

	public void changeBy(int x, int y)
	{
		hitbox.x = x;
		hitbox.y = y;
	}

	/**
	 * Draws the object
	 * 
	 * @param marker Place where object is drawn on.
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
	 */
	public void draw(PGraphics graphics, String key)
	{	
		if(image.get(key).getClass() == Gif.class)
		{
			((Gif) image.get(key)).play();
		}
		graphics.beginDraw();
		// I CHANGED THE SIZE OF THE IMAGE FOR BATTLEMODE HERE!!@Q@!@!Q$@#$@#$ 
		graphics.image(image.get(key), (float) hitbox.x, (float) hitbox.y, 100, 100);
		graphics.endDraw();
	}

	/**
	 * @param bounds Shape interacting with MovingImage
	 * @return true if MovingImage touches the shape
	 */
	public boolean intersects(PShape bounds)
	{

		/*
		double x = bounds.getVertexX();
		double y = bounds.getVertexY();
		double width = bounds.getBounds().getWidth();

		boolean intersects = Math.abs(hitbox.y + hitbox.height - y) < 16 && (hitbox.x > x && hitbox.x < x + width
				|| hitbox.x + hitbox.width > x && hitbox.x + hitbox.width < x + width);

		return intersects;
		/*
		 * boolean intersects = false; if(bounds instanceof Rectangle) intersects =
		 * hitbox.intersects((Rectangle)bounds); return intersects;
		 */
		return true;
	}

	/**
	 * @return Rectangular bounds of the MovingImage
	 */
	public Rectangle getBounds()
	{
		return hitbox;
	}
	
	public double getX() {
		return hitbox.getX();
	}
	
	public double getY() {
		return hitbox.getY();
	}
	
	public double getHeight() {
		return hitbox.getHeight();
	}
	
	public double getWidth() {
		return hitbox.getWidth();
	}
	
}
