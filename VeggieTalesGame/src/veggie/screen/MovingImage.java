package veggie.screen;

import java.awt.Rectangle;
import java.awt.Shape;

import processing.core.PImage;

public class MovingImage {
	
	private Rectangle hitbox;
	private PImage image;
	
	public MovingImage(int x, int y, int width, int height, PImage image) {
		hitbox = new Rectangle(x, y, width, height);
		this.image = image;
	}
	
	public void moveTo(int x, int y) {
		hitbox.setLocation(x, y);
	}
	
	public void moveBy(int x, int y) {
		hitbox.x += x;
		hitbox.y += y;
	}
	
	public void draw(DrawingSurface marker) {
		marker.image(image, (float)hitbox.x, (float)hitbox.y);
	}
	
	public boolean contains(Shape bounds) {
		return hitbox.contains(bounds.getBounds2D());
	}
	
	public Rectangle getBounds() {
		return hitbox;
	}
}
