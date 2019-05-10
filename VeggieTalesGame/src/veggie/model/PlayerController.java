package veggie.model;

import java.awt.Shape;
import java.util.ArrayList;

import processing.core.PImage;

public class PlayerController extends MovingImage {
	private int dx;
	private int dy;

	public PlayerController(int x, int y, int width, int height, PImage image) {
		super(x, y, width, height, image);
		dx = 0;
		dy = 0;
	}
	
	public PlayerController(PImage image, int x, int y) {
		super(x, y, image.width, image.height, image);
		dx = 0;
		dy = 0;
		System.out.println("player controller constructor");
	}
	
	public void walk(int dir) {
		dx += dir;
	}
	
	private void fall(ArrayList<Shape> platform) {
		for(Shape s : platform) {
			if(contains(s)) {
				dy = 0;
				break;
			} else {
				dy++;
			}
		}
	}
	
	public void jump() {
		dy--;
	}
	
	public void act(ArrayList<Shape> platform) {
		fall(platform);
		moveBy(dx, dy);
	}
	
}
